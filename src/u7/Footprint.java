package u7;

import java.awt.Color;
import java.awt.Graphics;

public class Footprint implements Shape, Animation {
    double radius;
    Point center;
    Color color;
    ShapesWorld world;

    public Footprint() {
        super();
        this.radius = 6;
        this.center = new Point(0, 0);
        this.color = Color.WHITE;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.drawOval((int) center.x, (int) center.y, (int) radius / 2, (int) (radius));
    }

    public boolean contains(double x, double y) {
        return false;
    }

    public double getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }

    public Point getCenter() {
        return center;
    }

    public void setShapesWorld(ShapesWorld theWorld) {
        this.world = theWorld;
    }

    public void userClicked(double at_X, double at_Y) {
    }

    public void userTyped(char key) {
    }

    public void moveTo(double x, double y) {
        center.x = x;
        center.y = y;
    }

    public void play() {
    }

}// end of class Footprint
