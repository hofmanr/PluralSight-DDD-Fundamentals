package pluralsightddd.sharedkernel.core.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pluralsightddd.sharedkernel.core.valueobjects.DateTimeOffsetRange;

public class OutOfRangeValidator implements ConstraintValidator<OutOfRange, DateTimeOffsetRange> {
    @Override
    public void initialize(OutOfRange constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(DateTimeOffsetRange dateTimeOffsetRange, ConstraintValidatorContext constraintValidatorContext) {
        if (dateTimeOffsetRange == null) return true;

        // Start should be less or equal to end
        return (dateTimeOffsetRange.getStart().compareTo(dateTimeOffsetRange.getEnd()) <= 0);
    }
}
