package com.pluralsight.frontdesk.core.scheduleaggregate;

import com.pluralsight.frontdesk.core.syncedaggregates.AppointmentType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pluralsightddd.sharedkernel.core.valueobjects.DateTimeOffset;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    private static final UUID SCHEDULE_ID = UUID.randomUUID();
    private static final Long CLIENT_ID = 10L;
    private static final Long DOCTOR_ID = 11L;
    private static final Long PATIENT_ID = 12L;
    private static final Long ROOM_ID = 13L;
    private static final String TITLE = "title";
    private static final DateTimeOffset START_TIME = new DateTimeOffset(LocalDateTime.now());
    private static AppointmentType standardAppointment;

    private static ValidatorFactory vf;
    private static Validator validator;

    private Appointment appointment;

    @BeforeAll
    static void init() {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();

        standardAppointment = new AppointmentType(1L, "Standard", "STD", 30);
    }

    @AfterAll
    static void close() {
        vf.close();
    }

    @BeforeEach
    void initEach() {
        appointment = new Appointment(UUID.randomUUID(), SCHEDULE_ID, standardAppointment, START_TIME,
                CLIENT_ID, DOCTOR_ID, PATIENT_ID, ROOM_ID,
                TITLE);
    }


    @Test
    void constructorShouldRaiseConstraintValidationForClientAndTimeRange() {
        var newAppointment = new Appointment(UUID.randomUUID(), SCHEDULE_ID, standardAppointment, null,
                -1L, 1L, 1L, 1L,
                "title");

        Set<ConstraintViolation<Appointment>> violations = validator.validate(newAppointment);
        assertEquals(2, violations.size());
        List<String> messages = violations.stream().map(ConstraintViolation::getMessage).toList();
        assertTrue(messages.contains("the time range is mandatory"));
        assertTrue(messages.contains("the ID of the client must be a positive number"));
    }


//    @Test
//    void setPotentiallyConflicting() {
//    }

    @Test
    void updateRoomShouldRaiseConstraintValidation() {
        appointment.updateRoom(-4L);

        Set<ConstraintViolation<Appointment>> violations = validator.validate(appointment);
        assertEquals(1, violations.size());
        assertEquals("the ID of the room must be a positive number", violations.iterator().next().getMessage());
    }

    @Test
    void updateDoctorShouldRaiseConstraintValidation() {
        appointment.updateDoctor(-4L);

        Set<ConstraintViolation<Appointment>> violations = validator.validate(appointment);
        assertEquals(1, violations.size());
        assertEquals("the ID of the doctor must be a positive number", violations.iterator().next().getMessage());
    }

//    @Test
//    void updateStartTime() {
//    }
//
//    @Test
//    void updateTitle() {
//    }
//
//    @Test
//    void updateAppointmentType() {
//    }
//
//    @Test
//    void confirm() {
//    }
//
//    @Test
//    void testUpdateAppointmentType() {
//    }
}