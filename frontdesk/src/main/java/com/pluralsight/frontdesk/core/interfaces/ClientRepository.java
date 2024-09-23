package com.pluralsight.frontdesk.core.interfaces;

import com.pluralsight.frontdesk.core.syncedaggregates.Client;
import pluralsightddd.sharedkernel.core.repositories.ReadonlyRepository;

public interface ClientRepository extends ReadonlyRepository<Client> {
}
