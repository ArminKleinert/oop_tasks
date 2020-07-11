package u8;

import java.awt.Color;

public abstract class Liquid {
    final protected Color color;
    final protected boolean drinkable;
    protected int temperature = 18;

    protected Liquid(Color color, boolean drinkable) {
        this.color = color;
        this.drinkable = drinkable;
    }

    public abstract String getName();

    public abstract Color getColor();

    public abstract boolean isDrinkable();

    public abstract void hitUp(int temperature);

    public abstract int getTemperature();
}
