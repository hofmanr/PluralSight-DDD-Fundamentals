package com.pluralsight.frontdesk.core.syncedaggregates;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pluralsightddd.sharedkernel.ddd.types.AggregateRoot;
import pluralsightddd.sharedkernel.ddd.types.DomainEntity;

import java.io.Serial;
import java.time.Duration;

@SuppressWarnings("JpaMissingIdInspection")
public class AppointmentType extends DomainEntity<Long> implements AggregateRoot {
    @Serial
    private static final long serialVersionUID = 1L;

    @Size(min = 3, max = 32)
    @NotNull
    private String name;
    @Size(min = 3, max = 8)
    @NotNull
    private String code;
    private Integer minutes;  // duration

    public AppointmentType(Long id, String name, String code, Integer minutes) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.minutes = minutes;
    }

    // Should not be used
    protected AppointmentType() {
        // default constructor
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    protected void setCode(String code) {
        this.code = code;
    }

    public Integer getMinutes() {
        return minutes;
    }

    protected void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Duration getDuration() {
        return minutes != null ? Duration.ofMinutes(minutes) : null;
    }

    @Override
    public String toString() {
        return "AppointmentType{" +
                "name='" + name + '\'' +
                '}';
    }
}
