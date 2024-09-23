package pluralsightddd.sharedkernel.infrastructure.data.exception;

import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import pluralsightddd.sharedkernel.common.exceptions.AbstractException;
import pluralsightddd.sharedkernel.common.exceptions.ExceptionOrigin;
import pluralsightddd.sharedkernel.common.exceptions.ExceptionReason;

import java.util.ArrayList;

public class DataAccessException extends AbstractException {

    public DataAccessException(ExceptionOrigin exceptionOrigin, RuntimeException exception) {
        super(exceptionOrigin, determineReason(exception), getRootCause(exception), exception);
    }

    private static String getRootCause(Throwable throwable) {
        ArrayList<Throwable> list;
        for (list = new ArrayList<>(); throwable != null && !list.contains(throwable); throwable = throwable.getCause()) {
            list.add(throwable);
        }
        return list.isEmpty() ? null : list.get(list.size() - 1).getMessage();
    }


    private static ExceptionReason determineReason(RuntimeException exception) {
        // Patterns in switch are available in Java 21 (this is Java 17)
        String className = exception.getClass().getSimpleName();
        if (exception instanceof LockTimeoutException ||
                className.equalsIgnoreCase("LockTimeoutException")) {
            return JpaExceptionReason.LOCK_TIMEOUT;
        }
        if (exception instanceof EntityExistsException ||
                className.equalsIgnoreCase("EntityExistsException")) {
            return JpaExceptionReason.ENTITY_EXISTS;
        }
        if (exception instanceof EntityNotFoundException ||
                className.equalsIgnoreCase("EntityNotFoundException")) {
            return JpaExceptionReason.NO_DATA_FOUND;
        }
        if (exception instanceof PessimisticLockException ||
                className.equalsIgnoreCase("PessimisticLockException")) {
            return JpaExceptionReason.PESSIMISTIC_LOCK;
        }
        if (exception instanceof OptimisticLockException ||
                className.equalsIgnoreCase("OptimisticLockException")) {
            return JpaExceptionReason.OPTIMISTIC_LOCK;
        }
        if (exception instanceof NoResultException ||
                className.equalsIgnoreCase("NoResultException")) {
            return JpaExceptionReason.NO_RESULT;
        }
        if (exception instanceof NonUniqueResultException ||
                className.equalsIgnoreCase("NonUniqueResultException")) {
            return JpaExceptionReason.NON_UNIQUE_RESULT;
        }
        if (exception instanceof ConstraintViolationException ||
                className.equalsIgnoreCase("ConstraintViolationException")) {
            return JpaExceptionReason.CONSTRAINT_VIOLATION;
        }
        if (exception instanceof PersistenceException) {
            String upperMsg = exception.getMessage().toUpperCase();
            if (upperMsg.contains("DUPLICATE KEY") || exception.getMessage().contains("DUPLICATEKEY")) {
                return JpaExceptionReason.DUPLICATE_KEY;
            } else {
                return JpaExceptionReason.GENERAL_ORM_EXCEPTION;
            }
        }
        return JpaExceptionReason.UNKNOWN;
    }
}
