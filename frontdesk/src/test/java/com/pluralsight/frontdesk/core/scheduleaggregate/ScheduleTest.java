package com.pluralsight.frontdesk.core.scheduleaggregate;

import com.pluralsight.frontdesk.core.syncedaggregates.AppointmentType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pluralsightddd.sharedkernel.core.valueobjects.DateTimeOffset;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {
    private static final UUID scheduleId = UUID.randomUUID();
    private static final Long clientId = 10L;
    private static final Long doctorId = 11L;
    private static final Long patientId = 12L;
    private static final Long roomId = 13L;
    private static final String title = "title";
    private static final DateTimeOffset startTime = new DateTimeOffset(LocalDateTime.now());
    private static AppointmentType standardAppointment;
    private static AppointmentType extensiveAppointment;

    private static ValidatorFactory vf;
    private static Validator validator;

    private Appointment appointment;
    private Schedule schedule;

    @BeforeAll
    static void init() {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();

        standardAppointment = new AppointmentType(1L, "Standard", "STD", 30);
        extensiveAppointment = new AppointmentType(2L, "Standard", "STD", 120);
    }

    @AfterAll
    static void close() {
        vf.close();
    }

    @BeforeEach
    void initEach() {
        appointment = new Appointment(UUID.randomUUID(), scheduleId, standardAppointment, startTime,
                clientId, doctorId, patientId, roomId,
                title);
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        schedule = new Schedule(UUID.randomUUID(), 1, appointments);
    }

    @Test
    void addNewAppointmentShouldRaiseConstraintValidation() {
        schedule.addNewAppointment(appointment);

        Set<ConstraintViolation<Schedule>> violations = validator.validate(schedule);
        assertEquals(1, violations.size());
        assertEquals("cannot add duplicate appointment to schedule", violations.iterator().next().getMessage());
    }

    @Test
    void addNewAppointmentShouldNotRaiseConstraintValidation() {
        var newAppointment = new Appointment(UUID.randomUUID(), scheduleId, extensiveAppointment, startTime, 3L, 3L, 3L, 3L, "Appointment");
        schedule.addNewAppointment(newAppointment);

        Set<ConstraintViolation<Schedule>> violations = validator.validate(schedule);
        assertEquals(0, violations.size());
    }

    @Test
    void deleteAppointmentShouldRaiseConstraintValidation() throws NoSuchMethodException {
        schedule.deleteAppointment(null);

        Method method = Schedule.class.getMethod("deleteAppointment", Appointment.class);
        ExecutableValidator methodValidator = validator.forExecutables();
        Set<ConstraintViolation<Schedule>> violations = methodValidator.validateParameters(schedule, method, new Object[]{null});
        assertEquals(1, violations.size());
    }
}