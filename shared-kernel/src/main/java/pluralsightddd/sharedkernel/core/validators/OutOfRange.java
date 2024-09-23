package pluralsightddd.sharedkernel.core.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OutOfRangeValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER}) // TYPE is class level
@Retention(RetentionPolicy.RUNTIME)
public @interface OutOfRange {

    String message() default "{vetClinic.validation.constraints.OutOfRange.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
