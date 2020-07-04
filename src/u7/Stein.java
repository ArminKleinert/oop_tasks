package u7;

import java.awt.*;

/**
 * @author Armin Kleinert
 * @version 1.0
 */
public class Stein extends AbstractAnimationShape {

    protected double velocityY;
    protected double velocityX;
    protected boolean resetYOnWorldChange;

    static class MiniStein extends Stein {

        private int ticksUntilFall; // The time that the stone rises

        MiniStein(double x, double y, double radius) {
            super(radius);
            center.x = x;
            center.y = y;
            resetYOnWorldChange = false;

            velocityY = -AbstractAnimationShape.rand.nextDouble() * 10;
            double temp = AbstractAnimationShape.rand.nextDouble() * 5;
            velocityX = (AbstractAnimationShape.rand.nextDouble() < 0.5) ? temp : -temp;

            ticksUntilFall = ((int) Math.abs(velocityY));
        }

        /**
         * New MiniStein objects are thrust upwards for a time and then fall down.
         * The time until the MiniStein instance starts falling is determined by its
         * ticksUntilFall attribute.
         * If the object falls and reaches the bottom of the screen, it stops falling and remains there.
         */
        @Override
        public void play() {
            if (center.y < shapesWorld.getMax_Y() - radius || ticksUntilFall > 0) {
                if (ticksUntilFall > 0) { // Rise upwards
                    ticksUntilFall--;
                    velocityY *= 0.99;
                } else { // Fall down
                    velocityY = Math.abs(velocityY) * 1.1; // Fall down faster and faster
                    if (!isWithinWorldBounds(this, shapesWorld)) {
                        // The stone reached the bottom.
                        // Snap back into bounds
                        center.y = shapesWorld.getMax_Y() - radius;
                        ticksUntilFall = 0;
                        velocityY = 0;
                        velocityX = 0;
                        return;
                    }
                }
                moveTo(center.x + velocityX, center.y + velocityY);
            }
        }
    }

    /**
     * Helper constructor for Ministein.
     *
     * @param radius
     */
    protected Stein(double radius) {
        super(new Point(), Color.lightGray, radius, false);
        velocityY = 0.0;
        resetYOnWorldChange = true;
    }

    public Stein(double startX, double startY, double radius) {
        this(radius);
        center.x = startX;
        center.y = startY;
        resetYOnWorldChange = false;
    }

    public Stein() {
        this(25);
    }

    /**
     * If the object reaches the bottom of the world, call {@link #shatter()}.
     * Otherwise, increase the velocity and keep on falling.
     */
    @Override
    public void play() {
        if (getCenter().y >= shapesWorld.getMax_Y() - 10) {
            // When the stone reaches the bottom of the screen
            center.y = shapesWorld.getMax_Y() - 10;
            shatter();
        } else {
            // When the stone is still falling
            velocityY += 0.01;
            velocityY *= 1.1; // Make the stone fall faster
            moveTo(center.x + velocityX, center.y + velocityY);
        }
    }

    /**
     * Shatter the stone and creates 5 Ministein objects.
     */
    private void shatter() {
        MiniStein ms;
        for (int i = 0; i < 5; i++) {
            ms = new MiniStein(center.x, center.y, radius * 0.33);
            ms.setShapesWorld(shapesWorld);
            shapesWorld.addShape(ms);
        }
        shapesWorld.removeShape(this);
    }

    /**
     * Draws the object as an arc, rounded at the top with a flat bottom.
     *
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillArc((int) getCenter().x, (int) getCenter().y,
                (int) radius, (int) radius, 0, 180);
    }

    /**
     * Similar to {@link AbstractAnimationShape#setShapesWorld(ShapesWorld)} with spawnAtRandomPosition
     * set to true, but only randomizes the X coordinate. The Y coordinate is set to the top of the screen.
     *
     * @param theWorld The new shapesworld.
     */
    @Override
    public void setShapesWorld(ShapesWorld theWorld) {
        super.setShapesWorld(theWorld);
        if (resetYOnWorldChange) {
            center.x = randomXInWorld(radius, shapesWorld);
            center.y = shapesWorld.getMin_Y();
        }
    }
}
