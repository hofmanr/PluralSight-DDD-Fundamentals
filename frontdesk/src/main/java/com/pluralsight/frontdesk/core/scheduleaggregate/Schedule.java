package com.pluralsight.frontdesk.core.scheduleaggregate;

import com.pluralsight.frontdesk.core.events.AppointmentScheduled;
import com.pluralsight.frontdesk.core.validators.DuplicateAppointment;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import pluralsightddd.sharedkernel.core.valueobjects.DateTimeOffset;
import pluralsightddd.sharedkernel.core.valueobjects.DateTimeOffsetRange;
import pluralsightddd.sharedkernel.ddd.types.AggregateRoot;
import pluralsightddd.sharedkernel.ddd.types.DomainEntity;

import java.time.LocalDateTime;
import java.util.*;

@DuplicateAppointment
public class Schedule extends DomainEntity<UUID> implements AggregateRoot {
    @Min(1)
    @NotNull
    private Integer clinicId;
    private List<Appointment> appointments = new ArrayList<>();
    private DateTimeOffsetRange dateRange;

    // Should not be used
    protected Schedule() {
        // Default constructor
    }

    public Schedule(UUID id, Integer clinicId, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.clinicId = clinicId;
        DateTimeOffset startOffset = new DateTimeOffset(start);
        DateTimeOffset endOffset = new DateTimeOffset(end);
        this.dateRange = new DateTimeOffsetRange(startOffset, endOffset);
    }

    public Schedule(UUID id, Integer clinicId) {
        this.id = id;
        this.clinicId = clinicId;
    }

    public Schedule(UUID id, Integer clinicId, List<Appointment> appointments) {
        this.id = id;
        this.clinicId = clinicId;
        this.appointments = appointments;
    }

    public Appointment getAppointment(UUID appointmentId) {
        return appointments.stream().filter(a -> a.getId().equals(appointmentId))
                .findFirst().orElse(null);
    }

    public void addNewAppointment(Appointment appointment)
    {
        // Check on duplicate appointment is done at entity level (DuplicateAppointment validator)
        if (appointment == null) return;
        appointments.add(appointment);

        markConflictingAppointments();

        var appointmentScheduledEvent = new AppointmentScheduled(appointment);
        events.add(appointmentScheduledEvent);
    }

    public void deleteAppointment(@NotNull Appointment appointment) {
        UUID id = appointment == null ? UUID.randomUUID() : appointment.getId();
        appointments.stream()
                .filter(a -> Objects.equals(a.getId(), id))
                .findFirst()
                .ifPresent(appointments::remove);

        markConflictingAppointments();

        // TODO: Add appointment deleted event and show delete message in the front-end
    }

    private void markConflictingAppointments() {
        appointments.forEach(app -> {
            var potentiallyConflictingAppointments = appointments.stream()
                    .filter(a -> Objects.equals(a.getPatientId(), app.getPatientId()) &&
                            a.getTimeRange().overlaps(app.getTimeRange()) &&
                            !Objects.equals(a.getId(), app.getId()))
                    .toList();

            // TODO: Add a rule to mark overlapping appointments in same room as conflicting
            // TODO: Add a rule to mark same doctor with overlapping appointments as conflicting

            potentiallyConflictingAppointments.forEach(pca -> pca.setPotentiallyConflicting(true));

            app.setPotentiallyConflicting(!potentiallyConflictingAppointments.isEmpty());
        });
    }

    /// <summary>
    /// Call any time this schedule's appointments are updated directly
    /// </summary>
    public void appointmentUpdatedHandler()
    {
        // TODO: Add ScheduleHandler calls to UpdateDoctor, UpdateRoom to complete additional rules described in MarkConflictingAppointments
        markConflictingAppointments();
    }

// ------------------------ Getters & Setters ---------------------------------------

    public Integer getClinicId() {
        return clinicId;
    }

    public DateTimeOffsetRange getDateRange() {
        return dateRange;
    }

    /**
     * Important: return an immutable list. By doing so, the client can not add a new appointment to the list.
     * @return
     */
    public List<Appointment> getAppointments() {
        return List.copyOf(appointments);
    }

    // Don't expose setAppointments publicly
    protected void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", clinicId=" + clinicId +
                ", appointments=" + appointments +
                ", dateRange=" + dateRange +
                '}';
    }

}
