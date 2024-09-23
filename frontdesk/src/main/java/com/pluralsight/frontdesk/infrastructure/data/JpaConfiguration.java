package com.pluralsight.frontdesk.infrastructure.data;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Dependent
public class JpaConfiguration {

    @Produces
    @VCF
    @PersistenceContext(unitName = "vetClinicFrontDeskPU")
    private EntityManager entityManagerFrontDesk;

    @Produces
    @VCS
    @PersistenceContext(unitName = "vetClinicSyncPU")
    private EntityManager entityManagerSync;
}
