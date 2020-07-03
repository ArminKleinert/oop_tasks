package u7;

import java.awt.*;

/**
 * @author Armin Kleinert
 * @version 1.0
 * Bonus-Klasse für Bonuspunkte
 */
public class Blinker extends AbstractAnimationShape {

    private int ticks = 0;

    public Blinker() {
        super(new Point(), Color.lightGray, 25, true);
    }

    @Override
    public void play() {
        if (ticks % 10 == 0) {
            color = Color.getHSBColor((float) Math.random(), (float) Math.random(),
                    (float) Math.random());
        }
    }

    @Override
    public void draw(Graphics g) {

    }
}
