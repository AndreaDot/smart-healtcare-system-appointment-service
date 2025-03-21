package com.smart_healtcare_system.appointment_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @JoinColumn(table = "patient", name = "patient_id", nullable = false)
    private Long patientId;

    @JoinColumn(table = "doctor", name = "doctor_id", nullable = false)
    private Long doctorId;

    private LocalDate date;

    private LocalTime time;

    private String status;


}
