package com.smart_healtcare_system.appointment_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTOResponse {

    private Long appointmentId;

    private PatientDTOResponse patient;

    private DoctorDTOResponse doctor;

    private LocalDate date;

    private LocalTime time;

    private String status;
}
