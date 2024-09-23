package com.pluralsight.frontdesk.core.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DuplicateAppointmentValidator.class)
@Target(ElementType.TYPE) // class level
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicateAppointment {

    String message() default "{vetClinic.validation.constraints.DuplicateAppointment.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
