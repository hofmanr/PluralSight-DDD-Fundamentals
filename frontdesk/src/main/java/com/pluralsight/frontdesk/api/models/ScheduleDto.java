package com.pluralsight.frontdesk.api.models;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@JsonbPropertyOrder({"id", "clinicId", "start", "end", "appointments"})
public class ScheduleDto {
    private final UUID id;
    private final Integer clinicId;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private List<AppointmentDto> appointments;

    public ScheduleDto(UUID id, Integer clinicId, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.clinicId = clinicId;
        this.start = start;
        this.end = end;
    }

    public UUID getId() {
        return id;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public List<AppointmentDto> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentDto> appointments) {
        this.appointments = appointments;
    }
}


