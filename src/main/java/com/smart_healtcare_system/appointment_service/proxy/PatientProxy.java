package com.smart_healtcare_system.appointment_service.proxy;

import com.smart_healtcare_system.appointment_service.dto.response.PatientDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service")
public interface PatientProxy {

    @GetMapping("/patients/id/{patientId}")
    ResponseEntity<PatientDTOResponse> retievePatientDetails(@PathVariable Long patientId);
}
