package u7;

import java.awt.*;
import java.util.Random;

/**
 * @author Armin Kleinert
 * @version 1.0
 * <p>
 * Helper class for all my classes that implement Animation and Shape since many methods just do the same thing.
 */
public abstract class AbstractAnimationShape implements Animation, Shape {

    protected static final Random rand = new Random();

    protected final Point center;
    protected ShapesWorld shapesWorld;
    protected Color color;
    protected final double radius;
    private final boolean spawnAtRandomPosition;

    /**
     * @param center                Center point of the shape.
     * @param color                 Color of the shape.
     * @param radius                Radius of the shape.
     * @param spawnAtRandomPosition true if the position of the object shall be randomized upon world change.
     */
    public AbstractAnimationShape(Point center, Color color, double radius, boolean spawnAtRandomPosition) {
        this.center = center;
        this.color = color;
        this.radius = radius;
        this.spawnAtRandomPosition = spawnAtRandomPosition;
    }

    /**
     * @return The color of the object.
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * Moves the objectt to the requested coordinates.
     *
     * @param x X coordinate
     * @param y Y coordinate.
     */
    @Override
    public void moveTo(double x, double y) {
        getCenter().x = x;
        getCenter().y = y;
    }

    /**
     * Sets the shapesWorld attribute and, if requested, resets the position of the object.
     *
     * @param theWorld The new shapesworld.
     */
    @Override
    public void setShapesWorld(ShapesWorld theWorld) {
        boolean isNewWorld = shapesWorld != theWorld;
        this.shapesWorld = theWorld;

        // If the world was changed and the object requested to have its
        // position randomized on world-change, randomize the position.
        // This could not be done in the constructor because the shapesWorld
        // was null at this point.
        if (isNewWorld && spawnAtRandomPosition) {
            center.x = randomXInWorld(radius, shapesWorld);
            center.y = randomYInWorld(radius, shapesWorld);
        }
    }

    /**
     * @return Center point of the object
     */
    @Override
    public Point getCenter() {
        return center;
    }

    /**
     * Called when the user clicks the object. The default implementation does nothing.
     * Implicitly, the object is selected so its userTyped method can be called.
     *
     * @param atX X coordinate of the click on screen.
     * @param atY Y coordinate of the click on screen.
     */
    @Override
    public void userClicked(double atX, double atY) {
    }

    /**
     * Called when the object is selected (userClicked(...)) and a key is pressed.
     * The default implementation ignores the operation.
     *
     * @param key the key which was pressed, represented as a char
     */
    @Override
    public void userTyped(char key) {
    }

    /**
     * Checks if the objects' area contains a point described by the x and y parameters.
     *
     * @param x X coordinate of the point.
     * @param y Y coordinate of the point.
     * @return true if the objects' area contains the point, false otherwise.
     */
    @Override
    public boolean contains(double x, double y) {
        return !(x < getCenter().x - getRadius())
                && !(x > getCenter().x + getRadius())
                && !(y < getCenter().y - getRadius())
                && !(y > getCenter().y + getRadius());
    }

    /**
     * @return The radius attribute.
     */
    @Override
    public double getRadius() {
        return radius;
    }

    /**
     * Calculates a random X coordinate in the shapesWorld.
     *
     * @param radius      Radius of the object. Needed to calculate offset from the borders of the shapesWorld
     * @param shapesWorld The world which contains the object
     * @return A random X coordinate in the shapesWorld.
     */
    protected static double randomXInWorld(double radius, ShapesWorld shapesWorld) {
        double temp = AbstractAnimationShape.rand.nextInt((int) (shapesWorld.getMax_X() - shapesWorld.getMin_X() - radius));
        return Math.max(shapesWorld.getMin_X() + temp, shapesWorld.getMin_X() + radius);
    }

    /**
     * Calculates a random Y coordinate in the shapesWorld.
     *
     * @param radius      Radius of the object. Needed to calculate offset from the borders of the shapesWorld
     * @param shapesWorld The world which contains the object
     * @return A random Y coordinate in the shapesWorld.
     */
    protected static double randomYInWorld(double radius, ShapesWorld shapesWorld) {
        double temp = AbstractAnimationShape.rand.nextInt((int) (shapesWorld.getMax_Y() - shapesWorld.getMin_Y() - radius));
        return Math.max(shapesWorld.getMin_Y() + temp, shapesWorld.getMin_Y() + radius);
    }

    /**
     * Checks if a Shape object is in the bounds of a ShapesWorld.
     *
     * @param sh The shape.
     * @param sw The shapesWorld.
     * @return true if the world contains the shape, false otherwise.
     */
    protected static boolean isWithinWorldBounds(Shape sh, ShapesWorld sw) {
        return (sh.getCenter().x + sh.getRadius()) <= sw.getMax_X()
                && (sh.getCenter().x - sh.getRadius()) >= sw.getMin_X()
                && (sh.getCenter().y + sh.getRadius()) <= sw.getMax_Y()
                && (sh.getCenter().y - sh.getRadius()) >= sw.getMin_Y();
    }
}
