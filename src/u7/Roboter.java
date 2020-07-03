package u7;

import java.awt.*;

/**
 * @author Armin Kleinert
 * @version 1.0
 */
public class Roboter extends AbstractAnimationShape {

    private double velocity;
    private boolean moving;

    public Roboter() {
        super(new Point(), Color.lightGray, 50, true);
        velocity = 0.5;
        moving = true;
    }

    @Override
    public void play() {
        if (moving) {
            moveTo(center.x, center.y + velocity);
            if (velocity > 0) {
                // The robot is moving downwards
                velocity *= 1.05; // Fall faster
                if ((center.y + radius) >= shapesWorld.getMax_Y()) { // Reached the ground
                    // Stop falling
                    center.y = shapesWorld.getMax_Y() - radius;
                    velocity = 0;
                    moving = false;
                }
            } else if (velocity < 0) { // The robot is moving upwards
                velocity *= 0.9; // Slow down the fall
                if (velocity > -0.1) { // When this threshold is passed, the robot starts falling
                    velocity = 0.5;
                }
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        // Body
        g.setColor(color);
        double bodyX = center.x - (radius / 2);
        double bodyY = center.y - (radius / 2);
        double bodyWidth = radius / 2;
        double bodyHeight = radius * 0.66;
        g.fillRect((int) bodyX, (int) bodyY, (int) bodyWidth, (int) bodyHeight);

        // Arms
        double armWidth = (radius / 4);
        int armHeight = (int) (radius / 4);
        g.fillRect((int) (bodyX - armWidth), (int) bodyY, (int) armWidth, armHeight);
        g.fillRect((int) (bodyX + bodyWidth), (int) bodyY, (int) armWidth, armHeight);

        // Legs
        double legsY = bodyY + bodyHeight;
        double legsHeight = radius / 4;
        double legsWidth = radius / 5;
        g.fillRect((int) bodyX, (int) legsY, (int) legsWidth, (int) legsHeight);
        g.fillRect((int) (bodyX + bodyWidth - legsWidth), (int) legsY, (int) legsWidth, (int) legsHeight);

        // Head
        double headWidth = radius / 4;
        double headHeight = radius / 4;
        double headX = center.x - (radius / 4) - (radius / 8);
        double headY = center.y - (radius / 2) - (radius / 4);
        g.fillRect((int) headX, (int) headY, (int) headWidth, (int) headHeight);
        g.setColor(Color.RED);
        int eyesY = (int) (headY + headHeight * 0.25);
        g.fillRect((int) headX, eyesY, (int) headWidth, (int)(headHeight / 4));
    }

    @Override
    public void userTyped(char key) {
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
    }
}