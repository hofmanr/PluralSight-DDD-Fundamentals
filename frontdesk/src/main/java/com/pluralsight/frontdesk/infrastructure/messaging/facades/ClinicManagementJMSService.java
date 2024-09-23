package com.pluralsight.frontdesk.infrastructure.messaging.facades;

import com.pluralsight.frontdesk.infrastructure.data.sync.ClientService;
import com.pluralsight.frontdesk.infrastructure.data.sync.DoctorService;
import com.pluralsight.frontdesk.infrastructure.data.sync.model.ClientDto;
import com.pluralsight.frontdesk.infrastructure.data.sync.model.DoctorDto;
import com.pluralsight.frontdesk.infrastructure.messaging.AbstractJmsListener;
import com.pluralsight.frontdesk.infrastructure.messaging.exceptions.JmsExceptionReason;
import com.pluralsight.frontdesk.infrastructure.messaging.exceptions.MessagingException;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import org.slf4j.Logger;
import pluralsightddd.sharedkernel.architecture.clean.InfrastructureRing;

@InfrastructureRing
@MessageDriven(name = "ManagementMessageListener",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType",
                        propertyValue = "jakarta.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination",
                        propertyValue = "jms/vccManagementQueue"),
                @ActivationConfigProperty(
                        propertyName = "connectionFactoryLookup",
                        propertyValue = "jms/vetClinicCF"
                )
        }
)
public class ClinicManagementJMSService extends AbstractJmsListener {

    private ClientService clientService;
    private DoctorService doctorService;
    private Logger logger;

    public ClinicManagementJMSService() {
        // A JMS listener needs a public constructor
    }

    @Inject
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Inject
    public void setDoctorService(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Inject
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void onMessage(Message message) {
        try {
            String payload = getPayload(message);
            handleMessage(payload);
        } catch (MessagingException ex) {
            String errorString = String.format("""
Service exception with
   > origin : %s" +
   > reason : %s" +
   > cause  : %s" +
   > message: %s""",
                    ex.getOrigin().origin(), 
                    ex.getReason().reason(), 
                    (ex.getCause() == null ? "Unknown" : ex.getCause().getMessage()), 
                    ex.getMessage());
            logger.error(errorString);
        } catch (Exception ex) {
            logger.error(String.valueOf(JmsExceptionReason.UNSUPPORTED_MESSAGE_TYPE), ex);
            throw new MessagingException(EXCEPTION_ORIGIN, JmsExceptionReason.UNSUPPORTED_MESSAGE_TYPE, "onMessage error", ex);
        }
    }

    private void handleMessage(String message)
    {
        JsonDocument doc = JsonDocument.parse(message);
        logger.info("Handling message: {}", doc);
        String eventType = doc.getStringProperty("eventType");
        if ("doctorCreated".equalsIgnoreCase(eventType)) {
            doctorService.add(getDoctor(doc));
            return;
        }
        if ("clientCreated".equalsIgnoreCase(eventType)) {
            clientService.add(getClient(doc));
            return;
        }
        if ("clientUpdated".equalsIgnoreCase(eventType)) {
            clientService.update(getClient(doc));
            return;
        }

        throw new MessagingException(EXCEPTION_ORIGIN, JmsExceptionReason.UNSUPPORTED_MESSAGE_TYPE, "Unknown message type: " + eventType);
    }

    private DoctorDto getDoctor(JsonDocument doc) {
        var entity = doc.getInnerDocument("entity");
        Long id = Long.valueOf(entity.getIntProperty("id"));
        String name = entity.getStringProperty("name");
        return new DoctorDto(id, name);
    }

    private ClientDto getClient(JsonDocument doc) {
        var entity = doc.getInnerDocument("entity");
        Long id = Long.valueOf(entity.getIntProperty("id"));
        String name = entity.getStringProperty("name");
        String salutation = entity.getStringProperty("salutation");
        Integer preferredDoctorId = entity.getIntProperty("preferredDoctorId");
        String emailAddress = entity.getStringProperty("emailAddress");
        return new ClientDto(
                id,
                name,
                salutation,
                preferredDoctorId == null ? null : Long.valueOf(preferredDoctorId),
                emailAddress);
    }
}

