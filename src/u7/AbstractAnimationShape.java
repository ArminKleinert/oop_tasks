package u7;

import java.awt.*;
import java.util.Random;

/**
 * @author Armin Kleinert
 * @version 1.0
 * Helper class for all my classes that implement Animation and Shaape since many methods just do the same thing.
 */
public abstract class AbstractAnimationShape implements Animation, Shape {

    protected static final Random rand = new Random();

    protected final Point center;
    protected ShapesWorld shapesWorld;
    protected Color color;
    protected final double radius;
    private final boolean spawnAtRandomPosition;

    public AbstractAnimationShape(Point center, Color color, double radius, boolean spawnAtRandomPosition) {
        this.center = center;
        this.color = color;
        this.radius = radius;
        this.spawnAtRandomPosition = spawnAtRandomPosition;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void moveTo(double x, double y) {
        getCenter().x = x;
        getCenter().y = y;
    }

    @Override
    public void setShapesWorld(ShapesWorld theWorld) {
        // If the world is changed and the position of new object should be randomized, then randomize it.
        // Otherwise, just set the new world.
        // The position can't be randomized before the world is initialized because the bounds are not known.

        boolean isNewWorld = shapesWorld != theWorld;
        this.shapesWorld = theWorld;
        if (isNewWorld && spawnAtRandomPosition) {
            center.x = randomXInWorld(radius);
            center.y = randomYInWorld(radius);
        }
    }

    @Override
    public Point getCenter() {
        return center;
    }

    @Override
    public void userClicked(double atX, double atY) {
    }

    @Override
    public void userTyped(char key) {
    }

    @Override
    public boolean contains(double x, double y) {
        return !(x < getCenter().x - getRadius())
                && !(x > getCenter().x + getRadius())
                && !(y < getCenter().y - getRadius())
                && !(y > getCenter().y + getRadius());
    }

    @Override
    public double getRadius() {
        return radius;
    }

    protected final double randomXInWorld(double radius) {
        double temp = AbstractAnimationShape.rand.nextInt((int) (shapesWorld.getMax_X() - shapesWorld.getMin_X() - radius));
        return shapesWorld.getMin_X() + temp;
    }

    protected final double randomYInWorld(double radius) {
        double temp = AbstractAnimationShape.rand.nextInt((int) (shapesWorld.getMax_Y() - shapesWorld.getMin_Y() - radius));
        return shapesWorld.getMin_Y() + temp;
    }

    protected final boolean isWithinWorldBounds() {
        return (center.x + radius) <= shapesWorld.getMax_X()
                && (center.x - radius) >= shapesWorld.getMin_X()
                && (center.y + radius) <= shapesWorld.getMax_Y()
                && (center.y - radius) >= shapesWorld.getMin_Y();

    }
}
