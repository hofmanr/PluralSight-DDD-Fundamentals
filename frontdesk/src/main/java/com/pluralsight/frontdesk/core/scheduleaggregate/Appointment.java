package com.pluralsight.frontdesk.core.scheduleaggregate;

import com.pluralsight.frontdesk.core.events.AppointmentUpdated;
import com.pluralsight.frontdesk.core.syncedaggregates.AppointmentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import pluralsightddd.sharedkernel.common.types.Action;
import pluralsightddd.sharedkernel.core.valueobjects.DateTimeOffset;
import pluralsightddd.sharedkernel.core.valueobjects.DateTimeOffsetRange;
import pluralsightddd.sharedkernel.ddd.types.DomainEntity;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

public class Appointment extends DomainEntity<UUID> {
    private UUID scheduleId;
    @Positive(message = "{vetClinic.validation.constraints.appointmentTypeId.Positive.message}")
    private Long appointmentTypeId;
    @Positive(message = "{vetClinic.validation.constraints.clientId.Positive.message}")
    private Long clientId;
    @Positive(message = "{vetClinic.validation.constraints.doctorId.Positive.message}")
    private Long doctorId;
    @Positive(message = "{vetClinic.validation.constraints.patientId.Positive.message}")
    private Long patientId;
    @Positive(message = "{vetClinic.validation.constraints.roomId.Positive.message}")
    private Long roomId;
    @Size(min = 3, max = 32)
    @NotNull
    private String title;
    @NotNull(message = "{vetClinic.validation.constraints.timeRange.NotNull.message}")
    private DateTimeOffsetRange timeRange;
    private DateTimeOffset dateTimeConfirmed;
    @NotNull
    private boolean potentiallyConflicting = false;


    // Should not be used
    protected Appointment() {
        // do nothing
    }


    public Appointment(UUID id, UUID scheduleId, AppointmentType appointmentType,
                       DateTimeOffset dateOfAppointment,
                       Long clientId,
                       Long selectedDoctor,
                       Long patientId,
                       Long roomId,
                       String title) {
        this.id = id;
        if (appointmentType != null) {
            this.appointmentTypeId = appointmentType.getId();
        }
        this.scheduleId = scheduleId;
        this.clientId = clientId;
        this.doctorId = selectedDoctor;
        this.patientId = patientId;
        this.roomId = roomId;
        this.title = title;
        if (dateOfAppointment != null && appointmentType != null) {
            this.timeRange = new DateTimeOffsetRange(dateOfAppointment, appointmentType.getDuration());
        }
    }

    public void updateRoom(long newRoomId) {
        if (Objects.equals(newRoomId, roomId)) return;

        roomId = newRoomId;

        var appointmentUpdatedEvent = new AppointmentUpdated(this);
        events.add(appointmentUpdatedEvent);
    }

    public void updateDoctor(long newDoctorId) {
        if (Objects.equals(newDoctorId, doctorId)) return;

        doctorId = newDoctorId;

        var appointmentUpdatedEvent = new AppointmentUpdated(this);
        events.add(appointmentUpdatedEvent);
    }

    public void updateStartTime(DateTimeOffset newStartTime, Action scheduleHandler) {
        if (Objects.equals(newStartTime, timeRange.getStart())) return;

        long minutes = timeRange.durationInMinutes();
        timeRange = new DateTimeOffsetRange(newStartTime, Duration.ofMinutes(minutes));

        if (scheduleHandler != null) scheduleHandler.invoke();

        var appointmentUpdatedEvent = new AppointmentUpdated(this);
        events.add(appointmentUpdatedEvent);
    }

    public void updateTitle(String newTitle) {
        if (newTitle.equals(title)) return;

        title = newTitle;

        var appointmentUpdatedEvent = new AppointmentUpdated(this);
        events.add(appointmentUpdatedEvent);
    }

    public void updateAppointmentType(AppointmentType appointmentType, Action scheduleHandler) {
        Long id = appointmentType == null ? -1L : appointmentType.getId();
        Duration duration = appointmentType == null ? Duration.ZERO : appointmentType.getDuration();
        if (Objects.equals(appointmentTypeId, id)) return;

        appointmentTypeId = id;
        timeRange = timeRange.newEnd(timeRange.getStart().addMinutes(duration));

        if (scheduleHandler != null) scheduleHandler.invoke();

        var appointmentUpdatedEvent = new AppointmentUpdated(this);
        events.add(appointmentUpdatedEvent);
    }

    public void confirm(DateTimeOffset dateConfirmed) {
        if (this.dateTimeConfirmed != null) return; // no need to reconfirm

        this.dateTimeConfirmed = dateConfirmed;
    }

    public Long getAppointmentTypeId() {
        return appointmentTypeId;
    }

    // --------------------------- Getters ------------------------

    public UUID getScheduleId() {
        return scheduleId;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getTitle() {
        return title;
    }

    public DateTimeOffsetRange getTimeRange() {
        return timeRange;
    }

    public DateTimeOffset getDateTimeConfirmed() {
        return dateTimeConfirmed;
    }

    public boolean isPotentiallyConflicting() {
        return potentiallyConflicting;
    }

    public void setPotentiallyConflicting(boolean potentiallyConflicting) {
        this.potentiallyConflicting = potentiallyConflicting;
    }

    // -------------------------- Detect duplicates -------------------
    public boolean duplicates(Appointment app) {
        if (this == app) return true;
        if (app == null) return false;
        if (timeRange == null || app.timeRange == null) return false;
        return Objects.equals(scheduleId, app.scheduleId) &&
                Objects.equals(clientId, app.clientId) &&
                Objects.equals(patientId, app.patientId) &&
                timeRange.overlaps(app.getTimeRange());
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", scheduleId=" + scheduleId +
                ", appointmentTypeId=" + appointmentTypeId +
                ", clientId=" + clientId +
                ", doctorId=" + doctorId +
                ", roomId=" + roomId +
                ", title='" + title + '\'' +
                ", timeRange=" + timeRange +
                ", dateTimeConfirmed=" + dateTimeConfirmed +
                '}';
    }

}
