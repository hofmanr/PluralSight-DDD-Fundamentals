package pluralsightddd.sharedkernel.infrastructure.data.repositories;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import pluralsightddd.sharedkernel.core.repositories.CRUDRepository;
import pluralsightddd.sharedkernel.ddd.types.AggregateRoot;

import java.io.Serializable;

import static jakarta.transaction.Transactional.TxType.REQUIRED;

public abstract class DomainCRUDRepository<T extends AggregateRoot> extends BaseRepository<T> implements CRUDRepository<T> {

    protected DomainCRUDRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Transactional(REQUIRED)
    public T add(T entity) {
        if (entity == null) return null;
        return execute(() -> {
            em.persist(entity);
            em.flush();
            return entity;
        });
    }

    @Transactional(REQUIRED)
    public T alter(T entity) {
        if (entity == null) return null;
        return execute(() -> {
            T storedEntity = em.merge(entity);
            em.flush();
            return storedEntity;
        });
    }

    @Transactional(REQUIRED)
    public void remove(T entity) {
        execute(() -> {
            em.remove(entity);
            em.flush();
        });
    }

    @Transactional(REQUIRED)
    public <I extends Serializable> void remove(I id) {
        if (id == null) return;
        execute(() -> {
            T storedEntity = em.getReference(clazz, id);
            em.remove(storedEntity);
            em.flush();
        });
    }
}
