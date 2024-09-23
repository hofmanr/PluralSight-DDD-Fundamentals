package com.pluralsight.frontdesk.infrastructure.data.repositories;

import com.pluralsight.frontdesk.core.interfaces.ScheduleRepository;
import com.pluralsight.frontdesk.core.scheduleaggregate.Schedule;
import com.pluralsight.frontdesk.infrastructure.data.VCF;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import pluralsightddd.sharedkernel.ddd.types.DomainEvent;
import pluralsightddd.sharedkernel.infrastructure.data.exception.DataAccessException;
import pluralsightddd.sharedkernel.common.exceptions.ExceptionOrigin;
import pluralsightddd.sharedkernel.infrastructure.data.repositories.DomainCRUDRepository;

import java.util.List;

@Dependent
public class ScheduleRepositoryImpl extends DomainCRUDRepository<Schedule> implements ScheduleRepository {
    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin("Data interaction for Schedule");

    private final Event<DomainEvent> domainEvent;

    @Inject
    public ScheduleRepositoryImpl(@VCF EntityManager entityManager, Event<DomainEvent> domainEvent) {
        super(entityManager);
        this.domainEvent = domainEvent;
    }

    @Override
    public Schedule getByClinic(Integer clinicId) {
        try {
            List<Schedule> schedules = em.createNamedQuery("Schedule.findByClinic", Schedule.class)
                    .setParameter("clinic", clinicId)
                    .getResultList();
            return schedules.isEmpty() ? null : schedules.get(0);
        } catch (RuntimeException ex) {
            throw new DataAccessException(EXCEPTION_ORIGIN, ex);
        }
    }

    // Fire all events
    @Override
    public void saveChanges(Schedule entity) {
        entity.getEvents().forEach(domainEvent::fire);
        entity.getEvents().clear();

        entity.getAppointments().forEach(a -> {
            a.getEvents().forEach(domainEvent::fire);
            a.getEvents().clear();
        });
    }


    @Override
    public ExceptionOrigin getExceptionOrigin() {
        return EXCEPTION_ORIGIN;
    }

//    @Override
//    public <U> void remove(U id) {
//        super.remove((Schedule) id);
//    }
}
