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
        velocity = 1;
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
        //Body
        g.setColor(color);
        g.fillRect((int) center.x, (int) center.y, (int) radius / 2, (int) ((radius / 3) * 2));

        //Body
        int headX = (int) (center.x + (radius / 8));
        int headY = (int) (center.y - (radius / 4));
        int headWidth = (int) (radius / 4);
        int headHeight = (int) (radius / 4);
        g.setColor(color);
        g.fillRect(headX, headY, headWidth, headHeight);

        // Arms
        int armWidth = (int) (radius / 3);
        int armHeight = (int) (radius / 4);
        g.setColor(Color.RED);
        g.fillRect((int) (center.x + (radius / 2) - 1), (int) center.y, armWidth, armHeight);
        g.setColor(Color.BLUE);
        g.fillRect((int) (center.x - (radius / 3)), (int) center.y, armWidth, armHeight);

        // Legs
        int legsY = (int) (center.y + ((radius / 3) * 2));
        int legsHeight = (int) (radius / 4);
        int legsWidth = (int) (radius / 5);
        g.setColor(Color.YELLOW);
        g.fillRect((int) center.x, legsY, legsWidth, legsHeight);
        g.setColor(Color.GREEN);
        g.fillRect((int) (center.x + ((radius / 5) * 1.5)), legsY, legsWidth, legsHeight);
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