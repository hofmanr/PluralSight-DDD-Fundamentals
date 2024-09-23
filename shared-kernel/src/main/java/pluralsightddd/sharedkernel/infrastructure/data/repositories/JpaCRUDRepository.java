package pluralsightddd.sharedkernel.infrastructure.data.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import pluralsightddd.sharedkernel.common.exceptions.ExceptionOrigin;
import pluralsightddd.sharedkernel.infrastructure.data.JpaEntity;
import pluralsightddd.sharedkernel.infrastructure.data.JpaExecutor;
import pluralsightddd.sharedkernel.infrastructure.data.JpaFunction;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Supplier;

import static jakarta.transaction.Transactional.TxType.REQUIRED;

public abstract class JpaCRUDRepository<T extends JpaEntity> {
    protected Class<T> clazz;

    protected EntityManager em;

    private final JpaExecutor exceptionResolver = new JpaExecutor();

    protected abstract ExceptionOrigin getExceptionOrigin();

    protected JpaCRUDRepository(EntityManager entityManager) {
        this.em = entityManager;

        Type type = getClass();
        while (type != null && !(type instanceof ParameterizedType)) {
            type = ((Class<?>) type).getGenericSuperclass();
        }

        // definition of the class should be something like: public class BookRepository extends BaseRepository<Book>
        // Argument 0 is in this case Book
        if (type != null) {
            this.clazz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
        }
    }

    public <U> T getById(U id) {
        return execute(() -> em.find(clazz, id));
    }

    public List<T> list() {
        return execute(() ->
                em.createQuery("select e from " + clazz.getSimpleName() + " e").getResultList());
    }

    public Long countAll() {
        TypedQuery<Long> query = em.createQuery("select count(e) from " + clazz.getSimpleName() + " e", Long.class);
        return execute(query::getSingleResult);
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
    public <U extends Serializable> void remove(U id) {
        if (id == null) return;
        execute(() -> {
            T storedEntity = em.getReference(clazz, id);
            em.remove(storedEntity);
            em.flush();
        });
    }

    public <U> U execute(Supplier<U> supplier) {
        return exceptionResolver.execute(supplier, getExceptionOrigin());
    }

    public void execute(JpaFunction function) {
        exceptionResolver.execute(function, getExceptionOrigin());
    }
}
