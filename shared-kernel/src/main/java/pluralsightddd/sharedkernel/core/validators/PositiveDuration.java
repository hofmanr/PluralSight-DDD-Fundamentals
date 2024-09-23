package pluralsightddd.sharedkernel.core.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PositiveDurationValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveDuration {

    String message() default "{vetClinic.validation.constraints.PositiveDuration.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
