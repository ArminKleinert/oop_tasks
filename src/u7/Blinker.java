package u7;

import java.awt.*;

/**
 * @author Armin Kleinert
 * @version 1.0
 * Bonus-Klasse für Bonuspunkte
 */
public class Blinker extends AbstractAnimationShape {

    private int ticks = 0;
    private int angle;
    private int startAngle;

    public Blinker() {
        super(new Point(), Color.lightGray, 50, true);
        angle = 0;
        startAngle = 180;
    }

    /**
     * Makes the object turn with each tick. It also changes color every 15 ticks.
     */
    @Override
    public void play() {
        if (ticks % 15 == 0) {
            color = Color.getHSBColor((float) Math.random(), (float) Math.random(),
                    (float) Math.random()).brighter();
        }
        startAngle = startAngle <= 0 ? 359 : startAngle - 10;
        angle = angle >= 360 ? 0 : angle + 1;
        ticks++;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillArc((int) getCenter().x, (int) getCenter().y,
                (int) radius, (int) radius, startAngle, angle);
    }
}
