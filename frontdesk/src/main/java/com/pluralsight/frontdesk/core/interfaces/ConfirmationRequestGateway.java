package com.pluralsight.frontdesk.core.interfaces;

import com.pluralsight.frontdesk.core.models.ConfirmationRequest;

public interface ConfirmationRequestGateway {

    void send(ConfirmationRequest request);
}
