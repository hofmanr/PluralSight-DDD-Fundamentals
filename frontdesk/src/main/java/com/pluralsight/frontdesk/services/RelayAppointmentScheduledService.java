package com.pluralsight.frontdesk.services;

import com.pluralsight.frontdesk.core.events.AppointmentScheduled;
import com.pluralsight.frontdesk.core.exceptions.AppointmentTypeNotFoundException;
import com.pluralsight.frontdesk.core.exceptions.ClientNotFoundException;
import com.pluralsight.frontdesk.core.exceptions.DoctorNotFoundException;
import com.pluralsight.frontdesk.core.exceptions.PatientNotFoundException;
import com.pluralsight.frontdesk.core.interfaces.AppointmentTypeRepository;
import com.pluralsight.frontdesk.core.interfaces.ClientRepository;
import com.pluralsight.frontdesk.core.interfaces.ConfirmationRequestGateway;
import com.pluralsight.frontdesk.core.interfaces.DoctorRepository;
import com.pluralsight.frontdesk.core.models.ConfirmationRequest;
import com.pluralsight.frontdesk.core.syncedaggregates.Client;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import pluralsightddd.sharedkernel.ddd.types.DomainEvent;

import static jakarta.enterprise.event.TransactionPhase.AFTER_SUCCESS;

@Dependent
@Transactional(Transactional.TxType.SUPPORTS)
public class RelayAppointmentScheduledService implements DomainEvent.IHandle<AppointmentScheduled> {
    private final ClientRepository clientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentTypeRepository appointmentTypeRepository;
    private final ConfirmationRequestGateway requestGateway;
    private final Logger logger;

    @Inject
    public RelayAppointmentScheduledService(ClientRepository clientRepository,
                                            DoctorRepository doctorRepository,
                                            AppointmentTypeRepository appointmentTypeRepository,
                                            ConfirmationRequestGateway requestGateway,
                                            Logger logger) {
        this.clientRepository = clientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentTypeRepository = appointmentTypeRepository;
        this.requestGateway = requestGateway;
        this.logger = logger;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void handle(@Observes(during=AFTER_SUCCESS) AppointmentScheduled appointmentScheduled) {
        var apt = appointmentScheduled.appointmentScheduled();
        if (apt == null) return;

        ConfirmationRequest newMessage = new ConfirmationRequest();
        newMessage.appointmentId = apt.getId();
        newMessage.title = apt.getTitle();
        newMessage.start = apt.getTimeRange().getStart().getLocalDateTime();
        newMessage.end = apt.getTimeRange().getEnd().getLocalDateTime();

        Client client = clientRepository.getById(apt.getClientId());
        if (client == null) throw new ClientNotFoundException(apt.getClientId());
        newMessage.clientName = client.getPreferredName();
        newMessage.patientName = client.getPatientName(apt.getPatientId());
        if (newMessage.patientName == null) throw new PatientNotFoundException(apt.getPatientId());

        var doctor = doctorRepository.getById(apt.getDoctorId());
        if (doctor == null) throw new DoctorNotFoundException(apt.getDoctorId());
        newMessage.doctorName = doctor.getName();

        var appointmentType = appointmentTypeRepository.getById(apt.getAppointmentTypeId());
        if (appointmentType == null) throw new AppointmentTypeNotFoundException(apt.getAppointmentTypeId());
        newMessage.appointmentType = appointmentType.getName();

        requestGateway.send(newMessage);
        logger.info("Message published for patient {}",  newMessage.patientName);
    }
}
