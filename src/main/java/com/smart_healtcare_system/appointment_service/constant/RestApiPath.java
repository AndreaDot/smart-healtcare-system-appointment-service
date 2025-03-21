package com.smart_healtcare_system.appointment_service.constant;

public class RestApiPath {
    public static final String BASE_APPOINTMENT = "/appointments";
    public static final String APPOINTMENT_REQUESTS_FOR_DOCTOR = "/doctor-requests/{doctorId}";
    public static final String APPROVE_APPOINTMENT = "/approve/{appointmentId}";
    public static final String REJECT_APPOINTMENT = "/reject/{appointmentId}";
}
