package u8;

import java.awt.*;

/**
 * Test-implementation for {@link Liquid}.
 *
 * @author Armin Kleinet
 */
public class CoughSyrup extends Liquid {

        public CoughSyrup() {
            super("Cough syrup", Color.lightGray, false);
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
