package com.smart_healtcare_system.appointment_service.controller;

import com.smart_healtcare_system.appointment_service.constant.RestApiPath;
import com.smart_healtcare_system.appointment_service.dto.request.AppointmentDTORequest;
import com.smart_healtcare_system.appointment_service.dto.response.AppointmentDTOResponse;
import com.smart_healtcare_system.appointment_service.model.Appointment;
import feign.Response;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(RestApiPath.BASE_APPOINTMENT)
public interface AppointmentHandlerController {

    @PostMapping
    ResponseEntity<AppointmentDTOResponse> scheduleAppointment(@Valid @RequestBody AppointmentDTORequest appointmentDTORequest);

    @GetMapping(RestApiPath.APPOINTMENT_REQUESTS_FOR_DOCTOR)
    ResponseEntity<List<AppointmentDTOResponse>> retrieveAppointmentRequestsForDoctor(@PathVariable Long doctorId);

    @PutMapping(RestApiPath.APPROVE_APPOINTMENT)
    ResponseEntity<Boolean> approveAppointment(@PathVariable Long appointmentId);

    @PutMapping(RestApiPath.REJECT_APPOINTMENT)
    ResponseEntity<Boolean> rejectAppointment(@PathVariable Long appointmentId);
}
