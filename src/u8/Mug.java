package u8;

/**
 *
 * @param <T>
 * @author Armin Kleinert
 */
public class Mug<T extends Liquid> {

    private final T liquid;
    private final int capacityMl;
    private int amountMl;

    public Mug(T liquid, int capacityMl) {
        if (liquid == null || capacityMl < 0) {
            throw new IllegalArgumentException("liquid must not be null and capacity must be positive.");
        }

        this.liquid = liquid;
        this.capacityMl = capacityMl;
        amountMl = 0;
    }

    public void pour(int ml) throws NotEnoughCapacityException {
        if (capacityMl < amountMl + ml) {
            throw new NotEnoughCapacityException();
        }
        amountMl += ml;
    }

    public void takeOut(int ml) throws NotEnoughLiquidException {
        if (amountMl < ml) {
            throw new NotEnoughLiquidException();
        }
        amountMl -= ml;
    }

    public void drink(int ml) throws UndrinkableException, NotEnoughLiquidException {
        if (!liquid.drinkable) {
            throw new UndrinkableException();
        }
        takeOut(ml);
    }

    public int empty() {
        int temp = amountMl;
        amountMl = 0;
        return temp;
    }

    public boolean isEmpty() {
        return amountMl == 0;
    }

    public boolean isHot() {
        return liquid.temperature > 80;
    }

    public T getLiquid() {
        return liquid;
    }

    public int getCapacity() {
        return capacityMl;
    }

    public int getAmount() {
        return amountMl;
    }
}
