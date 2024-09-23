package com.pluralsight.frontdesk.infrastructure.messaging;

import com.pluralsight.frontdesk.infrastructure.messaging.exceptions.JmsExceptionReason;
import com.pluralsight.frontdesk.infrastructure.messaging.exceptions.MessagingException;
import jakarta.annotation.Resource;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSRuntimeException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import pluralsightddd.sharedkernel.common.exceptions.ExceptionOrigin;

import java.util.ArrayList;

public abstract class AbstractJmsGateway {
    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin("Error receiving message");

    @Resource(name="jms/vetClinicCF")
    private ConnectionFactory cf;

    protected void sendMessage(Destination destination, String payload) {
        try (JMSContext jmsContext = cf.createContext()) {
            var message = jmsContext.createTextMessage(payload);
            jmsContext.createProducer().send(destination, message);
        } catch (JMSRuntimeException e) {
            Throwable rootCause = getRootCause(e);
            String errorMessage = rootCause == null ? "Unknown root cause" : rootCause.getMessage();
            throw new MessagingException(EXCEPTION_ORIGIN, JmsExceptionReason.SEND_EXCEPTION, errorMessage, e);
        }
    }

    protected String getJsonObject(Object request) {
        try (Jsonb jsonb = JsonbBuilder.create()) {
            return jsonb.toJson(request);
        } catch (Exception e) {
            throw new MessagingException(EXCEPTION_ORIGIN, JmsExceptionReason.UNSUPPORTED_MESSAGE_TYPE, "Message is not a JSON object", e);
        }
    }


    private Throwable getRootCause(Throwable throwable) {
        ArrayList<Throwable> list;
        for (list = new ArrayList<>(); throwable != null && !list.contains(throwable); throwable = throwable.getCause()) {
            list.add(throwable);
        }
        return list.isEmpty() ? null : list.get(list.size() - 1);
    }
}
