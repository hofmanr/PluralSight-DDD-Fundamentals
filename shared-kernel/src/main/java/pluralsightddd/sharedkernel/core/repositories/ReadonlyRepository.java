package pluralsightddd.sharedkernel.core.repositories;

import pluralsightddd.sharedkernel.ddd.types.AggregateRoot;
import pluralsightddd.sharedkernel.ddd.types.DomainRepository;

import java.util.List;

public interface ReadonlyRepository<T extends AggregateRoot> extends DomainRepository<T> {
    <I> T getById(I id);
    List<T> list();
    Long countAll();
}
