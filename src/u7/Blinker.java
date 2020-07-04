package u7;

import java.awt.*;

/**
 * @author Armin Kleinert
 * @version 1.0
 * Bonus-Klasse für Bonuspunkte.
 * <p>
 * A rotating Arc. It changes its color every few ticks.
 * The exact shape and rotation of the arc seems really erratic,
 * but is based on simple maths. (see the play() method)
 */
public class Blinker extends AbstractAnimationShape {

    private int ticks;
    private int angle;
    private int startAngle; // Name is based on the fillArc() method of the Graphics class.
    private boolean turned;

    public Blinker() {
        super(new Point(), Color.lightGray, 50, true);

        // Randomize angle, startAngle and the 'turned' attribute
        angle = AbstractAnimationShape.rand.nextInt(360);
        startAngle = AbstractAnimationShape.rand.nextInt(360);
        turned = AbstractAnimationShape.rand.nextBoolean();

        ticks = 0;
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

        ticks++;

        // Change both angle and startAngle
        angle += turned ? 5 : -5;
        startAngle += turned ? -2.5 : 2.5;

        // If startAngle reached an impossible value
        if (startAngle >= 360 || startAngle <= 0) {
            startAngle = angle;
        }

        // If angle reached an impossible value, reset it and change turn-direction
        if (angle >= 360 || angle <= 0) {
            turned = !turned;
            angle = startAngle;
        }
    }

    /**
     * Draw the object as an arc.
     *
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillArc((int) center.x, (int) center.y,
                (int) radius, (int) radius, startAngle, angle);
    }
}
