package pluralsightddd.sharedkernel.core.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pluralsightddd.sharedkernel.core.valueobjects.DateTimeOffsetRange;

public class PositiveDurationValidator implements ConstraintValidator<PositiveDuration, DateTimeOffsetRange> {
    @Override
    public void initialize(PositiveDuration constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(DateTimeOffsetRange dateTimeOffsetRange, ConstraintValidatorContext constraintValidatorContext) {
        if (dateTimeOffsetRange == null) return true;
        return dateTimeOffsetRange.durationInMinutes() != 0L;
    }
}
