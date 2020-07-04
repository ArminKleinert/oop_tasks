package u7;

import java.awt.*;

/**
 * @author Maria Stange
 * @version 1.0
 * <p>
 * Moves in a random direction at a random speed. When it reaches
 * the edge of the world, a new direction and speed are calculated.
 */
public class Captive extends AbstractAnimationShape {

    // The velocity attributes determine both the direction and speed of the object.
    private double velocityX;
    private double velocityY;

    /**
     * Creates a new rectangle with a random velocity (includes direction) and random position.
     *
     * @see #setRandomVelocity()
     */
    public Captive() {
        super(new Point(), Color.lightGray, 25, true);
        setRandomVelocity(); // Set random speed and direction
    }

    /**
     * Draws a vaguely human shape (head and body).
     *
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        // Draw body
        g.setColor(Color.yellow);
        g.fillOval((int) (getCenter().x - getRadius() / 2),
                (int) (getCenter().y - getRadius() / 2),
                (int) (getRadius() / 2), (int) (getRadius() * 0.75));

        // Draw head
        g.setColor(Color.white);
        g.fillOval((int) (getCenter().x - getRadius() * 0.4), (int) (getCenter().y - getRadius() * 0.75),
                (int) (getRadius() * 0.25), (int) (getRadius() * 0.25));
        g.setColor(Color.black);
    }

    /**
     * Makes the object move into the direction determined by its velocityX and
     * velocityY attributes. If it leaves the world, it snaps back in and randomizes
     * its velocities again.
     *
     * @see #setRandomVelocity()
     */
    @Override
    public void play() {
        double oldX = getCenter().x;
        double oldY = getCenter().y;
        moveTo(getCenter().x + velocityX, getCenter().y + velocityY);

        // If the object is outside the world, move it back and set a new random direction and speed.
        if (!isWithinWorldBounds(this, shapesWorld)) {
            moveTo(oldX, oldY);
            setRandomVelocity();
        }
    }

    /**
     * Randomizes the direction and speed within a certain boundry.
     */
    private void setRandomVelocity() {
        velocityX = (AbstractAnimationShape.rand.nextDouble() - 0.5) * 5;
        velocityY = (AbstractAnimationShape.rand.nextDouble() - 0.5) * 5;

        // If the speed is too low or too high, try again.
        if ((velocityX < 0.01 && velocityX > -0.01) || (velocityY < 0.01 && velocityY > -0.01)) {
            setRandomVelocity();
        }
    }
}
