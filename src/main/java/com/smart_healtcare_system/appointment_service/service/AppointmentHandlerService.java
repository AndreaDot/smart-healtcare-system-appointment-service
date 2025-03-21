package com.smart_healtcare_system.appointment_service.service;

import com.smart_healtcare_system.appointment_service.dto.request.AppointmentDTORequest;
import com.smart_healtcare_system.appointment_service.dto.response.AppointmentDTOResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AppointmentHandlerService {

    AppointmentDTOResponse scheduleAppointment(AppointmentDTORequest appointmentDTORequest);

    List<AppointmentDTOResponse> retrieveAppointmentsRequestsOfDoctor(Long doctorId);

    boolean approveAppointmentRequests(Long appointmentId);

    boolean rejectAppointmentRequests(Long appointmentId);

}
