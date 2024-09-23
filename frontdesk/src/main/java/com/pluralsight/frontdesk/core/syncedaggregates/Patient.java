package com.pluralsight.frontdesk.core.syncedaggregates;

import com.pluralsight.frontdesk.core.valueobjects.AnimalType;
import jakarta.persistence.*;
import pluralsightddd.sharedkernel.ddd.annotations.DomainEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@DomainEntity
public class Patient implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String sex;
    private AnimalType animalType;
    private Long preferredDoctorId;

    // Don't use this
    protected Patient() {
        // Default
    }

    public Patient(Long id, String name, String sex, AnimalType animalType, Long preferredDoctorId) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.animalType = animalType;
        this.preferredDoctorId = preferredDoctorId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public Long getPreferredDoctorId() {
        return preferredDoctorId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Patient patient = (Patient) object;
        return Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
