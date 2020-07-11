package u8;


public class MugTest {

    public interface MyRunnable {
        void run() throws Throwable;
    }

    public static void assertTrue(boolean b) {
        if (!b) {
            throw new RuntimeException();
        }
    }

    public static void assertFalse(boolean b) {
        if (b) {
            throw new RuntimeException();
        }
    }

    public static void assertEquals(int o1, int o2) {
        if (o1 != o2) {
            throw new RuntimeException();
        }
    }

    public static void assertThrows(Class<? extends Throwable> exceptionClass, MyRunnable runnable) {
        try {
            runnable.run();
        } catch (Throwable t) {
            if (!exceptionClass.isInstance(t)) {
                throw new RuntimeException();
            }
        }
    }

    public static void assertDoesNotThrow(MyRunnable runnable) {
        try {
            runnable.run();
        } catch (Throwable t) {
            throw new RuntimeException();
        }
    }

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

    public static void testMugGenerity() {
        Mug<Liquid> mug = new Mug<>(new Water(), 0);
        assertTrue(mug.getLiquid() instanceof Liquid);
        assertTrue(mug.getLiquid() instanceof Water);

        mug = new Mug<>(new CoughSyrup(), 0);
        assertTrue(mug.getLiquid() instanceof Liquid);
        assertTrue(mug.getLiquid() instanceof CoughSyrup);
    }

    public static void testPour() {
        Mug<Water> mug = new Mug<>(new Water(), 500);
        assertEquals(mug.getCapacity(), 500);
        assertEquals(mug.getAmount(), 0);

        assertDoesNotThrow(() -> mug.pour(0));
        assertDoesNotThrow(() -> mug.pour(100));
        assertDoesNotThrow(() -> mug.pour(400));
        assertEquals(500, mug.getAmount());

        assertThrows(NotEnoughCapacityException.class, () -> {
            mug.pour(1);
        });
        assertEquals(mug.getAmount(), 500);

        Mug<?> mug1 = new Mug<>(new Water(), 0);
        assertEquals(0, mug1.getAmount());
        assertEquals(0, mug1.getCapacity());
        assertDoesNotThrow(() -> mug1.pour(0));
        assertThrows(NotEnoughCapacityException.class, () -> mug1.pour(1));
        assertEquals(0, mug1.getAmount());
    }

    public static void testTakeOut() {
        Mug<Water> mug = new Mug<>(new Water(), 500);
        assertEquals(mug.getCapacity(), 500);
        assertEquals(mug.getAmount(), 0);

        assertThrows(NotEnoughLiquidException.class, () -> mug.takeOut(1));
        assertDoesNotThrow(() -> mug.takeOut(0));

        try {
            mug.pour(100);
        } catch (NotEnoughCapacityException e) {
            e.printStackTrace();
            return;
        }
        assertEquals(mug.getAmount(), 100);
        assertDoesNotThrow(() -> mug.takeOut(99));
        assertEquals(mug.getAmount(), 1);
        assertThrows(NotEnoughLiquidException.class, () -> mug.takeOut(2));
        assertEquals(mug.getAmount(), 1);
    }

    public static void testDrink() {
        Mug<Water> mug = new Mug<>(new Water(), 500);
        try {
            mug.pour(500);
        } catch (NotEnoughCapacityException e) {
            e.printStackTrace();
            return;
        }
        assertEquals(500, mug.getAmount());
        assertDoesNotThrow(() -> mug.drink(500));
        assertEquals(0, mug.getAmount());
        assertThrows(NotEnoughLiquidException.class, () -> mug.drink(1));

        Mug<CoughSyrup> mug1 = new Mug<>(new CoughSyrup(), 500);
        try {
            mug1.pour(500);
        } catch (NotEnoughCapacityException e) {
            e.printStackTrace();
            return;
        }
        assertEquals(500, mug1.getAmount());
        assertThrows(UndrinkableException.class, () -> mug1.drink(500));
        assertEquals(500, mug1.getAmount());
    }

    public static void testEmpty() {
        Mug<Water> mug = new Mug<>(new Water(), 500);

        assertEquals(0, mug.getAmount());
        assertEquals(0, mug.empty());
        assertEquals(0, mug.getAmount());

        try {
            mug.pour(500);
        } catch (NotEnoughCapacityException e) {
            e.printStackTrace();
            return;
        }
        assertEquals(500, mug.getAmount());
        assertEquals(500, mug.empty());
        assertEquals(0, mug.getAmount());
    }

    public static void testIsEmpty() {
        Mug<Water> mug = new Mug<>(new Water(), 500);

        assertTrue(mug.isEmpty());
        try {
            mug.pour(500);
        } catch (NotEnoughCapacityException e) {
            e.printStackTrace();
            return;
        }
        assertFalse(mug.isEmpty());
        mug.empty();
        assertTrue(mug.isEmpty());
    }

    public static void testIsHot() {
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
