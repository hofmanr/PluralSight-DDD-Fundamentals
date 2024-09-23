package com.pluralsight.frontdesk.core.exceptions;

public class AppointmentTypeNotFoundException extends RuntimeException {
    public AppointmentTypeNotFoundException(Long appointmentTypeID) {
        super(String.format("AppointmentType with ID %d not found", appointmentTypeID));
    }
}
