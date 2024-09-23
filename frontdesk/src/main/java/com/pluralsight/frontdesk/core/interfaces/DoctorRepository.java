package com.pluralsight.frontdesk.core.interfaces;

import com.pluralsight.frontdesk.core.syncedaggregates.Doctor;
import pluralsightddd.sharedkernel.core.repositories.ReadonlyRepository;

public interface DoctorRepository extends ReadonlyRepository<Doctor> {
}
