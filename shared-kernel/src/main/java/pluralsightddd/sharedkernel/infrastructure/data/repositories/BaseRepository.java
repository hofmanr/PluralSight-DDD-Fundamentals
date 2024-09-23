package pluralsightddd.sharedkernel.infrastructure.data.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import pluralsightddd.sharedkernel.infrastructure.data.JpaExecutor;
import pluralsightddd.sharedkernel.infrastructure.data.JpaFunction;
import pluralsightddd.sharedkernel.common.exceptions.ExceptionOrigin;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Supplier;

public abstract class BaseRepository<T> {
    protected Class<T> clazz;

    protected EntityManager em;

    private final JpaExecutor exceptionResolver = new JpaExecutor();

    protected abstract ExceptionOrigin getExceptionOrigin();

    protected BaseRepository(EntityManager entityManager) {
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

    public <I> T getById(I id) {
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

    protected void setClazz(final Class<T> clazz) {
        this.clazz = clazz;
    }

    public <U> U execute(Supplier<U> supplier) {
        return exceptionResolver.execute(supplier, getExceptionOrigin());
    }

    public void execute(JpaFunction function) {
        exceptionResolver.execute(function, getExceptionOrigin());
    }
}
