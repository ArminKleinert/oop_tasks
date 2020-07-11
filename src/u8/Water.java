package u8;

import java.awt.*;

public class Water extends Liquid {

    public Water() {
        super(Color.BLUE, true);
    }

    @Override
    public String getName() {
        return "Water";
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
