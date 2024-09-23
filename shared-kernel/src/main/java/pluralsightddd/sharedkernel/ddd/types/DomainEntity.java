package pluralsightddd.sharedkernel.ddd.types;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class DomainEntity<I> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected I id;
    protected int version;  // The version of the entity; used for optimistic locking (don't make it accessible)
    
    protected List<DomainEvent> events = new ArrayList<>();

    public I getId() {
        return id;
    }

    public List<DomainEvent> getEvents() {
        return events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (DomainEntity<I>) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id) * 41; // a prime number
    }
}
