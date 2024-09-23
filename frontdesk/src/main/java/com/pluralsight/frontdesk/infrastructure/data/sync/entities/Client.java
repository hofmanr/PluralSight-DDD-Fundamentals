package com.pluralsight.frontdesk.infrastructure.data.sync.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pluralsightddd.sharedkernel.infrastructure.data.JpaEntity;

@Entity
@Table(name = "CLIENTS")
public class Client extends JpaEntity {
    @Column(name = "name")
    @Size(min = 3, max = 32)
    @NotNull
    private String preferredName;

    @Column(name = "salutation")
    @Size(max = 32)
    private String salutation;

    @Column(name = "preferredDoctor")
    private Long preferredDoctorId;

    @Column(name = "email")
    @Size(min = 3, max = 32)
    @NotNull
    private String emailAddress;

    public @Size(min = 3, max = 32) @NotNull String getPreferredName() {
        return preferredName;
    }

    public Client() {
        // Default constructor
    }

    public Client(Long id, String preferredName) {
        this.setId(id);
        this.preferredName = preferredName;
    }

    public void setPreferredName(@Size(min = 3, max = 32) @NotNull String preferredName) {
        this.preferredName = preferredName;
    }

    public @Size(max = 32) String getSalutation() {
        return salutation;
    }

    public void setSalutation(@Size(max = 32) String salutation) {
        this.salutation = salutation;
    }

    public Long getPreferredDoctorId() {
        return preferredDoctorId;
    }

    public void setPreferredDoctorId(Long preferredDoctorId) {
        this.preferredDoctorId = preferredDoctorId;
    }

    public @Size(min = 3, max = 32) @NotNull String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(@Size(min = 3, max = 32) @NotNull String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
