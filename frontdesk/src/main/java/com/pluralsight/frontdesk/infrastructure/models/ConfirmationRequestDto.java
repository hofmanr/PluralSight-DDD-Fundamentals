package com.pluralsight.frontdesk.infrastructure.models;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonbPropertyOrder({"appointmentId", "appointmentType","title","clientName","patientName", "doctorName","start","end"})
public record ConfirmationRequestDto(
        UUID appointmentId,
        String appointmentType,
        String patientName,
        String clientName,
        String doctorName,
        LocalDateTime start,
        LocalDateTime end,
        String title
) { }
