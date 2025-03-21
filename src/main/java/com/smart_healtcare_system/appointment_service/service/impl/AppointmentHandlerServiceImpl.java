package com.smart_healtcare_system.appointment_service.service.impl;


import com.smart_healtcare_system.appointment_service.constant.AppointmentStatus;
import com.smart_healtcare_system.appointment_service.dto.request.AppointmentDTORequest;
import com.smart_healtcare_system.appointment_service.dto.response.AppointmentDTOResponse;
import com.smart_healtcare_system.appointment_service.dto.response.DoctorDTOResponse;
import com.smart_healtcare_system.appointment_service.dto.response.PatientDTOResponse;
import com.smart_healtcare_system.appointment_service.mapper.AppointmentMapper;
import com.smart_healtcare_system.appointment_service.model.Appointment;
import com.smart_healtcare_system.appointment_service.proxy.DoctorProxy;
import com.smart_healtcare_system.appointment_service.proxy.PatientProxy;
import com.smart_healtcare_system.appointment_service.repository.AppointmentRepository;
import com.smart_healtcare_system.appointment_service.service.AppointmentHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentHandlerServiceImpl implements AppointmentHandlerService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final PatientProxy patientProxy;
    private final DoctorProxy doctorProxy;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.appointment.requests.name}")
    private String appointmentRequestsQueue;

    @Value("${rabbitmq.queue.appointment.responses.name}")
    private String appointmentResponsesQueue;

    @Value("${rabbitmq.exchange.name}")
    private String appointmentExchange;

    @Value("${rabbitmq.binding.appointment.requests.routing.key}")
    private String appointmentRequestsRoutingKey;

    @Value("${rabbitmq.binding.appointment.responses.routing.key}")
    private String appointmentReponsesRoutingKey;


    @Override
    public AppointmentDTOResponse scheduleAppointment(AppointmentDTORequest appointmentDTORequest) {

        ResponseEntity<PatientDTOResponse> patientResponseEntity = patientProxy.retievePatientDetails(appointmentDTORequest.getPatientId());
        PatientDTOResponse patientDTOResponse = patientResponseEntity.getBody();

        ResponseEntity<DoctorDTOResponse> doctorDTOResponseResponseEntity = doctorProxy.retrieveDoctorDetails(appointmentDTORequest.getDoctorId());

        DoctorDTOResponse doctorDTOResponse = doctorDTOResponseResponseEntity.getBody();

        appointmentDTORequest.setStatus(AppointmentStatus.SCHEDULED);

        if (Objects.nonNull(patientDTOResponse) && Objects.nonNull(doctorDTOResponse)) {
            Optional<Appointment> byDateAndTimeBetween = appointmentRepository.findByDoctorIdAndStatusAndDateAndTimeBetween(appointmentDTORequest.getDoctorId(), AppointmentStatus.APPROVED.name(), appointmentDTORequest.getDate(), appointmentDTORequest.getTime().minusMinutes(30), appointmentDTORequest.getTime().plusMinutes(30));
            if (byDateAndTimeBetween.isPresent()) {
                return null;
            }
            Appointment appointmentSaved = appointmentRepository.save(appointmentMapper.mapDTORequestToEntity(appointmentDTORequest));
            AppointmentDTOResponse appointmentResponse = AppointmentDTOResponse.builder()
                    .date(appointmentSaved.getDate())
                    .time(appointmentSaved.getTime())
                    .doctor(doctorDTOResponse)
                    .patient(patientDTOResponse)
                    .status(appointmentSaved.getStatus())
                    .build();
            rabbitTemplate.convertAndSend(appointmentExchange, appointmentRequestsRoutingKey, appointmentResponse);
            return appointmentResponse;
        }
        return null;
    }

    @Override
    public List<AppointmentDTOResponse> retrieveAppointmentsRequestsOfDoctor(Long doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndStatus(doctorId, AppointmentStatus.SCHEDULED.name());
        if (!appointments.isEmpty()) {
            return appointments.stream().map(appointment -> AppointmentDTOResponse.builder()
                    .appointmentId(appointment.getAppointmentId())
                    .status(appointment.getStatus())
                    .date(appointment.getDate())
                    .time(appointment.getTime())
                    .build()).toList();
        }
        return null;
    }

    @Override
    public boolean approveAppointmentRequests(Long appointmentId) {
        return updateStatus(appointmentId, AppointmentStatus.APPROVED);
    }

    @Override
    public boolean rejectAppointmentRequests(Long appointmentId) {
        return updateStatus(appointmentId, AppointmentStatus.CANCELED);
    }

    private boolean updateStatus(Long appointmentId, AppointmentStatus status) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isPresent()) {
            appointment.get().setStatus(status.name());
            appointmentRepository.save(appointment.get());
            rabbitTemplate.convertAndSend(appointmentExchange, appointmentReponsesRoutingKey, AppointmentDTOResponse.builder()
                    .appointmentId(appointment.get().getAppointmentId())
                    .status(appointment.get().getStatus())
                    .date(appointment.get().getDate())
                    .time(appointment.get().getTime())
                    .build());
            return true;
        }
        return false;
    }
}
