package com.pluralsight.frontdesk.infrastructure.data.sync.model;

import pluralsightddd.sharedkernel.ddd.types.DomainEvent;

public record DoctorDto(Long id, String name) implements DomainEvent {}
