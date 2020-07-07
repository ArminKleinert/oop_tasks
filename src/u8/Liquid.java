package u8;

import java.awt.Color;

public abstract class Liquid {
    final protected String name;
    final protected Color color;
    final protected boolean drinkable;
    protected int temperature = 18;

    protected Liquid(String name, Color color, boolean drinkable) {
        this.name = name;
        this.color = color;
        this.drinkable = drinkable;
    }

    protected String nname() {
        return name;
    }

    public abstract String getName();

    public abstract Color getColor();

    public abstract boolean isDrinkable();

    public abstract void hitUp(int temperature);

    public abstract int getTemperature();
}
