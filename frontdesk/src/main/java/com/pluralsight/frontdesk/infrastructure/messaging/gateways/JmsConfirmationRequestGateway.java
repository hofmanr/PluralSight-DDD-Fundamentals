package com.pluralsight.frontdesk.infrastructure.messaging.gateways;

import com.pluralsight.frontdesk.core.models.ConfirmationRequest;
import com.pluralsight.frontdesk.core.interfaces.ConfirmationRequestGateway;
import com.pluralsight.frontdesk.infrastructure.messaging.AbstractJmsGateway;
import com.pluralsight.frontdesk.infrastructure.models.ConfirmationRequestMessage;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.jms.Queue;
import pluralsightddd.sharedkernel.architecture.clean.InfrastructureRing;

@InfrastructureRing
@Dependent
public class JmsConfirmationRequestGateway extends AbstractJmsGateway implements ConfirmationRequestGateway {
    private final Queue queue;

    @Inject
    public JmsConfirmationRequestGateway(@Confirmation Queue queue) {
        this.queue = queue;
    }

    @Override
    public void send(ConfirmationRequest request) {
        var dto = new ConfirmationRequestMessage(request);
        String jsonObject = getJsonObject(dto);
        super.sendMessage(queue, jsonObject);
    }

}
