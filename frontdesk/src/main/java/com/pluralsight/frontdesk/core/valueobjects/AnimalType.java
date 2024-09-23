package com.pluralsight.frontdesk.core.valueobjects;

import jakarta.validation.constraints.Size;
import pluralsightddd.sharedkernel.ddd.types.ValueObject;

import java.util.Collection;
import java.util.List;

public class AnimalType extends ValueObject {
    private static final long serialVersionUID = 1L;

    @Size(max = 32)
    private String species;
    @Size(max = 32)
    private String breed;

    public AnimalType() {
    }

    public AnimalType(String species, String breed) {
        this.species = species;
        this.breed = breed;
    }

    public String getSpecies() {
        return species;
    }

    public String getBreed() {
        return breed;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Collection<Object> getEqualityComponents() {
        return List.of(species, breed);
    }
}
