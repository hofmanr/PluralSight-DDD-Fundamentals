package com.pluralsight.frontdesk.infrastructure.data.sync.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pluralsightddd.sharedkernel.infrastructure.data.JpaEntity;

@Entity
@Table(name = "DOCTORS")
public class Doctor extends JpaEntity {

    @Column(name = "name")
    @Size(min = 3, max = 32)
    @NotNull
    private String name;

    protected Doctor() {
        // Default constructor is needed
    }

    public Doctor(Long id, String name) {
        this.setId(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                '}';
    }
}
