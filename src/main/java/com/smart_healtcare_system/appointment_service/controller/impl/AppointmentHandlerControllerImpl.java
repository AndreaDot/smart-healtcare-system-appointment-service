package com.smart_healtcare_system.appointment_service.controller.impl;

import com.smart_healtcare_system.appointment_service.controller.AppointmentHandlerController;
import com.smart_healtcare_system.appointment_service.dto.request.AppointmentDTORequest;
import com.smart_healtcare_system.appointment_service.dto.response.AppointmentDTOResponse;
import com.smart_healtcare_system.appointment_service.service.AppointmentHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class AppointmentHandlerControllerImpl implements AppointmentHandlerController {

    private final AppointmentHandlerService appointmentHandlerService;

    @Override
    public ResponseEntity<AppointmentDTOResponse> scheduleAppointment(AppointmentDTORequest appointmentDTORequest) {
        AppointmentDTOResponse appointmentDTOResponse = appointmentHandlerService.scheduleAppointment(appointmentDTORequest);
        if (Objects.nonNull(appointmentDTOResponse)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(appointmentDTOResponse);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<List<AppointmentDTOResponse>> retrieveAppointmentRequestsForDoctor(Long doctorId) {
        List<AppointmentDTOResponse> appointmentsRequestsOfDoctor = appointmentHandlerService.retrieveAppointmentsRequestsOfDoctor(doctorId);
        if (Objects.nonNull(appointmentsRequestsOfDoctor)) {
            return ResponseEntity.status(HttpStatus.OK).body(appointmentsRequestsOfDoctor);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Boolean> approveAppointment(Long appointmentId) {
        boolean approved = appointmentHandlerService.approveAppointmentRequests(appointmentId);
        if (approved) {
            return ResponseEntity.status(HttpStatus.OK).body(approved);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<Boolean> rejectAppointment(Long appointmentId) {
        boolean rejected = appointmentHandlerService.rejectAppointmentRequests(appointmentId);
        if (rejected) {
            return ResponseEntity.status(HttpStatus.OK).body(rejected);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();    }
}
