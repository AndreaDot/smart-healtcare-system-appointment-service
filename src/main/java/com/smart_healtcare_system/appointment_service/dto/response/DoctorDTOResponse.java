package com.smart_healtcare_system.appointment_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTOResponse {

    private String name;

    private String surname;

    private String ssn;

    private String email;

    private String specialization;

    private String city;

    private String address;
}
