package com.pluralsight.frontdesk.core.events;

import com.pluralsight.frontdesk.core.scheduleaggregate.Appointment;
import pluralsightddd.sharedkernel.ddd.types.DomainEvent;

public record AppointmentUpdated(Appointment appointmentUpdated) implements DomainEvent {}
