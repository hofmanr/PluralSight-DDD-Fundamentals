package com.pluralsight.frontdesk.infrastructure.data.repositories;

import com.pluralsight.frontdesk.core.syncedaggregates.Client;
import com.pluralsight.frontdesk.infrastructure.data.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pluralsightddd.sharedkernel.core.repositories.ReadonlyRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientDtoRepositoryTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    public static void initAll() {
        emf = Persistence.createEntityManagerFactory("vetClinicTestPU");
        em = emf.createEntityManager();
    }

    @AfterAll
    public static void tearDown() {
        emf.close();
    }

    @Test
    void testAdd() throws Exception {
        em.getTransaction().begin();

        ReadonlyRepository<Client> repo = new ClientRepositoryImpl(em);

        // 1. add new clients
        JpaUtil util = new JpaUtil(em);
        util.executeSQLCommand("insert into CLIENTS (id, version, name, salutation, email) " +
                "values (1L, 1, 'John Smit', 'Dear Mr', 'J.smit@email.com')");
        util.executeSQLCommand("insert into PATIENTS (id, client_id, sex, name, species) " +
                "values (10L, 1, 'Male', 'My Pet', 'buldog')");
        util.executeSQLCommand("insert into PATIENTS (id, client_id, sex, name, species) " +
                "values (11L, 1, 'Female', 'Petty', 'st bernhard')");
        util.executeSQLCommand("insert into CLIENTS (id, version, name, salutation, email) " +
                "values (2L, 1, 'Valerie Jones', 'Dear Mrs', 'mjones@email.com')");
        util.checkData("select * from CLIENTS");

        // 2. end transaction & start new one
        em.getTransaction().commit();

        // 3. clear the JPA cache
        em.clear();

        List<Client> clients = repo.list(); // waarom worden hier aparte queries uitgevoerd en bij Scheulde niet?
        assertEquals(2, clients.size());

        Client firstClient = repo.getById(1L);
        assertEquals(2, firstClient.getPatients().size());
    }
}