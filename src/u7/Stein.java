package u7;

import java.awt.*;

/**
 * @author Armin Kleinert
 * @version 1.0
 */
public class Stein extends AbstractAnimationShape {

    protected double velocityY;
    protected double velocityX;

    static class MiniStein extends Stein {

        private int ticksUntilFall; // The time that the stone rises
        private final double minY; // The minimum y coordinate the stone can fall to

        MiniStein(double x, double y, double minY, double radius) {
            super(radius);
            center.x = x;
            center.y = y;
            this.minY = minY;

            velocityY = -AbstractAnimationShape.rand.nextDouble() * 10;
            double temp = AbstractAnimationShape.rand.nextDouble() * 5;
            velocityX = (AbstractAnimationShape.rand.nextDouble() < 0.5) ? temp : -temp;

            ticksUntilFall = ((int) Math.abs(velocityY));
        }

        /**
         * New MiniStein objects are thrust upwards for a time and then fall down.
         */
        @Override
        public void play() {
            if (getCenter().y < minY || ticksUntilFall > 0) {
                if (ticksUntilFall > 0) { // Rise upwards
                    ticksUntilFall--;
                    velocityY *= 0.99;
                } else  { // Fall down
                    velocityY = Math.abs(velocityY) * 1.1; // Fall down faster and faster
                    if (getCenter().y > minY) {
                        // The stone reached the bottom.
                        getCenter().y = minY;
                        ticksUntilFall = 0;
                    }
                }
                moveTo(center.x + velocityX, center.y + velocityY);
            }
        }
    }

    /**
     * Helper constructor for Ministein.
     * @param radius
     */
    protected Stein(double radius) {
        super(new Point(), Color.lightGray, radius, true);
        velocityY = 0.0;
        center.y = shapesWorld.getMin_Y();
    }

    public Stein() {
        this(25);
    }

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
     *  Shatter the stone and creates 5 Ministein objects.
     */
    private void shatter() {
        MiniStein ms;
        for (int i = 0; i < 5; i++) {
            ms = new MiniStein(center.x, center.y, center.y, radius * 0.33);
            ms.setShapesWorld(shapesWorld);
            shapesWorld.addShape(ms);
        }
        shapesWorld.removeShape(this);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillArc((int) getCenter().x, (int) getCenter().y,
                (int) radius, (int) radius, 0, 180);
    }
}
