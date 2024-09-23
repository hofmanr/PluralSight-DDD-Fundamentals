package com.pluralsight.frontdesk.infrastructure.data.repositories;

import com.pluralsight.frontdesk.core.interfaces.ClientRepository;
import com.pluralsight.frontdesk.core.syncedaggregates.Client;
import com.pluralsight.frontdesk.infrastructure.data.VCF;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import pluralsightddd.sharedkernel.common.exceptions.ExceptionOrigin;
import pluralsightddd.sharedkernel.infrastructure.data.repositories.DomainReadonlyRepository;

@Dependent
public class ClientRepositoryImpl extends DomainReadonlyRepository<Client> implements ClientRepository {
    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin("Data interaction for ClientDto");

    @Inject
    protected ClientRepositoryImpl(@VCF EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected ExceptionOrigin getExceptionOrigin() {
        return EXCEPTION_ORIGIN;
    }

}
