package u7;

import java.awt.*;

/**
 * @author Armin Kleinert
 * @version 1.0
 */
public class Feuerwerk extends AbstractAnimationShape {

    private static class FireworkSparkle extends AbstractAnimationShape {

        private double velocityX;
        private double velocityY;
        private int ticksToLive = 100;

        FireworkSparkle(double x, double y) {
            super(new Point(x, y), randomColor(), 10, false);

            // Set random velocity and direction
            velocityY = -(AbstractAnimationShape.rand.nextDouble() * 5 + 4.5);
            double temp = AbstractAnimationShape.rand.nextDouble() * 7 + 0.45;
            // 50/50 change of going right or left
            velocityX = (AbstractAnimationShape.rand.nextInt() % 2 == 0) ? temp : -temp;
        }

        /**
         * Returns a random color from the following list:
         * Color.WHITE;
         * Color.BLUE;
         * Color.GREEN;
         * Color.CYAN;
         * Color.YELLOW;
         * Color.RED;
         * Color.MAGENTA;
         *
         * @return A random color
         */
        private static Color randomColor() {
            switch (AbstractAnimationShape.rand.nextInt(7)) {
                case 0:
                    return Color.WHITE;
                case 1:
                    return Color.BLUE;
                case 2:
                    return Color.GREEN;
                case 3:
                    return Color.CYAN;
                case 4:
                    return Color.YELLOW;
                case 5:
                    return Color.RED;
                case 6:
                    return Color.MAGENTA;
            }
            return null; // Will not happen, but Java will complain if it is not here.
        }

        /**
         *
         */
        @Override
        public void play() {
            if (ticksToLive > 0) {
                // Slow down rising velocity every 4 ticks
                if (ticksToLive % 4 == 0) velocityY *= 0.85;
            } else {
                // Remove shape if it was fading for 30 ticks
                if (ticksToLive < -30) {
                    shapesWorld.removeShape(this);
                    return;
                } else if (ticksToLive == 0) {
                    // Make velocity positive (make sparkle fall)
                    velocityY = Math.abs(velocityY);
                }
                // Made dying sparkle darker every 5 ticks
                if (ticksToLive % 5 == 0) color = color.darker();
            }
            // Move sparkle, slow it down on the x-axis and decrement its livetime.
            moveTo(center.x + velocityX, center.y + velocityY);
            velocityX *= 0.925;
            ticksToLive--;
        }

        /**
         * Draws the sparkle as an oval
         * @param g
         */
        @Override
        public void draw(Graphics g) {
            g.setColor(color);
            g.drawOval((int) center.x, (int) center.y, (int) radius, (int) radius);
        }
    }

    private static final int NUM_FLAIRS = 10;

    /* Keeps track of the number of spawned flairs */
    private int flairsSpawned;
    /* True if the object has been clicked */
    private boolean spawnFlairs;
    /* Counts ticks */
    private int tickCount;

    public Feuerwerk() {
        super(new Point(), Color.LIGHT_GRAY, 25, true);
        spawnFlairs = false;
        flairsSpawned = 0;
    }

    /**
     * If activated, spawns a new flair every 5 ticks.
     */
    @Override
    public void play() {
        if (spawnFlairs && tickCount % 5 == 0) {
            shapesWorld.addShape(new FireworkSparkle(center.x, center.y));
            flairsSpawned++;
            if (flairsSpawned > NUM_FLAIRS) {
                shapesWorld.removeShape(this);
            }
        }
        if (spawnFlairs) tickCount++;
    }

    /**
     * Draws the object as a box.
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect((int) center.x, (int) center.y, (int) radius, (int) radius);
    }

    /**
     * When the object is clicked, start spawning flairs.
     * @param at_X
     * @param at_Y
     */
    @Override
    public void userClicked(double at_X, double at_Y) {
        spawnFlairs = true;
    }
}

