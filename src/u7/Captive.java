package u7;

import java.awt.*;

/**
 * @author Armin Kleinert
 * @version 1.0
 */
public class Captive extends AbstractAnimationShape {

    private final Rectangle shapesWorldRectangle;
    private final Rectangle collisionRect;

    private double velocityX;
    private double velocityY;

    /**
     * Creates a new rectangle with a random velocity (includes direction) and random position
     */
    public Captive() {
        super(new Point(), Color.lightGray, 25, true);
        setRandomVelocity();
        collisionRect = new Rectangle();
        shapesWorldRectangle = new Rectangle();
        updateCollisionRectangle();
    }

    /**
     * Draws a vaguely human shape (head and body.
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

    @Override
    public void play() {
        double oldX = getCenter().x;
        double oldY = getCenter().y;
        moveTo(getCenter().x + velocityX, getCenter().y + velocityY);

        // Update collision rectangles for the object with its new x and y coordinates
        updateCollisionRectangle();

        // Update collision rectangle of the world (might be important if it was resized.
        shapesWorldRectangle.setBounds(
                shapesWorld.getMin_X(), shapesWorld.getMin_Y(),
                shapesWorld.getMax_X() - shapesWorld.getMin_X(),
                shapesWorld.getMax_Y() - shapesWorld.getMin_Y());

        // If the object is outside the world, move it back and set a new random direction and speed.
        if (!shapesWorldRectangle.contains(collisionRect)) {
            moveTo(oldX, oldY);
            setRandomVelocity();
        }
    }

    /**
     * Randomizes the direction within a certain boundry.
     */
    private void setRandomVelocity() {
        velocityX = (AbstractAnimationShape.rand.nextDouble() - 0.5) * 5;
        velocityY = (AbstractAnimationShape.rand.nextDouble() - 0.5) * 5;
    }

    private void updateCollisionRectangle() {
        double collRecX = (getCenter().x - getRadius() / 2);
        double collRecY = (getCenter().y - getRadius() * 0.6);
        collisionRect.setBounds((int) collRecX, (int) collRecY,
                (int) (getCenter().x + getRadius() / 2 - collRecX),
                (int) (getCenter().y + getRadius() * 0.75 - collRecY));
    }
}
