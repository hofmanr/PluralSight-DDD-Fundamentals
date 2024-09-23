package com.pluralsight.frontdesk.api.models;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"appointmentTypeId", "name", "duration"})
public record AppointmentTypeDto(
        Long appointmentTypeId,
        String name,
        Integer duration // in minutes
) {
}
