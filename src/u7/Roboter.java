package u7;

import java.awt.*;

/**
 * @author Armin Kleinert
 * @version 1.0
 * <p>
 * A fun, hard to control robot. Once selected (via mouse click),
 * 'j' and ' ' make it jump, 'a' makes it go left and 'd' makes
 * it go right.
 */
public class Roboter extends AbstractAnimationShape {

    private double velocity;
    private boolean moving;

    // Helper attributes for drawing.
    private final double bodyWidth;
    private final double bodyHeight;
    private final double armWidth;
    private final double armHeight;
    private final double legsHeight;
    private final double legsWidth;
    private final double headWidth;
    private final double headHeight;

    public Roboter() {
        super(new Point(), Color.lightGray, 50, true);

        // The robot is spawned at random x and y coordinates.
        // The following lines make it fall down.
        velocity = 0.5;
        moving = true;

        // Set up helper attributes for drawing.
        bodyWidth = radius / 2;
        bodyHeight = radius * 0.66;
        armWidth = radius / 4;
        armHeight = radius / 4;
        legsHeight = radius / 4;
        legsWidth = radius / 5;
        headWidth = radius / 4;
        headHeight = radius / 4;
    }

    /**
     * If the robot is moving, do the following:
     * - If it is falling, call {@link #fall()}
     * - If it is jumping, call {@link #rise()}
     */
    @Override
    public void play() {
        if (moving) {
            moveTo(center.x, center.y + velocity);
            if (velocity > 0) {
                fall();
            } else if (velocity < 0) { // The robot is moving upwards
                rise();
            }
        }
    }

    /**
     * Called by {@link #play()} if the object is falling.
     * First, the downwards velocity is increased.
     * Then, if the object reached the ground, set its velocity to 0 and stop moving.
     */
    private void fall() {
        // The robot is moving downwards
        velocity *= 1.05; // Fall faster
        if ((center.y + (radius / 2)) > shapesWorld.getMax_Y()) { // Reached the ground
            // Stop falling
            center.y = shapesWorld.getMax_Y() - (radius / 2);
            velocity = 0;
            moving = false;
        }
    }

    /**
     * Called by {@link #play()} if the object is rising (is jumping).
     * The upwards velocity is decreased. Once it reaches a certain
     * threshold, the object starts falling.
     */
    private void rise() {
        velocity *= 0.9; // Slow down the fall
        if (velocity > -0.1) { // When this threshold is passed, the robot starts falling
            velocity = 0.5;
        }
    }

    /**
     * Draw the object as a robot with a body, arms, legs, a head and eyes.
     * The first 4 are drawn with the normal color.
     * The eyes are always a red line.
     *
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        // Body
        g.setColor(color);
        double bodyX = center.x - (radius / 2);
        double bodyY = center.y - (radius / 2);
        g.fillRect((int) bodyX, (int) bodyY, (int) bodyWidth, (int) bodyHeight);

        // Arms
        g.fillRect((int) (bodyX - armWidth), (int) bodyY, (int) armWidth, (int) armHeight);
        g.fillRect((int) (bodyX + bodyWidth), (int) bodyY, (int) armWidth, (int) armHeight);

        // Legs
        double legsY = bodyY + bodyHeight;
        g.fillRect((int) bodyX, (int) legsY, (int) legsWidth, (int) legsHeight);
        g.fillRect((int) (bodyX + bodyWidth - legsWidth), (int) legsY, (int) legsWidth, (int) legsHeight);

        // Head
        double headX = center.x - (radius / 4) - (radius / 8);
        double headY = center.y - (radius / 2) - (radius / 4);
        g.fillRect((int) headX, (int) headY, (int) headWidth, (int) headHeight);

        // Eyes (It was easier to draw the eyes as a rectangle rather than a line)
        g.setColor(Color.RED);
        int eyesY = (int) (headY + headHeight * 0.25);
        g.drawRect((int) headX, eyesY, (int) headWidth, (int) (headHeight / 4));
    }

    /**
     * When the object is selected (via mouse click) and a button is pressed, do the following:
     * - If the button is 'j' or ' ' (space), start jumping.
     * - If the button is 'a', go left
     * - If the button is 'd', go right
     *
     * @param key the key which was pressed, represented as a char
     */
    @Override
    public void userTyped(char key) {
        double oldX = center.x;
        switch (key) {
            case 'j': // When key is j or space, jump
            case ' ':
                velocity -= 10;
                moving = true;
                break;
            case 'a': // When key is 'a', go left
                center.x -= 5;
                break;
            case 'd': // When key is 'd', go right
                center.x += 5;
                break;
        }
        // If the object went out of bounds, reset its X coordinate.
        // We don't care about the Y coordinate, as the robot will
        // fall back inbounds eventually.
        if (!isWithinWorldBounds(this, shapesWorld)) {
            center.x = oldX;
        }
    }
}