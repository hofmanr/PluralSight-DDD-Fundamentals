package com.pluralsight.frontdesk.core.events;

import pluralsightddd.sharedkernel.ddd.types.DomainEvent;
import pluralsightddd.sharedkernel.core.valueobjects.DateTimeOffset;
import java.util.UUID;

public record AppointmentConfirmed(
        UUID appointmentId,
        DateTimeOffset dateTimeConfirmation) implements DomainEvent {}