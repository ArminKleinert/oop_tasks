package u8;

public class MugTest {

    private static void testMugConstructor() {
        try {
            new Mug<>(null, 0);
            assert (false);
        } catch (IllegalArgumentException ignored) {
        }

        try {
            new Mug<>(new Water(), -1);
            assert( false);
        } catch (IllegalArgumentException ignored) {
        }

        Water water = new Water();
        int cap = 0;
        Mug<Liquid> mug = new Mug<>(water, cap);

        assert (water != mug.getLiquid());
        assert (cap != mug.getCapacity());
        assert (0 != mug.getAmount());

        System.out.println("testMugConstructor -> Success!");
    }

    private static void testMugGenerity() {
        Mug<Liquid> mug = new Mug<>(new Water(), 0);
        assert (mug.getLiquid() instanceof Liquid);
        assert (mug.getLiquid() instanceof Water);

        mug = new Mug<>(new CoughSyrup(), 0);
        assert (mug.getLiquid() instanceof Liquid);
        assert (mug.getLiquid() instanceof CoughSyrup);

        System.out.println("testMugGenerity -> Success!");
    }

    private static void testPour() {
        Mug<Water> mug = new Mug<>(new Water(), 500);
        assert (mug.getCapacity() == 500);
        assert (mug.getAmount() == 0);

        try {
            mug.pour(0);
            mug.pour(100);
            mug.pour(400);
        } catch (NotEnoughCapacityException nece) {
            nece.printStackTrace();
            return;
        }
        assert (500 == mug.getAmount());

        try {
            mug.pour(1);
            assert( false);
        } catch (NotEnoughCapacityException nece) {
        }
        assert (mug.getAmount() == 500);

        Mug<?> mug1 = new Mug<>(new Water(), 0);
        assert (0 == mug1.getAmount());
        assert (0 == mug1.getCapacity());

        try {
            mug1.pour(0);
        } catch (NotEnoughCapacityException nece) {
            nece.printStackTrace();
            return;
        }

        try {
            mug1.pour(1);
            assert (false);
        } catch (NotEnoughCapacityException ignored) {
        }

        assert (0 == mug1.getAmount());

        System.out.println("testPour -> Success!");
    }

    private static void testTakeOut() {
        Mug<Water> mug = new Mug<>(new Water(), 500);
        assert (mug.getCapacity() == 500);
        assert (mug.getAmount() == 0);

        try {
            mug.takeOut(1);
            assert (false);
        } catch (NotEnoughLiquidException ignored) {
        }

        try {
            mug.takeOut(0);
        } catch (NotEnoughLiquidException nece) {
            nece.printStackTrace();
            return;
        }

        try {
            mug.pour(100);
        } catch (NotEnoughCapacityException nece) {
            nece.printStackTrace();
            return;
        }

        assert (mug.getAmount() == 100);

        try {
            mug.takeOut(99);
        } catch (NotEnoughLiquidException nece) {
            nece.printStackTrace();
            return;
        }

        assert (mug.getAmount() == 1);

        try {
            mug.takeOut(2);
            assert (false);
        } catch (NotEnoughLiquidException ignored) {
        }

        assert (mug.getAmount() == 1);

        System.out.println("testTakeOut -> Success!");
    }

    private static void testDrink() {
        Mug<Water> mug = new Mug<>(new Water(), 500);

        try {
            mug.pour(500);
        } catch (NotEnoughCapacityException nece) {
            nece.printStackTrace();
            return;
        }

        assert (500 == mug.getAmount());

        try {
            mug.drink(500);
        } catch (NotEnoughLiquidException | UndrinkableException nece) {
            nece.printStackTrace();
            return;
        }

        assert (0 == mug.getAmount());

        try {
            mug.drink(1);
            assert (false);
        } catch (NotEnoughLiquidException | UndrinkableException ignored) {
        }

        Mug<CoughSyrup> mug1 = new Mug<>(new CoughSyrup(), 500);

        try {
            mug.pour(500);
        } catch (NotEnoughCapacityException nece) {
            nece.printStackTrace();
            return;
        }

        assert (500 == mug1.getAmount());

        try {
            mug.drink(500);
            assert (false);
        } catch (NotEnoughLiquidException | UndrinkableException ignored) {
        }

        assert (500 == mug1.getAmount());

        System.out.println("testDrink -> Success!");
    }

    private static void testEmpty() {
        Mug<Water> mug = new Mug<>(new Water(), 500);

        assert (0 == mug.getAmount());
        int temp = mug.empty();
        assert (temp == 0);
        assert (0 == mug.getAmount());

        try {
            mug.pour(500);
        } catch (NotEnoughCapacityException nece) {
            nece.printStackTrace();
            return;
        }

        assert (500 == mug.getAmount());
        temp = mug.empty();
        assert (temp == 0);
        assert (0 == mug.getAmount());

        System.out.println("testEmpty -> Success!");
    }

    private static void testIsEmpty() {
        Mug<Water> mug = new Mug<>(new Water(), 500);

        assert (mug.isEmpty());

        try {
            mug.pour(500);
        } catch (NotEnoughCapacityException nece) {
            nece.printStackTrace();
            return;
        }

        assert (!mug.isEmpty());
        mug.empty();
        assert (mug.isEmpty());

        System.out.println("testIsEmpty-> Success!");
    }

    private static void testIsHot() {
        Water water = new Water();

        Mug<Water> mug = new Mug<>(water, 500);
        assert (!mug.isHot());
        mug.getLiquid().hitUp(1);
        assert (!mug.isHot());
        mug.getLiquid().hitUp(10000);
        assert (mug.isHot());

        mug = new Mug<>(water, 500);
        assert (mug.isHot());

        System.out.println("testIsHot -> Success!");
    }

    public static void main(String[] args) {
        testMugConstructor();
        testMugGenerity();
        testPour();
        testTakeOut();
        testDrink();
        testEmpty();
        testIsEmpty();
        testIsHot();
    }
}
