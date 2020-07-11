package u8;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MugTest {

    @Test
    private void testMugConstructor() {
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
    private void testMugGenerity() {
        Mug<Liquid> mug = new Mug<>(new Water(), 0);
        assertTrue(mug.getLiquid() instanceof Liquid);
        assertTrue(mug.getLiquid() instanceof Water);

        mug = new Mug<>(new CoughSyrup(), 0);
        assertTrue(mug.getLiquid() instanceof Liquid);
        assertTrue(mug.getLiquid() instanceof CoughSyrup);
    }

    @Test
    private void testPour() {
        Mug<Water> mug = new Mug<>(new Water(), 500);
        assertEquals(mug.getCapacity(), 500);
        assertEquals(mug.getAmount(), 0);

        assertEquals(mug.getAmount(), 0);
        assertDoesNotThrow(() -> mug.pour(0));
        assertDoesNotThrow(() -> mug.pour(100));
        assertDoesNotThrow(() -> mug.pour(400));
        assertEquals(mug.getAmount(), 500);

        assertThrows(NotEnoughCapacityException.class, () -> mug.pour(1));
        assertEquals(mug.getAmount(), 500);

        Mug<?> mug1 = new Mug<>(new Water(), 0);
        assertEquals(mug.getAmount(), 0);
        assertEquals(mug.getCapacity(),0);
        assertDoesNotThrow(() -> mug1.pour(0));
        assertThrows(NotEnoughCapacityException.class, () -> mug1.pour(1));
        assertEquals(mug.getAmount(), 0);
    }

    @Test
    private void testTakeOut() throws NotEnoughCapacityException {
        Mug<Water> mug = new Mug<>(new Water(), 500);
        assertEquals(mug.getCapacity(), 500);
        assertEquals(mug.getAmount(), 0);

        assertThrows(NotEnoughLiquidException.class, () -> mug.takeOut(1));
        assertDoesNotThrow(() -> mug.takeOut(0));

            mug.pour(100);
        assertEquals(mug.getAmount(), 100);
        assertDoesNotThrow(() -> mug.takeOut(99));
        assertEquals(mug.getAmount(), 1);
        assertThrows(NotEnoughLiquidException.class, () -> mug.takeOut(2));
        assertEquals(mug.getAmount(), 1);
    }

    @Test
    private void testDrink() throws NotEnoughCapacityException {
        Mug<Water> mug = new Mug<>(new Water(), 500);
        mug.pour(500);
        assertEquals(mug.getAmount(), 500);
        assertDoesNotThrow(() -> mug.drink(500));
        assertEquals(mug.getAmount(), 0);
        assertThrows(NotEnoughLiquidException.class, () -> mug.drink(1));

        Mug<CoughSyrup> mug1 = new Mug<>(new CoughSyrup(), 500);
        mug1.pour(500);
        assertEquals(mug.getAmount(), 500);
        assertThrows(UndrinkableException.class, () -> mug1.drink(500));
        assertEquals(mug.getAmount(), 500);
    }

    @Test
    private void testEmpty() throws NotEnoughCapacityException {
        Mug <Water> mug = new Mug<>(new Water(), 500);

        assertEquals(mug.getAmount(), 0);
        assertEquals(mug.empty(), 0);
        assertEquals(mug.getAmount(), 0);

        mug.pour(500);
        assertEquals(mug.getAmount(), 500);
        assertEquals(mug.empty(), 500);
        assertEquals(mug.getAmount(), 0);
    }

    @Test
    private void testIsEmpty() throws NotEnoughCapacityException {
        Mug<Water> mug = new Mug<>(new Water(), 500);

        assertTrue(mug.isEmpty());
        mug.pour(500);
        assertFalse(mug.isEmpty());
        mug.empty();
        assertTrue(mug.isEmpty());
    }

    @Test
    private void testIsHot() {
        Water water = new Water();

        Mug<Water> mug = new Mug<> (water, 500);
        assertFalse(mug.isHot());
        mug.getLiquid().hitUp(1);
        assertFalse(mug.isHot());
        mug.getLiquid().hitUp(10000);
        assertTrue(mug.isHot());

        mug = new Mug<>(water, 500);
        assertTrue(mug.isHot());
    }
}
