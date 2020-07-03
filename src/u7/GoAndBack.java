package u7;

import java.awt.*;

/**
 * @author Armin Kleinert
 * @version 1.0
 */
public class GoAndBack extends AbstractAnimationShape {

    private double velocity;
    private boolean goRight;

    public GoAndBack() {
        super(new Point(), Color.getHSBColor((float) Math.random(), (float) Math.random(),
                (float) Math.random()), 25, true);
        goRight = Math.random() < 0.5; // Random direction (50/50 chance of going left or right)
        velocity = 3;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillOval((int) (getCenter().x - getRadius() / 2),
                (int) (getCenter().y - getRadius() / 2),
                (int) getRadius(), (int) getRadius());
    }

    /**
     * When the object is clicked, it changes direction
     *
     * @param atX
     * @param atY
     */
    @Override
    public void userClicked(double atX, double atY) {
        goRight = !goRight;
    }


    @Override
    public void play() {
        // When the screen-boundry is reached, change direction
        if ((getCenter().x + getRadius() / 2) >= shapesWorld.getMax_X()
                || (getCenter().x - getRadius() / 2) <= shapesWorld.getMin_X()) {
            goRight = !goRight;
        }

        // Move the object in the specified direction.
        if (goRight) {
            getCenter().x += velocity;
        } else {
            getCenter().x -= velocity;
        }
    }

}
