package com.pluralsight.frontdesk.core.syncedaggregates;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pluralsightddd.sharedkernel.ddd.types.AggregateRoot;
import pluralsightddd.sharedkernel.ddd.types.DomainEntity;

import java.io.Serial;

public class Doctor extends DomainEntity<Long> implements AggregateRoot {
    @Serial
    private static final long serialVersionUID = 1L;

    @Size(min = 3, max = 32)
    @NotNull
    private String name;

    protected Doctor() {
        // Default constructor is needed
    }

    public Doctor(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                '}';
    }
}
