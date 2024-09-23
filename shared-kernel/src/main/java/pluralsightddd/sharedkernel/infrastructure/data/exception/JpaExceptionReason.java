package pluralsightddd.sharedkernel.infrastructure.data.exception;

import pluralsightddd.sharedkernel.common.exceptions.ExceptionReason;

public class JpaExceptionReason {
    public static final ExceptionReason ILLEGAL_STATE = new ExceptionReason("Database in in an illegal state");

    public static final ExceptionReason DUPLICATE_KEY = new ExceptionReason("Entity with Id already exists");
    public static final ExceptionReason ENTITY_EXISTS = new ExceptionReason("Entity already exists");
    public static final ExceptionReason LOCK_TIMEOUT = new ExceptionReason("Lock timeout");
    public static final ExceptionReason NON_UNIQUE_RESULT = new ExceptionReason("Non-unique result, unique result expected");
    public static final ExceptionReason NO_RESULT = new ExceptionReason("No results/records found");
    public static final ExceptionReason OPTIMISTIC_LOCK = new ExceptionReason("Optimistic lock");
    public static final ExceptionReason PESSIMISTIC_LOCK = new ExceptionReason("Pessimistic lock");
    public static final ExceptionReason QUERY_TIMEOUT = new ExceptionReason("Timeout while querying for data");
    public static final ExceptionReason CONSTRAINT_VIOLATION = new ExceptionReason("Constraint violation");
    public static final ExceptionReason NO_DATA_FOUND = new ExceptionReason("No data found");
    public static final ExceptionReason GENERAL_ORM_EXCEPTION = new ExceptionReason("Problem with EntityManager accessing the database");
    public static final ExceptionReason UNKNOWN = new ExceptionReason("Unknown exception");

    private JpaExceptionReason() {
    }
}
