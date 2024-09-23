package com.pluralsight.frontdesk.core.exceptions;

public class DuplicateAppointmentException extends RuntimeException {
    public DuplicateAppointmentException(String message, String argumentName) {
        super(String.format("%s [%s is not unique]", message, argumentName));
    }
}
