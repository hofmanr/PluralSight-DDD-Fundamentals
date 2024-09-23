package com.pluralsight.frontdesk.infrastructure.data.repositories;

import com.pluralsight.frontdesk.TestEvent;
import com.pluralsight.frontdesk.core.interfaces.ScheduleRepository;
import com.pluralsight.frontdesk.core.scheduleaggregate.Appointment;
import com.pluralsight.frontdesk.core.scheduleaggregate.Schedule;
import com.pluralsight.frontdesk.core.syncedaggregates.AppointmentType;
import com.pluralsight.frontdesk.infrastructure.data.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import pluralsightddd.sharedkernel.core.valueobjects.DateTimeOffset;
import pluralsightddd.sharedkernel.common.exceptions.ExceptionOrigin;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleRepositoryImplTest {
    private static final ExceptionOrigin EXCEPTION_ORIGIN = new ExceptionOrigin("Data interaction for Schedule");

    private static final Integer CLINIC_ID = 1;
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static final DateTimeOffset startTime = new DateTimeOffset(LocalDateTime.now());
    private static AppointmentType standardAppointment;

    private ScheduleRepository repo;
    private UUID scheduleId;

    @BeforeAll
    public static void setupAll() throws Exception {
        emf = Persistence.createEntityManagerFactory("vetClinicTestPU");
        em = emf.createEntityManager();

        standardAppointment = new AppointmentType(1L,"Standard", "STD", 30);

        em.getTransaction().begin();
        // Add doctors, rooms, clients, patients, appointment types
        JpaUtil util = new JpaUtil(em);
        util.executeSQLCommand("insert into DOCTORS (id, version, name) values (1L, 1, 'Dr. Jones')");
        util.executeSQLCommand("insert into DOCTORS (id, version, name) values (2L, 1, 'Dr. Bernard')");
        util.executeSQLCommand("insert into CLIENTS (id, version, name, email) values (1L, 1, 'John Smit', 'J.smit@email.com')");
        util.executeSQLCommand("insert into CLIENTS (id, version, name, email) values (2L, 1, 'Valerie Jones', 'mjones@email.com')");
        util.executeSQLCommand("insert into PATIENTS (id, client_id, sex, name, species) values (1L, 1L, 'Male', 'My Pet', 'buldog')");
        util.executeSQLCommand("insert into PATIENTS (id, client_id, sex, name, species) values (3L, 2L, 'Female', 'Doggy', 'st bernhard')");
        util.executeSQLCommand("insert into ROOMS (id, version, name) values (1L, 1, 'Room 1')");
        util.executeSQLCommand("insert into ROOMS (id, version, name) values (2L, 1, 'Room 2')");
        util.executeSQLCommand("insert into APPOINTMENT_TYPES (id, version, minutes, code, name) values (1L, 1, 30, 'STD', 'Standard')");
        util.executeSQLCommand("insert into APPOINTMENT_TYPES (id, version, minutes, code, name) values (2L, 1, 60, 'EXT', 'Extended')");
        em.getTransaction().commit();
    }

    @AfterAll
    public static void tearDownAll() {
        em.close();
        emf.close();
    }


    @BeforeEach
    public void setupEach() {
        repo = new ScheduleRepositoryImpl(em, new TestEvent());

        em.getTransaction().begin();

        // 1. add new schedule
        scheduleId = UUID.randomUUID();
        Schedule schedule = new Schedule(scheduleId, CLINIC_ID);
        schedule = repo.add(schedule);

        // 2. add an appointments
        Appointment appointment = new Appointment(UUID.randomUUID(), scheduleId, standardAppointment, startTime, 1L, 1L, 1L, 1L, "Appointment 1");
        schedule.addNewAppointment(appointment);
        repo.alter(schedule);

        repo.saveChanges(schedule);
        em.getTransaction().commit();
    }

    @AfterEach
    public void tearDownEach() {
        em.getTransaction().begin();
        em.clear();
        Schedule schedule = repo.getByClinic(CLINIC_ID);
        if (schedule != null) {
            repo.remove(schedule);
        }
        em.getTransaction().commit();
    }

    @Test
    void testAddAppointment() {
        em.getTransaction().begin();

        // 1 Add one appointment
        Schedule schedule = repo.getByClinic(CLINIC_ID);
        Appointment appointment3 = new Appointment(UUID.randomUUID(), scheduleId, standardAppointment, startTime, 2L, 1L, 3L, 2L, "Title");
        schedule.addNewAppointment(appointment3);
        repo.alter(schedule);

        // 2. end transaction & start new one
        repo.saveChanges(schedule);
        em.getTransaction().commit();
        // 3. clear the JPA cache
        em.clear();

        // 4. find schedule with appointments
        schedule = repo.getByClinic(CLINIC_ID);
        assertNotNull(schedule);
        assertEquals(2, schedule.getAppointments().size());
        assertEquals(scheduleId, schedule.getId());
        assertEquals(1, schedule.getClinicId());
    }

    @Test
    void testModifyAppointment() {
        em.getTransaction().begin();

        Schedule schedule = repo.getByClinic(CLINIC_ID);

        // 1. Change appointment
        Appointment appointment = schedule.getAppointments().get(0);
        appointment.updateTitle("Hello World");
        appointment.updateDoctor(2L);
        appointment.updateRoom(2L); // 4L

        AppointmentType appointmentType = new AppointmentType(2L, "Extended", "EXT", 60);
        appointment.updateAppointmentType(appointmentType, null);

        LocalDateTime newStartDateTime = LocalDateTime.now().plusMinutes(15);
        LocalDateTime confirmed = LocalDateTime.now();
        DateTimeOffset newStartTime = new DateTimeOffset(newStartDateTime);
        appointment.updateStartTime(newStartTime, null);
        appointment.confirm(new DateTimeOffset(confirmed));

        repo.alter(schedule);

        // 2. end transaction & start new one
        repo.saveChanges(schedule);
        em.getTransaction().commit();

        // 3. clear the JPA cache
        em.clear();

        // 4. find schedule with appointments
        schedule = repo.getByClinic(CLINIC_ID);
        assertNotNull(schedule);
        assertEquals(1, schedule.getAppointments().size());
        Appointment appointment1 = schedule.getAppointments().get(0);
        assertEquals("Hello World", appointment1.getTitle());
        assertEquals(2L, appointment1.getDoctorId());
        assertEquals(2L, appointment1.getRoomId());
        assertEquals(60, appointment1.getTimeRange().durationInMinutes());
        assertEquals(newStartTime, appointment1.getTimeRange().getStart());
        assertNotNull(appointment1.getDateTimeConfirmed());
    }

    @Test
    void testDeleteAppointment() {
        em.getTransaction().begin();

        Schedule schedule = repo.getByClinic(CLINIC_ID);

        // 1. Delete appointment
        Appointment appointment = schedule.getAppointments().get(0);
        schedule.deleteAppointment(appointment);
        repo.alter(schedule);

        // 2. end transaction & start new one
        repo.saveChanges(schedule);
        em.getTransaction().commit();

        // 3. clear the JPA cache
        em.clear();

        // 4. find schedule with appointments
        schedule = repo.getByClinic(CLINIC_ID);
        assertNotNull(schedule);
        assertEquals(0, schedule.getAppointments().size());
    }

//    @Test
//    void testRoomNotFoundViolation() {
//        DataAccessException dataAccessException = null;
//        try {
//            em.getTransaction().begin();
//
//            // Add appointment with non-existing room
//            Schedule schedule = repo.getByClinic(CLINIC_ID);
//            Appointment appointment3 = new Appointment(UUID.randomUUID(), scheduleId, standardAppointment, startTime, 2L, 1L, 3L, 20L, "Title");
//            schedule.addNewAppointment(appointment3);
//            repo.alter(schedule);
//
//            repo.saveChanges(schedule);
//        } catch (DataAccessException ex) {
//            dataAccessException = ex;
//        } finally {
//            em.getTransaction().commit();
//        }
//
//        assertNotNull(dataAccessException);
//        assertEquals("Data interaction for Schedule", dataAccessException.getOrigin().origin());
//        assertEquals(JpaExceptionReason.CONSTRAINT_VIOLATION, dataAccessException.getReason());
//        assertTrue(dataAccessException.getMessage().contains("Referential integrity constraint violation"));
//    }

}