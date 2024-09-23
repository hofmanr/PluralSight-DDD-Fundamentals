package com.pluralsight.frontdesk;

//import com.pluralsight.frontdesk.core.events.AppointmentScheduledEvent;
//import com.pluralsight.frontdesk.core.handlers.RelayAppointmentScheduledService;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.NotificationOptions;
import jakarta.enterprise.util.TypeLiteral;
import pluralsightddd.sharedkernel.ddd.types.DomainEvent;

import java.lang.annotation.Annotation;
import java.util.concurrent.CompletionStage;

public class TestEvent implements Event<DomainEvent> {

    @Override
    public void fire(DomainEvent event) {
//        if (event instanceof AppointmentScheduledEvent rasEvent) {
//            RelayAppointmentScheduledService ras = new RelayAppointmentScheduledService();
//            ras.handle(rasEvent);
//        }
    }

    @Override
    public <U extends DomainEvent> CompletionStage<U> fireAsync(U u) {
        return null;
    }

    @Override
    public <U extends DomainEvent> CompletionStage<U> fireAsync(U u, NotificationOptions notificationOptions) {
        return null;
    }

    @Override
    public Event<DomainEvent> select(Annotation... annotations) {
        return null;
    }

    @Override
    public <U extends DomainEvent> Event<U> select(Class<U> aClass, Annotation... annotations) {
        return null;
    }

    @Override
    public <U extends DomainEvent> Event<U> select(TypeLiteral<U> typeLiteral, Annotation... annotations) {
        return null;
    }
}
