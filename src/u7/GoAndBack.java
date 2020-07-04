package u7;

import java.awt.*;

/**
 * @author Armin Kleinert
 * @version 1.0
 * <p>
 * Spawns at a random position and starts going left or right.
 * Upon reaching the boundries of the world, it changes direction.
 */
public class GoAndBack extends AbstractAnimationShape {

    private final double velocity;
    private boolean goRight;

    /**
     * Creates a new instance with randomized colors.
     */
    public GoAndBack() {
        super(new Point(),
                Color.getHSBColor(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()),
                25, true);
        goRight = rand.nextBoolean(); // Random direction
        velocity = 3;
    }

    /**
     * Draws the object as a circle.
     *
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillOval((int) (getCenter().x - getRadius() / 2),
                (int) (getCenter().y - getRadius() / 2),
                (int) getRadius(), (int) getRadius());
    }

    /**
     * When the object is clicked, it changes direction.
     *
     * @param atX
     * @param atY
     */
    @Override
    public void userClicked(double atX, double atY) {
        goRight = !goRight;
    }

    /**
     * The object moves left or right (determined by its goRight attribute).
     * When it reaches the edge of the screen, it changes direction.
     */
    @Override
    public void play() {
        // When the screen-boundry is reached, change direction
        if (!AbstractAnimationShape.isWithinWorldBounds(this, shapesWorld)) {
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
