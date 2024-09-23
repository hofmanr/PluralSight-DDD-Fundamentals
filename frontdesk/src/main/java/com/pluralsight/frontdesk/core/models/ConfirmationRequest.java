package com.pluralsight.frontdesk.core.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class ConfirmationRequest {

    public UUID appointmentId;
    public String appointmentType;
    public String patientName;
    public String clientName;
    public String doctorName;
    public LocalDateTime start;
    public LocalDateTime end;
    public String title;
}
