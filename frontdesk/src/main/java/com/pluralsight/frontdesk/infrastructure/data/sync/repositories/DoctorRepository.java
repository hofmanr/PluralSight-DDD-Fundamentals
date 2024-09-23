package com.pluralsight.frontdesk.infrastructure.data.sync.repositories;

import com.pluralsight.frontdesk.infrastructure.data.VCS;
import com.pluralsight.frontdesk.infrastructure.data.sync.entities.Doctor;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import pluralsightddd.sharedkernel.common.exceptions.ExceptionOrigin;
import pluralsightddd.sharedkernel.infrastructure.data.repositories.JpaCRUDRepository;

public class DoctorRepository extends JpaCRUDRepository<Doctor> {
    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin("Data interaction for Doctor");

    @Inject
    protected DoctorRepository(@VCS EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected ExceptionOrigin getExceptionOrigin() {
        return EXCEPTION_ORIGIN;
    }
}
