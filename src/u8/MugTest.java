package u8;

/**
 * This is the test-class which does not use junit for testing.<br>
 * It has a bunch of supporting methods which throw {@link RuntimeException} on failure.<br>
 * If this happens, the programmer can see what exactly failed on the stacktrace.
 *
 * Actually, you should use the MugUnitTest class in the test directory.<br>
 * We only made this one to show that we know how try-catch works.
 *
 * @author Maria Stange, Armin Kleinert
 */
public class MugTest {

    /**
     * Test-interface for a runnable.<br>
     * Javas "Runnable" interface aims for functional purity and can't throw.<br>
     * We could not import "Executable" outside of unit-test-classes (which is intended for testing exceptions).<br>
     * <p>
     * Since this interface has only 1 method, it can be written in lambda-form.
     */
    public interface MyRunnable {
        void run() throws Exception;
    }

    // SECTION Methods to support testing. All of these throw RuntimeException on failure.

    /**
     * Test if a boolean is true. If not, throw a RuntimeException.
     *
     * @param b
     * @throws RuntimeException on failure
     */
    public static void assertTrue(boolean b) {
        if (!b) {
            throw new RuntimeException();
        }
    }

    /**
     * Test if a boolean is false. If it is true, throw a RuntimeException.
     *
     * @param b
     * @throws RuntimeException on failure
     */
    public static void assertFalse(boolean b) {
        if (b) {
            throw new RuntimeException();
        }
    }

    /**
     * Test if two integers are equaal. If not, throw a RuntimeException.
     *
     * @param o1
     * @param
     * @throws RuntimeException on failure
     */
    public static void assertEquals(int o1, int o2) {
        if (o1 != o2) {
            throw new RuntimeException();
        }
    }

    /**
     * Test if a {@link MyRunnable} throws a specific type of exception.<br>
     * If it does not throw, a RuntimeException is thrown.<br>
     * If it does throw, but the type is wrong, throw a RuntimeException.
     * <p>
     * This method can be called as follows:<br>
     * {@code assertThrows(NullPointerException.class, () -> "".indexOf(null))}<br>
     * Where NullPointerException is the requested type and the lambda is the expression to run.
     *
     * @param exceptionClass
     * @param runnable
     * @throws RuntimeException on failure
     */
    public static void assertThrows(Class<? extends Throwable> exceptionClass, MyRunnable runnable) {
        try {
            runnable.run();
            throw new RuntimeException();
        } catch (Throwable t) {
            if (!exceptionClass.isInstance(t)) {
                throw new RuntimeException();
            }
        }
    }

    /**
     * Checks if a given {@link MyRunnable} does not throw any exception.<br>
     * If is does, throw a RuntimeException.
     *
     * @param runnable
     * @throws RuntimeException on failure
     */
    public static void assertDoesNotThrow(MyRunnable runnable) {
        try {
            runnable.run();
        } catch (Throwable t) {
            throw new RuntimeException();
        }
    }

    // SECTION Test-methods

    /**
     * Tests if the constructor {@link Mug#Mug(Liquid, int)}
     *
     * @throws RuntimeException on failure
     */
    public static void testMugConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Mug<>(null, 0));
        assertThrows(IllegalArgumentException.class, () -> new Mug<>(new Water(), -1));

        Water water = new Water();
        int cap = 0;
        Mug<Liquid> mug = new Mug<>(water, cap);
        assertTrue(water == mug.getLiquid());
        assertEquals(cap, mug.getCapacity());
        assertEquals(0, mug.getAmount());
    }

    /**
     * Shows how the {@link Mug} class is generic.
     *
     * @throws RuntimeException on failure
     */
    public static void testMugGenerity() {
        // Test Mug with water
        Mug<Liquid> mug = new Mug<>(new Water(), 0);
        assertTrue(mug.getLiquid() instanceof Liquid);
        assertTrue(mug.getLiquid() instanceof Water);

        // Test Mug with CoughSyrup
        mug = new Mug<>(new CoughSyrup(), 0);
        assertTrue(mug.getLiquid() instanceof Liquid);
        assertTrue(mug.getLiquid() instanceof CoughSyrup);
    }

    /**
     * Tests the {@link Mug#pour(int)} method.
     *
     * @throws RuntimeException on failure
     */
    public static void testPour() {
        // Create mug and test initial values.
        Mug<Water> mug = new Mug<>(new Water(), 500);
        assertEquals(mug.getCapacity(), 500);
        assertEquals(mug.getAmount(), 0);

        // Pour 0, 100 and 400 ml into the mug. Non of these should
        // throw because the capacity is 500 ml.
        assertDoesNotThrow(() -> mug.pour(0));
        assertDoesNotThrow(() -> mug.pour(100));
        assertDoesNotThrow(() -> mug.pour(400));
        assertEquals(500, mug.getAmount());

        // Test if pouring in even 1 ml over the capacity will throw
        // a NotEnoughCapacityException.
        assertThrows(NotEnoughCapacityException.class, () -> {
            mug.pour(1);
        });
        // Check if the amount did not change.
        assertEquals(mug.getAmount(), 500);

        // Create a new mug with 0 ml capacity.
        Mug<?> mug1 = new Mug<>(new Water(), 0);
        assertEquals(0, mug1.getAmount());
        assertEquals(0, mug1.getCapacity());
        assertDoesNotThrow(() -> mug1.pour(0)); // Pouring in 0 should not throw

        // Go over capacity.
        assertThrows(NotEnoughCapacityException.class, () -> mug1.pour(1));
        assertEquals(0, mug1.getAmount());
    }

    /**
     * Tests {@link Mug#takeOut(int)}
     *
     * @throws RuntimeException on failure
     */
    public static void testTakeOut() {
        // Create and test initial attributes.
        Mug<Water> mug = new Mug<>(new Water(), 500);
        assertEquals(mug.getCapacity(), 500);
        assertEquals(mug.getAmount(), 0);

        // Taking out liquid from an empty mug should throw
        assertThrows(NotEnoughLiquidException.class, () -> mug.takeOut(1));

        // Taking out nothing should not throw
        assertDoesNotThrow(() -> mug.takeOut(0));

        // If this fails, there is no point in testing the rest.
        // Thus print the trace on failure and return.
        try {
            mug.pour(100);
        } catch (NotEnoughCapacityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        assertEquals(mug.getAmount(), 100);

        // Afer pouring in 100 ml, take out 99 ml (should not throw)
        assertDoesNotThrow(() -> mug.takeOut(99));
        assertEquals(mug.getAmount(), 1);

        // Try to take out too much; Should throw
        assertThrows(NotEnoughLiquidException.class, () -> mug.takeOut(2));
        assertEquals(mug.getAmount(), 1);
    }

    /**
     * Tests {@link Mug#drink(int)}
     *
     * @throws RuntimeException on failure
     */
    public static void testDrink() {
        Mug<Water> mug = new Mug<>(new Water(), 500);

        // If this fails, there is no point in testing the rest.
        // Thus print the trace on failure and return.
        try {
            mug.pour(500);
        } catch (NotEnoughCapacityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // Test drinking.
        assertEquals(500, mug.getAmount());
        assertDoesNotThrow(() -> mug.drink(500));// Drink the mugs exact amount. Should not throw

        // Drink too much. Should throw.
        assertEquals(0, mug.getAmount());
        assertThrows(NotEnoughLiquidException.class, () -> mug.drink(1));

        // Test another mug.
        Mug<CoughSyrup> mug1 = new Mug<>(new CoughSyrup(), 500);

        // If this fails, there is no point in testing the rest.
        // Thus print the trace on failure and return.
        try {
            mug1.pour(500);
        } catch (NotEnoughCapacityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // Try to drink something undrinkable. Should throw.
        assertEquals(500, mug1.getAmount());
        assertThrows(UndrinkableException.class, () -> mug1.drink(500));
        assertEquals(500, mug1.getAmount());
    }

    /**
     * Tests {@link Mug#empty()}
     *
     * @throws RuntimeException on failure
     */
    public static void testEmpty() {
        Mug<Water> mug = new Mug<>(new Water(), 500);

        // Try to empty an empty mug.
        assertEquals(0, mug.getAmount());
        assertEquals(0, mug.empty());
        assertEquals(0, mug.getAmount());

        // If this fails, there is no point in testing the rest.
        // Thus print the trace on failure and return.
        try {
            mug.pour(500);
        } catch (NotEnoughCapacityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // Try to empty a filled mug.
        assertEquals(500, mug.getAmount());
        assertEquals(500, mug.empty());
        assertEquals(0, mug.getAmount());
    }

    /**
     * Tests {@link Mug#isEmpty()}
     *
     * @throws RuntimeException on failure
     */
    public static void testIsEmpty() {
        Mug<Water> mug = new Mug<>(new Water(), 500);

        // New mug should be empty
        assertTrue(mug.isEmpty());

        // If this fails, there is no point in testing the rest.
        // Thus print the trace on failure and return.
        try {
            mug.pour(500);
        } catch (NotEnoughCapacityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // After pouring in something, the mug should not be empty
        assertFalse(mug.isEmpty());

        // Empty out the filled mug. It should now be empty.
        mug.empty();
        assertTrue(mug.isEmpty());
    }

    /**
     * Tests {@link Mug#isHot()}
     *
     * @throws RuntimeException on failure
     */
    public static void testIsHot() {
        Water water = new Water();
        Mug<Water> mug = new Mug<>(water, 500);

        // Initially, the water in the mug should not be hot.
        assertFalse(mug.isHot());

        // Heat up the water a bit. It should still not be hot.
        mug.getLiquid().hitUp(1);
        assertFalse(mug.isHot());

        // Make the water boil and evaporate. It should not be hot.
        mug.getLiquid().hitUp(10000);
        assertTrue(mug.isHot());

        // Pouring the same water into a new mug means that the new mug is
        // also hot.
        mug = new Mug<>(water, 500);
        assertTrue(mug.isHot());
    }

    // SECTION Main

    public static void main(String[] args) {
        try {
            System.out.println("testMugConstructor()");
            testMugConstructor();
            System.out.println("  -> Success");
        } catch (RuntimeException re) {
            re.printStackTrace();
        }

        try {
            System.out.println("testMugGenerity()");
            testMugGenerity();
            System.out.println("  -> Success");
        } catch (RuntimeException re) {
            re.printStackTrace();
        }

        try {
            System.out.println("testPour()");
            testPour();
            System.out.println("  -> Success");
        } catch (RuntimeException re) {
            re.printStackTrace();
        }

        try {
            System.out.println("testTakeOut()");
            testTakeOut();
            System.out.println("  -> Success");
        } catch (RuntimeException re) {
            re.printStackTrace();
        }

        try {
            System.out.println("testDrink()");
            testDrink();
            System.out.println("  -> Success");
        } catch (RuntimeException re) {
            re.printStackTrace();
        }

        try {
            System.out.println("testEmpty()");
            testEmpty();
            System.out.println("  -> Success");
        } catch (RuntimeException re) {
            re.printStackTrace();
        }

        try {
            System.out.println("testIsEmpty()");
            testIsEmpty();
            System.out.println("  -> Success");
        } catch (RuntimeException re) {
            re.printStackTrace();
        }

        try {
            System.out.println("testIsHot()");
            testIsHot();
            System.out.println("  -> Success");
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }
}
