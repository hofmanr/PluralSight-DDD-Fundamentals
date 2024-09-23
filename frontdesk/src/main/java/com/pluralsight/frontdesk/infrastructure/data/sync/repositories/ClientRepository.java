package com.pluralsight.frontdesk.infrastructure.data.sync.repositories;

import com.pluralsight.frontdesk.infrastructure.data.sync.entities.Client;
import com.pluralsight.frontdesk.infrastructure.data.VCS;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import pluralsightddd.sharedkernel.common.exceptions.ExceptionOrigin;
import pluralsightddd.sharedkernel.infrastructure.data.repositories.JpaCRUDRepository;

public class ClientRepository extends JpaCRUDRepository<Client> {
    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin("Data interaction for ClientDto");

    @Inject
    protected ClientRepository(@VCS EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected ExceptionOrigin getExceptionOrigin() {
        return EXCEPTION_ORIGIN;
    }
}
