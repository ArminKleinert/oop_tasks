package u8;

import java.awt.*;

/**
 * Test-implementation for {@link Liquid}.
 *
 * @author Maria Stange
 */
public class Water extends Liquid {

    public Water() {
        super("Water", Color.BLUE, true);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isDrinkable() {
        return drinkable;
    }

    @Override
    public void hitUp(int temperature) {
        this.temperature += temperature;
    }

    @Override
    public int getTemperature() {
        return temperature;
    }
}
