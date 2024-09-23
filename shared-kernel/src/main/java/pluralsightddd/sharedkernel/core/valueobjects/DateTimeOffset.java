package pluralsightddd.sharedkernel.core.valueobjects;

import pluralsightddd.sharedkernel.ddd.types.ValueObject;

import java.io.Serial;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

public class DateTimeOffset extends ValueObject {
    @Serial
    private static final long serialVersionUID = 1L;

    private LocalDateTime utcDateTime;

    protected DateTimeOffset() {
        // Default constructor
    }

    public DateTimeOffset(LocalDateTime dateTime) {
        this(dateTime, false);
    }

    public DateTimeOffset(LocalDateTime dateTime, boolean isUTC) {
        this.utcDateTime = isUTC ? dateTime : convertToUtc(dateTime);
    }

    public DateTimeOffset(int year, int month, int day, int hour, int minute, int second) {
        this(year, month, day, hour, minute, second, false);
    }

    public DateTimeOffset(int year, int month, int day, int hour, int minute, int second, boolean isUTC) {
        this(LocalDateTime.of(year, month, day, hour, minute, second), isUTC);
    }

    // e.g. "2001-04-14 14:30:45"
    public DateTimeOffset(String dateTimeString) {
        this(toLocalDateTime(dateTimeString));
    }

    private static LocalDateTime toLocalDateTime(String dateTimeString) {
        String str = dateTimeString.replace("T", " ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(str, formatter);
    }

    public LocalDateTime getUtcDateTime() {
        return utcDateTime;
    }

    public LocalDateTime getLocalDateTime() {
        return convertToLocal(utcDateTime);
    }

    public DateTimeOffset addMinutes(Duration duration) {
        long minutes = duration == null ? 0 : duration.toMinutes();
        return new DateTimeOffset(this.utcDateTime.plusMinutes(minutes), true);
    }

    public DateTimeOffset plusDays(long days) {
        return new DateTimeOffset(this.utcDateTime.plusDays(days), true);
    }

    public boolean isBefore(DateTimeOffset dateTime) {
        return this.utcDateTime.isBefore(dateTime.utcDateTime);
    }

    public boolean isAfter(DateTimeOffset dateTime) {
        return this.utcDateTime.isAfter(dateTime.utcDateTime);
    }

    private static LocalDateTime convertToUtc(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }

    private static LocalDateTime convertToLocal(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
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
        return "DateTimeOffset{" +
                "utcDateTime=" + utcDateTime +
                '}';
    }

    @Override
//    @Transient
    protected Collection<Object> getEqualityComponents() {
        return List.of(utcDateTime);
    }
}
