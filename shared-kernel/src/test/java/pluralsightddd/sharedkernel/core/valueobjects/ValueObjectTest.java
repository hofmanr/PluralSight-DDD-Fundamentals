package pluralsightddd.sharedkernel.core.valueobjects;

import org.junit.jupiter.api.Test;
import pluralsightddd.sharedkernel.ddd.types.ValueObject;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValueObjectTest {

    private static class MyValueObject extends ValueObject {
        private final String val1;
        private final Integer val2;
        private final LocalDate val3;

        public MyValueObject(String val1, Integer val2, LocalDate val3) {
            this.val1 = val1;
            this.val2 = val2;
            this.val3 = val3;
        }

        @Override
        protected Collection<Object> getEqualityComponents() {
            return List.of(val1, val2, val3);
        }
    }

    private final LocalDate date = LocalDate.of(2024,3,30);
    private final MyValueObject object1 = new MyValueObject("Hello", 10, date);
    private final MyValueObject object2 = new MyValueObject("Hello", 10, date);
    private final MyValueObject object3 = new MyValueObject("Hola", 10, date);
    private final MyValueObject object4 = new MyValueObject("Hello", 20, date);
    private final MyValueObject object5 = new MyValueObject("Hello", 10, date.plusDays(20));
    private final ValueObject object6 = new ValueObject() {
        @Override
        protected Collection<Object> getEqualityComponents() {
            String val = "Hello World";
            return List.of(val);
        }
    };

    @Test
    void testEquals() {
        assertEquals(object1, object2);
        assertNotEquals(object1, object3);
        assertNotEquals(object1, object4);
        assertNotEquals(object1, object5);
        assertNotEquals(object1, object6);
    }

    @Test
    void testHashCode() {
        int hashObj1 = object1.hashCode();
        int hashObj2 = object2.hashCode();
        int hashObj3 = object3.hashCode();
        int hashObj4 = object4.hashCode();
        int hashObj5 = object5.hashCode();
        int hashObj6 = object6.hashCode();

        assertEquals(hashObj1, hashObj2);
        assertNotEquals(hashObj1, hashObj3);
        assertNotEquals(hashObj1, hashObj4);
        assertNotEquals(hashObj1, hashObj5);
        assertNotEquals(hashObj1, hashObj6);
    }

    @Test
    void testCompareTo() {
        assertEquals(0, object1.compareTo(object2));
        assertEquals(-1, object1.compareTo(object3));
        assertEquals(-1, object1.compareTo(object4));
        assertEquals(-1, object1.compareTo(object5));
        assertEquals(1, object1.compareTo(object6));
    }
}