package com.pluralsight.frontdesk.core.exceptions;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(Long patientID) {
        super(String.format("Patient with ID %d not found", patientID));
    }
}
