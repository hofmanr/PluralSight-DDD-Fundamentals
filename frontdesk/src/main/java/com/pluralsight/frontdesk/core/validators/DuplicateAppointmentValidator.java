package com.pluralsight.frontdesk.core.validators;

import com.pluralsight.frontdesk.core.scheduleaggregate.Appointment;
import com.pluralsight.frontdesk.core.scheduleaggregate.Schedule;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class DuplicateAppointmentValidator implements ConstraintValidator<DuplicateAppointment, Schedule> {
    @Override
    public void initialize(DuplicateAppointment constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    // Because an (DDD) entity is identified by the ID, we can not override equals and hasCode
    // If we could override equals and hasCode, the code would be
//    @Override
//    public boolean isValid(Schedule schedule, ConstraintValidatorContext constraintValidatorContext) {
//        // Or simply schedule.getAppointments().contains(appointment)?
//        Set<Appointment> uniques = new HashSet<>();
//        for (Appointment appointment : schedule.getAppointments()) {
//            if (!uniques.add(appointment)) {
//                return false;
//            }
//        }
//
//        return true;
//    }

    @Override
    public boolean isValid(Schedule schedule, ConstraintValidatorContext constraintValidatorContext) {
        List<Appointment> uniques = new ArrayList<>();
        for (Appointment appointment : schedule.getAppointments()) {
            if (isDuplicate(appointment, uniques)) {
                return false;
            }
            uniques.add(appointment);
        }

        return true;
    }

    private boolean isDuplicate(Appointment appointment, List<Appointment> appointments) {
        return appointments.stream().anyMatch(a -> a.duplicates(appointment));
    }
}
