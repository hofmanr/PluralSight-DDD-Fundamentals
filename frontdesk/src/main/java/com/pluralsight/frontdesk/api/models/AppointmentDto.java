package com.pluralsight.frontdesk.api.models;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonbPropertyOrder({"scheduleId", "appointmentId", "appointmentType", "title", "clientId", "clientName", "patientId", "patientName",
"roomId", "doctorId", "start", "end", "isAllDay", "isConfirmed"})
public class AppointmentDto {
    public UUID appointmentId;
    public UUID scheduleId;
    public Long roomId;
    public Long doctorId;
    public Long clientId;
    public Long patientId;
    public String patientName;
    public String clientName;
    public LocalDateTime start;
    public LocalDateTime end;
    public String title;
    public boolean isAllDay;
    public boolean isPotentiallyConflicting;
    public boolean isConfirmed;
    public AppointmentTypeDto appointmentType;

    public AppointmentDto shallowCopy() {
        return new AppointmentDto(); // TODO
    }

    @Override
    public String toString() {
        return "AppointmentDto{" +
                "appointmentId=" + appointmentId +
                ", roomId=" + roomId +
                ", doctorId=" + doctorId +
                ", clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", patientId=" + patientId +
                ", patientName='" + patientName + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
