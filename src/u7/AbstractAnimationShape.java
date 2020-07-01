package u7;

import java.awt.*;
import java.util.Random;

public abstract class AbstractAnimationShape implements Animation, Shape {

    protected static final Random rand = new Random();

    protected final Point center;
    protected ShapesWorld shapesWorld;
    protected final Color color;
    protected final double radius;

    public AbstractAnimationShape(Point center, Color color, double radius) {
        this.center = center;
        this.color = color;
        this.radius = radius;
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
        this.shapesWorld = theWorld;
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
        System.out.println(key);
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
}
