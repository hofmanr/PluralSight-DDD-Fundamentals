package com.pluralsight.frontdesk.core.exceptions;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(Long clientID) {
        super(String.format("ClientDto with ID %d not found", clientID));
    }
}
