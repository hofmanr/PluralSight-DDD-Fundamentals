package pluralsightddd.sharedkernel.infrastructure.data;

import jakarta.enterprise.context.Dependent;
import jakarta.validation.constraints.NotNull;
import pluralsightddd.sharedkernel.infrastructure.data.exception.DataAccessException;
import pluralsightddd.sharedkernel.common.exceptions.ExceptionOrigin;

import java.util.function.Supplier;

@Dependent
public class JpaExecutor {
    public <U> U execute(Supplier<U> supplier, @NotNull(message = "{vetClinic.validation.NotNull.origin}") ExceptionOrigin exceptionOrigin) {
        try {
            return supplier == null ? null : supplier.get();
        } catch (RuntimeException e) {
            throw new DataAccessException(exceptionOrigin, e);
        }
    }

    public void execute(JpaFunction function, @NotNull(message = "{bookstore.validation.NotNull.origin}") ExceptionOrigin exceptionOrigin) {
        execute(() -> {
            function.exec();
            return null;
        }, exceptionOrigin);
    }
}
