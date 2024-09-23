package com.pluralsight.frontdesk.infrastructure.messaging.exceptions;

import pluralsightddd.sharedkernel.common.exceptions.AbstractException;
import pluralsightddd.sharedkernel.common.exceptions.ExceptionOrigin;
import pluralsightddd.sharedkernel.common.exceptions.ExceptionReason;

public class MessagingException extends AbstractException {

    public MessagingException(ExceptionOrigin exceptionOrigin, ExceptionReason exceptionReason, String message) {
        super(exceptionOrigin, exceptionReason, message);
    }

    public MessagingException(ExceptionOrigin exceptionOrigin, ExceptionReason exceptionReason, String message, Throwable cause) {
        super(exceptionOrigin, exceptionReason, message, cause);
    }
}
