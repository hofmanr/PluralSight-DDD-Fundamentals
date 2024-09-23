package com.pluralsight.frontdesk.infrastructure.data.repositories;

import com.pluralsight.frontdesk.core.interfaces.AppointmentTypeRepository;
import com.pluralsight.frontdesk.core.syncedaggregates.AppointmentType;
import com.pluralsight.frontdesk.infrastructure.data.VCF;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import pluralsightddd.sharedkernel.common.exceptions.ExceptionOrigin;
import pluralsightddd.sharedkernel.infrastructure.data.repositories.DomainReadonlyRepository;

@Dependent
public class AppointmentTypeRepositoryImpl extends DomainReadonlyRepository<AppointmentType> implements AppointmentTypeRepository {
    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin("Data interaction for AppointmentType");

    @Inject
    protected AppointmentTypeRepositoryImpl(@VCF EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected ExceptionOrigin getExceptionOrigin() {
        return EXCEPTION_ORIGIN;
    }
}
