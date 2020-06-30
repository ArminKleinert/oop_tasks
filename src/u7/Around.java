package u7;

/**
 * @author M. Esponda
 * @version 1.01
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Random;

public class Around implements Shape, Animation {

    double radius;
    Point center;
    Random rand;

    Color color;
    ShapesWorld welt;
    double velocity = 2;

    public Around() {
        this.radius = 25;
        this.color = Color.CYAN;
        this.center = new Point();
        this.rand = new Random();
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void moveTo(double x, double y) {
        center.x = (int) x;
        center.y = (int) y;
    }

    @Override
    public void setShapesWorld(ShapesWorld theWorld) {
        this.welt = theWorld;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        fillTriangle(g, center.x - radius, center.y - radius, radius * 2, radius * 2);
    }

    public void fillTriangle(Graphics g, double x, double y, double w, double h) {
        int[] x_coords = {(int) (x + w / 2), (int) (x), (int) (x + w)};
        int[] y_coords = {(int) (y), (int) (y + h), (int) (y + h)};
        Polygon p = new Polygon(x_coords, y_coords, 3);
        g.fillPolygon(p);
    }


    @Override
    public Point getCenter() {
        return center;
    }

    @Override
    public void userClicked(double atX, double atY) {
        this.radius += 2;
        this.welt.addShape(new Around());
    }

    @Override
    public void userTyped(char key) {
        System.out.println("key");
    }

    // implement the Animation-Interface
    @Override
    public void play() {
        if ((center.x - radius) <= welt.getMax_X())
            center.x = center.x + velocity;
        else
            center.x = welt.getMin_X() + radius;
    }

    @Override
    public boolean contains(double x, double y) {
        if (x < (center.x - radius) || x > center.x + radius || y < (center.y - radius) || y > (center.y + radius))
            return false;
        else
            return true;
    }

    @Override
    public double getRadius() {
        return radius;
    }

} // end of class Around


