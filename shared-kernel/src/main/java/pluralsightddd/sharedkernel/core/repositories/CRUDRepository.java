package pluralsightddd.sharedkernel.core.repositories;

import pluralsightddd.sharedkernel.ddd.types.AggregateRoot;

public interface CRUDRepository<T extends AggregateRoot> extends ReadonlyRepository<T> {
    T add(T entity);

    T alter(T entity);

    void remove(T entity);
}
