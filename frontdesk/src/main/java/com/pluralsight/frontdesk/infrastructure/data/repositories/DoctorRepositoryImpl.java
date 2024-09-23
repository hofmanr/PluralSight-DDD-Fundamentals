package com.pluralsight.frontdesk.infrastructure.data.repositories;

import com.pluralsight.frontdesk.core.interfaces.DoctorRepository;
import com.pluralsight.frontdesk.core.syncedaggregates.Doctor;
import com.pluralsight.frontdesk.infrastructure.data.VCF;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import pluralsightddd.sharedkernel.common.exceptions.ExceptionOrigin;
import pluralsightddd.sharedkernel.infrastructure.data.repositories.DomainReadonlyRepository;

@Dependent
public class DoctorRepositoryImpl extends DomainReadonlyRepository<Doctor> implements DoctorRepository {
    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin("Data interaction for Doctor");

    @Inject
    protected DoctorRepositoryImpl(@VCF EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected ExceptionOrigin getExceptionOrigin() {
        return EXCEPTION_ORIGIN;
    }

}
