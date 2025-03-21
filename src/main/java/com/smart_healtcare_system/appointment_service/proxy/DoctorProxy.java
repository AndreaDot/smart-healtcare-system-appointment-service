package com.smart_healtcare_system.appointment_service.proxy;

import com.smart_healtcare_system.appointment_service.dto.response.DoctorDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "doctor-service")
public interface DoctorProxy {
    @GetMapping("doctors/id/{doctorID}")
    ResponseEntity<DoctorDTOResponse> retrieveDoctorDetails(@PathVariable Long doctorID);
}
