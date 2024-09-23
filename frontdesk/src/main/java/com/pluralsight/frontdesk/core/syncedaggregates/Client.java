package com.pluralsight.frontdesk.core.syncedaggregates;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pluralsightddd.sharedkernel.ddd.types.AggregateRoot;
import pluralsightddd.sharedkernel.ddd.types.DomainEntity;

import java.io.Serial;
import java.util.List;
import java.util.Objects;

public class Client extends DomainEntity<Long> implements AggregateRoot {
    @Serial
    private static final long serialVersionUID = 1L;

    @Size(min = 3, max = 32)
    @NotNull
    private String preferredName;
    @Size(max = 32)
    private String salutation;
    private Long preferredDoctorId;
    @Size(min = 3, max = 32)
    @NotNull
    private String emailAddress;
    private List<Patient> patients;

    // Should not publicly being used
    protected Client() {
    }

    public Client(Long id, String preferredName, String salutation, Long preferredDoctorId, String emailAddress, List<Patient> patients) {
        this.id = id;
        this.preferredName = preferredName;
        this.salutation = salutation;
        this.preferredDoctorId = preferredDoctorId;
        this.emailAddress = emailAddress;
        this.patients = patients;
    }

    public String getPatientName(Long patientId) {
        if (patientId == null) return null;
        Patient patient = patients.stream()
                .filter(c -> Objects.equals(patientId, c.getId())).findFirst().orElse(null);
        return patient == null ? null : patient.getName();
    }

    public String getPreferredName() {
        return preferredName;
    }

    public String getSalutation() {
        return salutation;
    }

    public Long getPreferredDoctorId() {
        return preferredDoctorId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
