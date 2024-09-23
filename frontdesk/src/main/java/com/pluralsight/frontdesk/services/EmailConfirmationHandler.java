package com.pluralsight.frontdesk.services;

import com.pluralsight.frontdesk.core.events.AppointmentConfirmed;
import com.pluralsight.frontdesk.core.interfaces.ApplicationSettings;
import com.pluralsight.frontdesk.core.interfaces.ScheduleRepository;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import pluralsightddd.sharedkernel.ddd.types.DomainEvent;

import java.util.UUID;

/**
 * This handler responds to incoming messages saying a user has confirmed an appointment
 */
public class EmailConfirmationHandler implements DomainEvent.IHandle<AppointmentConfirmed> {
    private final ScheduleRepository scheduleRepository;
    private final ApplicationSettings settings;
    private final Logger logger;

    @Inject
    public EmailConfirmationHandler(ScheduleRepository scheduleRepository, ApplicationSettings settings, Logger logger) {
        this.scheduleRepository = scheduleRepository;
        this.settings = settings;
        this.logger = logger;
    }

    @Override
    public void handle(@Observes AppointmentConfirmed appointmentConfirmed) {
        UUID appointmentId = appointmentConfirmed.appointmentId();
        logger.info("Handling appointment confirmation: {}",  appointmentConfirmed.appointmentId());

        // Note: In this demo this only works for appointments scheduled on TestDate (settings.ClinicId, _settings.TestDate)
        var schedule = scheduleRepository.getByClinic(settings.getClinicId());

        assert schedule != null;
        var appointmentToConfirm = schedule.getAppointment(appointmentId);

        assert appointmentToConfirm != null;
        appointmentToConfirm.confirm(appointmentConfirmed.dateTimeConfirmation());

        scheduleRepository.alter(schedule);
        scheduleRepository.saveChanges(schedule);
    }
}

