package com.pluralsight.frontdesk.infrastructure.messaging.facades;

import com.pluralsight.frontdesk.core.events.AppointmentConfirmed;
import com.pluralsight.frontdesk.infrastructure.messaging.AbstractJmsListener;
import com.pluralsight.frontdesk.infrastructure.messaging.exceptions.JmsExceptionReason;
import com.pluralsight.frontdesk.infrastructure.messaging.exceptions.MessagingException;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import org.slf4j.Logger;
import pluralsightddd.sharedkernel.architecture.clean.InfrastructureRing;
import pluralsightddd.sharedkernel.core.valueobjects.DateTimeOffset;

import java.util.UUID;

@InfrastructureRing
@MessageDriven(name = "PublicMessageListener",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType",
                        propertyValue = "jakarta.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination",
                        propertyValue = "jms/vccPublicInputQueue"),
                @ActivationConfigProperty(
                        propertyName = "connectionFactoryLookup",
                        propertyValue = "jms/vetClinicCF"
                )
        }
)
public class VetClinicPublicJMSService extends AbstractJmsListener {

    private Event<AppointmentConfirmed> confirmationReceivedEvent;
    private Logger logger;

    public VetClinicPublicJMSService() {
        // A JMS listener needs a public constructor
    }

    @Inject
    public void setConfirmationReceivedEvent(Event<AppointmentConfirmed> confirmationReceivedEvent) {
        this.confirmationReceivedEvent = confirmationReceivedEvent;
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
        logger.info("doc = {}", doc);
        String eventType = doc.getStringProperty("eventType");
        if ("confirmationResponse".equalsIgnoreCase(eventType)) {
            handleConfirmationEvent(doc);
            return;
        }

        throw new MessagingException(EXCEPTION_ORIGIN, JmsExceptionReason.UNSUPPORTED_MESSAGE_TYPE, "Unknown message type: " + eventType);
    }

    private void handleConfirmationEvent(JsonDocument doc) {
        UUID appointmentId = UUID.fromString(doc.getStringProperty("appointmentId"));
        DateTimeOffset dateTimeConfirmation = new DateTimeOffset(doc.getStringProperty("dateTimeEventOccurred"));
        var event = new AppointmentConfirmed(appointmentId, dateTimeConfirmation);

        confirmationReceivedEvent.fire(event);
    }

}

