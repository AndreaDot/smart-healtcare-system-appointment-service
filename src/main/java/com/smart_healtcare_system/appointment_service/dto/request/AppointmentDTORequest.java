package com.smart_healtcare_system.appointment_service.dto.request;

import com.smart_healtcare_system.appointment_service.constant.AppointmentStatus;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AppointmentDTORequest {

    @NotNull(message = "{patient.required}")
    private Long patientId;

    @NotNull(message = "{doctor.required}")
    private Long doctorId;

    @NotNull(message = "{time.required}")
    private LocalDate date;

    @NotNull(message = "{time.required}")
    private LocalTime time;

    private AppointmentStatus status;
}
