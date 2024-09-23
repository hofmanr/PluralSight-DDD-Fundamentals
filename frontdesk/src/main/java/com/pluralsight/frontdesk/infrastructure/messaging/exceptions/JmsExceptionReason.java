package com.pluralsight.frontdesk.infrastructure.messaging.exceptions;

import pluralsightddd.sharedkernel.common.exceptions.ExceptionReason;

public class JmsExceptionReason {

    private JmsExceptionReason() {
        // Default (hidden) constructor
    }

    public static final ExceptionReason UNSUPPORTED_MESSAGE_TYPE = new ExceptionReason("Unsupported message type");
    public static final ExceptionReason RECEIVE_EXCEPTION = new ExceptionReason("Error occurred while receiving a message");
    public static final ExceptionReason SEND_EXCEPTION = new ExceptionReason("Error occurred while sending a message");
}
