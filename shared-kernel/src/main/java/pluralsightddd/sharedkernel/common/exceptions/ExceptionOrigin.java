package pluralsightddd.sharedkernel.common.exceptions;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public record ExceptionOrigin(String origin) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public ExceptionOrigin {
        Objects.requireNonNull(origin, "The origin should not be null");
    }
}
