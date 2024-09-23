package pluralsightddd.sharedkernel.ddd.types;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

public abstract class ValueObject implements Serializable, Comparable<ValueObject> {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer cachedHashCode;

    protected abstract Collection<Object> getEqualityComponents();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ValueObject that = (ValueObject) obj;
        Collection<Object> thisCollection = getEqualityComponents();
        Collection<Object> thatCollection = that.getEqualityComponents();
        if (thisCollection == null || thatCollection == null) return false;
        return thisCollection.equals(thatCollection); // same order
    }

    @Override
    public int hashCode() {
        if (cachedHashCode == null) {
            cachedHashCode = getEqualityComponents().stream()
                    .filter(Objects::nonNull)
                    .map(Objects::hashCode)
                    .reduce(0, (a, b) -> a * 23 + b);
        }
        return cachedHashCode;
    }

    @Override
    public int compareTo(ValueObject obj) {
        String thisType = getType(this);
        String otherType = getType(obj);

        if (!thisType.equals(otherType))
            return Integer.compare(thisType.compareTo(otherType), 0); // Always return -1, 0 or 1

        Object[] components = getEqualityComponents().toArray();
        Object[] otherComponents = obj.getEqualityComponents().toArray();

        for (int i = 0; i < components.length; i++)
        {
            int comparison = compareComponents(components[i], otherComponents[i]);
            if (comparison != 0)
                return comparison;
        }

        return 0;
    }

    private int compareComponents(Object object1, Object object2)
    {
        if (object1 == null && object2 == null) {
            return 0;
        }

        if (object1 == null) {
            return -1;
        }

        if (object2 == null) {
            return 1;
        }

        if (implementsInterface(object1, Comparable.class) && implementsInterface(object2, Comparable.class)) {
            Comparable<Object> comparable1 = (Comparable<Object>) object1;
            Comparable<Object> comparable2 = (Comparable<Object>) object2;
            return Integer.compare(comparable1.compareTo(comparable2), 0); // Always return -1, 0 or 1
        }

        return object1.equals(object2) ? 0 : -1;
    }

    private static boolean implementsInterface(Object object, Class<?> interf){
        return interf.isInstance(object);
    }

    private static String getType(Object obj) {
        return obj.getClass().getTypeName();
    }
}
