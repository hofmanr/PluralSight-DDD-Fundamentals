package com.pluralsight.frontdesk.api.models;

import pluralsightddd.sharedkernel.core.valueobjects.DateTimeOffset;

import java.util.UUID;

public record UpdateAppointmentRequest(
        UUID id,
        UUID scheduleId,
        String title,
        Long roomId,
        Long doctorId,
        Long appointmentTypeId,
        DateTimeOffset start
) {
}