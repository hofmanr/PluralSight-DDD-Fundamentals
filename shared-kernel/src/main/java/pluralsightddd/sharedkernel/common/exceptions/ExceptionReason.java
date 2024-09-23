package pluralsightddd.sharedkernel.common.exceptions;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public record ExceptionReason(String reason) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public ExceptionReason {
        Objects.requireNonNull(reason, "The reason should not be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExceptionReason that = (ExceptionReason) o;
        return reason.equals(that.reason);
    }

    @Override
    public String toString() {
        return "ExceptionReason{" +
                "reason='" + reason + '\'' +
                '}';
    }
}
