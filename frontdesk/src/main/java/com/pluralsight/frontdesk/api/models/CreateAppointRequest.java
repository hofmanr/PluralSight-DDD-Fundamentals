package com.pluralsight.frontdesk.api.models;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateAppointRequest(
         Long patientId,
         UUID scheduleId,
         Long appointmentTypeId,
         Long clientId,
         Long roomId,
         LocalDateTime dateOfAppointment, // localDateTime; not utc
         Long selectedDoctor,
         String title
) {}
