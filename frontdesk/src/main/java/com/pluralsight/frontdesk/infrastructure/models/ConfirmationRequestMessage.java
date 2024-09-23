package com.pluralsight.frontdesk.infrastructure.models;

import com.pluralsight.frontdesk.core.models.ConfirmationRequest;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"eventType", "payload"})
public class ConfirmationRequestMessage extends ConfirmationRequest {
    public final String eventType;
    public final ConfirmationRequestDto payload;

    public ConfirmationRequestMessage(ConfirmationRequest request) {
        this.eventType = "confirmationRequest";
        this.payload = new ConfirmationRequestDto(
                request.appointmentId,
                request.appointmentType,
                request.patientName,
                request.clientName,
                request.doctorName,
                request.start,
                request.end,
                request.title
        );
    }
}
