package pluralsightddd.sharedkernel.core.valueobjects;

import java.time.LocalDateTime;

public class DateTimeOffsetRangeBuilder {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public static DateTimeOffsetRangeBuilder builder() {
        return new DateTimeOffsetRangeBuilder();
    }

    private DateTimeOffsetRangeBuilder() {
    }

    public DateTimeOffsetRangeBuilder withStart(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public DateTimeOffsetRangeBuilder withEnd(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    public DateTimeOffsetRange build() {
        if (startDateTime == null || endDateTime == null) {
            return null;
        }

        DateTimeOffset startOffset = new DateTimeOffset(startDateTime);
        DateTimeOffset endOffset = new DateTimeOffset(endDateTime);
        return new DateTimeOffsetRange(startOffset, endOffset);
    }
}
