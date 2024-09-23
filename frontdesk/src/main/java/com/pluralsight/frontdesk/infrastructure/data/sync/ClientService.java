package com.pluralsight.frontdesk.infrastructure.data.sync;

import com.pluralsight.frontdesk.infrastructure.data.sync.entities.Client;
import com.pluralsight.frontdesk.infrastructure.data.sync.model.ClientDto;
import com.pluralsight.frontdesk.infrastructure.data.sync.repositories.ClientRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.slf4j.Logger;

import java.util.Objects;

@Dependent
public class ClientService {
    private final Logger logger;
    private final ClientRepository clientRepository;

    @Inject
    public ClientService(Logger logger, ClientRepository clientRepository) {
        this.logger = logger;
        this.clientRepository = clientRepository;
    }

    public void add(ClientDto clientDto) {
        logger.info("Create Client: {}", clientDto);
        // The ClientDto is the synced-client!
        Client client = new Client(clientDto.id(), clientDto.name());
        client.setSalutation(clientDto.salutation());
        client.setEmailAddress(clientDto.emailAddress());
        client.setPreferredDoctorId(clientDto.preferredDoctorId());

        clientRepository.add(client);
    }

    public void update(ClientDto clientDto) {
        logger.info("Update Client: {}", clientDto);
        // The ClientDto is the synced-client!
        Client client = clientRepository.getById(clientDto.id());
        if (client == null) return;

        if (!Objects.equals(client.getPreferredName(), clientDto.name())) {
            logger.debug("Update name");
            client.setPreferredName(clientDto.name());
        }
        if (!Objects.equals(client.getEmailAddress(), clientDto.emailAddress())) {
            logger.debug("Update emailAddress");
            client.setEmailAddress(clientDto.emailAddress());
        }
        if (!Objects.equals(client.getSalutation(), clientDto.salutation())) {
            logger.debug("Update salutation");
            client.setSalutation(clientDto.salutation());
        }
        if (!Objects.equals(client.getPreferredDoctorId(), clientDto.preferredDoctorId())) {
            logger.debug("Update doctorId");
            client.setPreferredDoctorId(clientDto.preferredDoctorId());
        }

        clientRepository.alter(client);
    }
}
