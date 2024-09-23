package pluralsightddd.sharedkernel.core.valueobjects;

import jakarta.validation.constraints.NotNull;
import pluralsightddd.sharedkernel.core.validators.OutOfRange;
import pluralsightddd.sharedkernel.core.validators.PositiveDuration;
import pluralsightddd.sharedkernel.ddd.types.ValueObject;

import java.io.Serial;
import java.time.Duration;
import java.util.Collection;
import java.util.List;

// DateTimeOffset is UTC dateTime
@OutOfRange
@PositiveDuration
public class DateTimeOffsetRange extends ValueObject {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    private DateTimeOffset start;

    @NotNull
    private DateTimeOffset end;


    protected DateTimeOffsetRange() {
    }

    public DateTimeOffsetRange(DateTimeOffset start, DateTimeOffset end)
    {
        this.start = start;
        this.end = end;
    }

    public DateTimeOffsetRange(DateTimeOffset start, Duration duration) {
        this(start, start == null ? null : start.addMinutes(duration));
    }

    public DateTimeOffset getStart() {
        return start;
    }

    public DateTimeOffset getEnd() {
        return end;
    }

    public long durationInMinutes()
    {
        return Duration.between(start.getUtcDateTime(), end.getUtcDateTime()).toMinutes();
    }

    public boolean isAllDay() {
        long durationInMinutes = durationInMinutes();
        return durationInMinutes >+ 8 * 60; // 8 hours
    }

    public DateTimeOffsetRange newDuration(Duration newDuration)
    {
        return new DateTimeOffsetRange(this.start, newDuration);
    }

    public DateTimeOffsetRange newEnd(DateTimeOffset newEnd)
    {
        return new DateTimeOffsetRange(this.start, newEnd);
    }

    public DateTimeOffsetRange newStart(DateTimeOffset newStart)
    {
        return new DateTimeOffsetRange(newStart, this.end);
    }

    public static DateTimeOffsetRange createOneDayRange(DateTimeOffset day)
    {
        return new DateTimeOffsetRange(day, day.plusDays(1));
    }

    public static DateTimeOffsetRange createOneWeekRange(DateTimeOffset startDay)
    {
        return new DateTimeOffsetRange(startDay, startDay.plusDays(7));
    }

    public boolean overlaps(DateTimeOffsetRange dateTimeRange)
    {
        return this.start.isBefore(dateTimeRange.end) &&
                this.end.isAfter(dateTimeRange.start);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "DateTimeOffsetRange{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

//    @Transient
    @Override
    protected Collection<Object> getEqualityComponents() {
        return List.of(start, end);
    }
}
