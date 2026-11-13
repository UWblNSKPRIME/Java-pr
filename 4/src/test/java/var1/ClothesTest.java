package var1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClothesTest {
    @Test
    void constructorAndSetters() {
        Clothes clothes = new Clothes(" Shirt ", " M ", 200);
        assertEquals("Shirt", clothes.getName());
        assertEquals("M", clothes.getSize());
        assertEquals(200.0, clothes.getPrice());
        clothes.setName("Coat");
        clothes.setSize("L");
        clothes.setPrice(500);
        assertEquals("Coat", clothes.getName());
        assertEquals("L", clothes.getSize());
        assertEquals(500.0, clothes.getPrice());
    }

    @Test
    void equalityAndText() {
        Clothes first = new Clothes("Shirt", "M", 200);
        Clothes second = new Clothes("Shirt", "M", 200);
        assertEquals(first, first);
        assertEquals(first, second);
        assertEquals(second, first);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, new Clothes("Coat", "M", 200));
        assertNotEquals(first, new Clothes("Shirt", "L", 200));
        assertNotEquals(first, new Clothes("Shirt", "M", 100));
        assertNotEquals(first, null);
        assertNotEquals(first, "Shirt");
        assertEquals("Clothes{name='Shirt', size='M', price=200.0}", first.toString());
    }

    @Test
    void invalidFieldsAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Clothes("", "M", 1));
        assertThrows(IllegalArgumentException.class, () -> new Clothes("Shirt", " ", 1));
        assertThrows(IllegalArgumentException.class, () -> new Clothes("Shirt", "M", -1));
        assertThrows(IllegalArgumentException.class, () -> new Clothes("Shirt", "M", Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> new Clothes("Shirt", "M", Double.POSITIVE_INFINITY));
        Clothes clothes = new Clothes("Shirt", "M", 1);
        assertThrows(IllegalArgumentException.class, () -> clothes.setName(null));
        assertThrows(IllegalArgumentException.class, () -> clothes.setSize(null));
        assertThrows(IllegalArgumentException.class, () -> clothes.setPrice(-1));
        assertEquals(1.0, clothes.getPrice());
    }
}
