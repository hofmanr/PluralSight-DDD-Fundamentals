package com.pluralsight.frontdesk.infrastructure.messaging.gateways;

import jakarta.annotation.Resource;
import jakarta.enterprise.inject.Produces;
import jakarta.jms.Queue;

public class ConfirmationRequestConfiguration {

    @Resource(name = "jms/vccPublicOutputQueue")
    @Produces
    @Confirmation
    private Queue confirmationRequestQueue;
}
