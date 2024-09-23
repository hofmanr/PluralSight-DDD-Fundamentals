package pluralsightddd.sharedkernel.core.valueobjects;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeOffsetRangeTest {

    private static ValidatorFactory vf;
    private static Validator validator;

    @BeforeAll
    static void init() {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    @AfterAll
    static void close() {
        vf.close();
    }

    @Test
    void noConstraintValidationErrors() {
        DateTimeOffset start = new DateTimeOffset(LocalDateTime.now());
        DateTimeOffset end = new DateTimeOffset(LocalDateTime.now().plusMinutes(2));
        DateTimeOffsetRange range = new DateTimeOffsetRange(start, end);

        Set<ConstraintViolation<DateTimeOffsetRange>> violations = validator.validate(range);
        assertEquals(0, violations.size());
    }


    @Test
    void startAfterEndShouldRaiseConstraintValidation() {
        DateTimeOffset start = new DateTimeOffset(LocalDateTime.now());
        DateTimeOffset end = new DateTimeOffset(LocalDateTime.now().minusMinutes(30));
        DateTimeOffsetRange range = new DateTimeOffsetRange(start, end);

        Set<ConstraintViolation<DateTimeOffsetRange>> violations = validator.validate(range);
        assertEquals(1, violations.size());
        ConstraintViolation<DateTimeOffsetRange> violation = violations.iterator().next();
        String constraintName = violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
        String message = violation.getMessage();
        assertEquals("OutOfRange", constraintName);
        assertEquals("the start must be before the end", message);
    }

    @Test
    void startEqualsEndShouldRaiseConstraintValidation() {
        DateTimeOffset start = new DateTimeOffset(LocalDateTime.now());
        DateTimeOffsetRange range = new DateTimeOffsetRange(start, start);

        Set<ConstraintViolation<DateTimeOffsetRange>> violations = validator.validate(range);
        assertEquals(1, violations.size());
        ConstraintViolation<DateTimeOffsetRange> violation = violations.iterator().next();
        String constraintName = violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
        String message = violation.getMessage();
        assertEquals("PositiveDuration", constraintName);
        assertEquals("the duration must be greater than zero", message);
    }

}