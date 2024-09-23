package com.pluralsight.frontdesk.infrastructure.data.sync.model;

import pluralsightddd.sharedkernel.ddd.types.DomainEvent;

public record ClientDto(
        Long id,
        String name,
        String salutation,
        Long preferredDoctorId,
        String emailAddress) implements DomainEvent { }
