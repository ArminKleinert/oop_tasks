package u8;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Armin Kleinert
 */
public class MugUnitTest {

    @Test
    public void testMugConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Mug<>(null, 0));
        assertThrows(IllegalArgumentException.class, () -> new Mug<>(new Water(), -1));

        Water water = new Water();
        int cap = 0;
        Mug<Liquid> mug = new Mug<>(water, cap);
        assertSame(water, mug.getLiquid());
        assertEquals(cap, mug.getCapacity());
        assertEquals(0, mug.getAmount());
    }

    @Test
    public void testMugGenerity() {
        Mug<Liquid> mug = new Mug<>(new Water(), 0);
        assertTrue(mug.getLiquid() instanceof Liquid);
        assertTrue(mug.getLiquid() instanceof Water);

        mug = new Mug<>(new CoughSyrup(), 0);
        assertTrue(mug.getLiquid() instanceof Liquid);
        assertTrue(mug.getLiquid() instanceof CoughSyrup);
    }

    @Test
    public void testPour() {
        Mug<Water> mug = new Mug<>(new Water(), 500);
        assertEquals(mug.getCapacity(), 500);
        assertEquals(mug.getAmount(), 0);

        assertDoesNotThrow(() -> mug.pour(0));
        assertDoesNotThrow(() -> mug.pour(100));
        assertDoesNotThrow(() -> mug.pour(400));
        assertEquals(500, mug.getAmount());

        assertThrows(NotEnoughCapacityException.class, () -> mug.pour(1));
        assertEquals(mug.getAmount(), 500);

        Mug<?> mug1 = new Mug<>(new Water(), 0);
        assertEquals(0, mug1.getAmount());
        assertEquals(0, mug1.getCapacity());
        assertDoesNotThrow(() -> mug1.pour(0));
        assertThrows(NotEnoughCapacityException.class, () -> mug1.pour(1));
        assertEquals(0, mug1.getAmount());
    }

    @Test
    public void testTakeOut() {
        Mug<Water> mug = new Mug<>(new Water(), 500);
        assertEquals(mug.getCapacity(), 500);
        assertEquals(mug.getAmount(), 0);

        assertThrows(NotEnoughLiquidException.class, () -> mug.takeOut(1));
        assertDoesNotThrow(() -> mug.takeOut(0));

        assertDoesNotThrow(() -> mug.pour(100));
        assertEquals(mug.getAmount(), 100);
        assertDoesNotThrow(() -> mug.takeOut(99));
        assertEquals(mug.getAmount(), 1);
        assertThrows(NotEnoughLiquidException.class, () -> mug.takeOut(2));
        assertEquals(mug.getAmount(), 1);
    }

    @Test
    public void testDrink() {
        Mug<Water> mug = new Mug<>(new Water(), 500);
        assertDoesNotThrow(() -> mug.pour(500));
        assertEquals(500, mug.getAmount());
        assertDoesNotThrow(() -> mug.drink(500));
        assertEquals(0, mug.getAmount());
        assertThrows(NotEnoughLiquidException.class, () -> mug.drink(1));

        Mug<CoughSyrup> mug1 = new Mug<>(new CoughSyrup(), 500);
        assertDoesNotThrow(() -> mug1.pour(500));
        assertEquals(500, mug1.getAmount());
        assertThrows(UndrinkableException.class, () -> mug1.drink(500));
        assertEquals(500, mug1.getAmount());
    }

    @Test
    public void testEmpty() {
        Mug<Water> mug = new Mug<>(new Water(), 500);

        assertEquals(0, mug.getAmount());
        assertEquals(0, mug.empty());
        assertEquals(0, mug.getAmount());

        assertDoesNotThrow(() -> mug.pour(500));
        assertEquals(500, mug.getAmount());
        assertEquals(500, mug.empty());
        assertEquals(0, mug.getAmount());
    }

    @Test
    public void testIsEmpty() throws NotEnoughCapacityException {
        Mug<Water> mug = new Mug<>(new Water(), 500);

        assertTrue(mug.isEmpty());
        mug.pour(500);
        assertFalse(mug.isEmpty());
        mug.empty();
        assertTrue(mug.isEmpty());
    }

    @Test
    public void testIsHot() {
        Water water = new Water();

        Mug<Water> mug = new Mug<>(water, 500);
        assertFalse(mug.isHot());
        mug.getLiquid().hitUp(1);
        assertFalse(mug.isHot());
        mug.getLiquid().hitUp(10000);
        assertTrue(mug.isHot());

        mug = new Mug<>(water, 500);
        assertTrue(mug.isHot());
    }
}
