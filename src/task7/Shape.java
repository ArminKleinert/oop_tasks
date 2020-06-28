package task7;

import java.awt.*;

public interface Shape {
    public void draw(Graphics g);

    public Point getCenter();

    public double getRadius();

    public Color getColor();

    public void setShapesWorld(ShapesWorld theWorld);

    public void userClicked(double atX, double atY);

    public void userTyped(char key);

    public void moveTo(double x, double y);
}
