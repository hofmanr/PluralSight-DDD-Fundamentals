package com.pluralsight.frontdesk.infrastructure.messaging;

import com.pluralsight.frontdesk.infrastructure.messaging.exceptions.JmsExceptionReason;
import com.pluralsight.frontdesk.infrastructure.messaging.exceptions.MessagingException;
import jakarta.jms.*;
import pluralsightddd.sharedkernel.common.exceptions.ExceptionOrigin;

import java.nio.charset.StandardCharsets;

public abstract class AbstractJmsListener implements MessageListener {
     protected static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin("Error receiving message");

    protected String getPayload(Message msg) {
        try {
            if (msg instanceof TextMessage textMessage) {
                return textMessage.getText();
            }
            if (msg instanceof BytesMessage bytesMessage) {
                byte[] data = new byte[(int) bytesMessage.getBodyLength()];
                return new String(data, StandardCharsets.UTF_16);
            }
        } catch (JMSException ex) {
            throw new MessagingException(EXCEPTION_ORIGIN, JmsExceptionReason.RECEIVE_EXCEPTION, ex.getMessage(), ex);
        }
        throw new MessagingException(EXCEPTION_ORIGIN, JmsExceptionReason.UNSUPPORTED_MESSAGE_TYPE, "Message type is not supported");
    }

}
