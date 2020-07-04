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
            double temp = -(AbstractAnimationShape.rand.nextDouble() * 5 + 4.5);
            velocityY = (AbstractAnimationShape.rand.nextInt() % 2 == 0) ? temp : -temp;
            // velocityY = temp; // Uncomment to make flairs always go up.
            temp = AbstractAnimationShape.rand.nextDouble() * 7 + 0.45;
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
                default: // Triggers only if the number is 6 (see docs of Random#nextInt)
                    return Color.MAGENTA;
            }
        }

        /**
         * The sparkle keeps rising until its ticksToLive attribute reaches 0.
         * Then, it starts falling and its color darkens.
         *
         * @see #sinkDownward()
         */
        @Override
        public void play() {
            if (ticksToLive > 0) {
                // Slow down rising velocity every 4 ticks
                if (ticksToLive % 4 == 0) velocityY *= 0.85;
            } else {
                sinkDownward();
            }
            // Move sparkle, slow it down on the x-axis and decrement its lifetime.
            moveTo(center.x + velocityX, center.y + velocityY);
            velocityX *= 0.925;
            ticksToLive--;
        }

        /**
         * Called when the sparkle moves downwards. Its color fades until it becomes black.
         * Once it has been falling for 30 ticks, it is removed.
         */
        private void sinkDownward() {
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

        /**
         * Draws the sparkle as a small, un-filled oval.
         *
         * @param g
         */
        @Override
        public void draw(Graphics g) {
            g.setColor(color);
            g.drawOval((int) center.x, (int) center.y, (int) radius, (int) radius);
        }
    }

    // The number of sparkles/flairs which will be spawned
    private static final int NUM_FLAIRS = 10;

    /* Keeps track of the number of spawned flairs */
    private int flairsSpawned;
    /* True if the object has been clicked */
    private boolean spawnFlairs;
    /* Counts ticks */
    private int tickCount;

    public Feuerwerk() {
        super(new Point(), Color.getHSBColor(0.1f, 0.8f, 1f), 25, true);
        spawnFlairs = false;
        flairsSpawned = 0;
    }

    /**
     * If activated, spawns a new flair every 5 ticks. Once {@link #NUM_FLAIRS} sparkles have
     * been spawned, remove the object.
     *
     * @see FireworkSparkle#FireworkSparkle(double, double)
     */
    @Override
    public void play() {
        if (spawnFlairs && tickCount % 5 == 0) {
            // Create new sparkle/flair
            shapesWorld.addShape(new FireworkSparkle(center.x, center.y));
            flairsSpawned++;

            // If the box is empty, remove it.
            if (flairsSpawned > NUM_FLAIRS) {
                shapesWorld.removeShape(this);
            }
        }
        if (spawnFlairs) tickCount++;
    }

    /**
     * Draws the object as a box.
     *
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect((int) center.x, (int) center.y, (int) radius, (int) radius);
    }

    /**
     * When the object is clicked, start spawning flairs.
     *
     * @param at_X
     * @param at_Y
     */
    @Override
    public void userClicked(double at_X, double at_Y) {
        spawnFlairs = true;
    }
}

