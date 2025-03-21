package com.smart_healtcare_system.appointment_service.mapper;

import com.smart_healtcare_system.appointment_service.dto.request.AppointmentDTORequest;
import com.smart_healtcare_system.appointment_service.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(expression = "java(appointmentDTORequest.getStatus().name())", target = "status")
    Appointment mapDTORequestToEntity(AppointmentDTORequest appointmentDTORequest);


}
