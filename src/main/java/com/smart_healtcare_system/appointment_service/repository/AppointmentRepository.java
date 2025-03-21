package com.smart_healtcare_system.appointment_service.repository;

import com.smart_healtcare_system.appointment_service.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorIdAndStatus(Long doctorId, String status);

    List<Appointment> findByDoctorId(Long doctorId);

    Optional<Appointment> findByDoctorIdAndStatusAndDateAndTimeBetween(Long doctorId, String status, LocalDate date, LocalTime timefrom, LocalTime timeTo);

    List<Appointment> findByPatientId(Long patientId);

}
