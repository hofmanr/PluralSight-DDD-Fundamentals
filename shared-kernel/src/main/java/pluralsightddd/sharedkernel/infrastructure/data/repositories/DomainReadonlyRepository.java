package pluralsightddd.sharedkernel.infrastructure.data.repositories;

import jakarta.persistence.EntityManager;
import pluralsightddd.sharedkernel.core.repositories.ReadonlyRepository;
import pluralsightddd.sharedkernel.ddd.types.AggregateRoot;

public abstract class DomainReadonlyRepository<T extends AggregateRoot> extends BaseRepository<T> implements ReadonlyRepository<T> {
    protected DomainReadonlyRepository(EntityManager entityManager) {
        super(entityManager);
    }
}
