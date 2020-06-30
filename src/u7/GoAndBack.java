package u7;

import java.awt.*;

/**
 * Author Armin Kleinert
 */
public class GoAndBack implements Shape, Animation {

    private final double radius;
    private final Point center;

    private final Color color;
    private ShapesWorld shapesWorld;

    private boolean goRight;

    public GoAndBack() {
        this.radius = 25;
        this.center = new Point();

        // Random color
        color = Color.getHSBColor((float) Math.random(), (float) Math.random(), (float) Math.random());
        // Random direction (50/50 chance of going left or right)
        goRight = Math.random() < 0.5;
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
        this.shapesWorld = theWorld;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int) (center.x - radius / 2), (int) (center.y - radius / 2), (int) radius, (int) radius);
    }

    @Override
    public Point getCenter() {
        return center;
    }

    @Override
    public void userClicked(double atX, double atY) {
        goRight = !goRight;
    }

    @Override
    public void userTyped(char key) {
        System.out.println("key");
    }

    // implement the Animation-Interface
    @Override
    public void play() {
        if ((center.x + radius / 2) >= shapesWorld.getMax_X() || (center.x - radius / 2) <= shapesWorld.getMin_X()) {
            goRight = !goRight;
        }
        int velocity = 3;
        if (goRight) {
            center.x = center.x + velocity;
        } else {
            center.x = center.x - velocity;
        }
    }

    @Override
    public boolean contains(double x, double y) {
        return !(x < (center.x - radius))
                && !(x > center.x + radius)
                && !(y < (center.y - radius))
                && !(y > (center.y + radius));
    }

    @Override
    public double getRadius() {
        return radius;
    }

}
