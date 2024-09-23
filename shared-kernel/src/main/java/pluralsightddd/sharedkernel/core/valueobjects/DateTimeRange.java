package pluralsightddd.sharedkernel.core.valueobjects;

import pluralsightddd.sharedkernel.ddd.types.ValueObject;

import java.io.Serial;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class DateTimeRange extends ValueObject {
    @Serial
    private static final long serialVersionUID = 1L;

    private final LocalDateTime start;
    private final LocalDateTime end;

    public DateTimeRange(LocalDateTime start, LocalDateTime end) {
        // TODO Guard against OutOfRange with parameters (start, nameof(start), start, end);
        this.start = start;
        this.end = end;
    }

    public DateTimeRange(LocalDateTime start, Duration duration) {
        this(start, plusDuration(start, duration));
    }

    private static LocalDateTime plusDuration(LocalDateTime dateTime, Duration duration) {
        long seconds = duration == null ? 0 : duration.toSeconds();
        return dateTime.plusSeconds(seconds);
    }

    public long durationInMinutes()
    {
        return Duration.between(start, end).toMinutes();
    }

    public DateTimeRange newDuration(Duration newDuration)
    {
        return new DateTimeRange(this.start, newDuration);
    }

    public DateTimeRange newEnd(LocalDateTime newEnd)
    {
        return new DateTimeRange(this.start, newEnd);
    }

    public DateTimeRange newStart(LocalDateTime newStart)
    {
        return new DateTimeRange(newStart, this.end);
    }

    public static DateTimeRange createOneDayRange(LocalDateTime day)
    {
        return new DateTimeRange(day, day.plusDays(1));
    }

    public static DateTimeRange createOneWeekRange(LocalDateTime startDay)
    {
        return new DateTimeRange(startDay, startDay.plusDays(7));
    }

    public boolean overlaps(DateTimeRange dateTimeRange)
    {
        return this.start.isBefore(dateTimeRange.end) &&
                this.end.isAfter(dateTimeRange.start);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
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
    protected Collection<Object> getEqualityComponents() {
        return List.of(start, end);
    }

    @Override
    public String toString() {
        return "DateTimeRange{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
