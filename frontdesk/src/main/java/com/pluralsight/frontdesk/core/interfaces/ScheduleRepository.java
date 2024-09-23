package com.pluralsight.frontdesk.core.interfaces;

import com.pluralsight.frontdesk.core.scheduleaggregate.Schedule;
import pluralsightddd.sharedkernel.common.exceptions.ExceptionOrigin;
import pluralsightddd.sharedkernel.core.repositories.CRUDRepository;


public interface ScheduleRepository extends CRUDRepository<Schedule> {
    Schedule getByClinic(Integer clinicId);

    // Fire all events
    void saveChanges(Schedule entity);

    ExceptionOrigin getExceptionOrigin();

}
