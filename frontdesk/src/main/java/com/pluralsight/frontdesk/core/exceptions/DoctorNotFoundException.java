package com.pluralsight.frontdesk.core.exceptions;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(Long doctorID) {
        super(String.format("Doctor with ID %d not found", doctorID));
    }
}
