package u7;

import java.awt.*;

/**
 * @author Armin Kleinert
 * @version 1.0
 * <p>
 * Bonus-Klasse für Bonuspunkte.
 * A white box which, when clicked, spawns a random type of Shape on the screen.
 */
public class Spawner extends AbstractAnimationShape {

    public Spawner() {
        super(new Point(), Color.WHITE, 50, true);
    }

    /**
     * Creates a random new Shape object and removes the current one.
     *
     * @param atX X coordinate of the click on screen. Ignored.
     * @param atY Y coordinate of the click on screen. Ignored.
     */
    @Override
    public void userClicked(double atX, double atY) {
        // The possible classes
        Class<Shape>[] cs = new Class[]{
                Around.class, Blinker.class, Captive.class, CrazyWalker.class,
                Feuerwerk.class, GoAndBack.class, Roboter.class,
                Scared.class, Spawner.class, Stein.class};

        int i = AbstractAnimationShape.rand.nextInt(cs.length);

        try {
            // Creates a new Shape using the constructor of a random class.
            // Since all the classes have a constructor with no arguments, it works.
            Shape newShape = cs[i].getConstructor(new Class[]{}).newInstance();
            shapesWorld.addShape(newShape);
            newShape.getCenter().x = center.x;
            newShape.getCenter().y = center.y;
        } catch (Exception e) {
            // e.printStackTrace(); // Uncomment to see the error. This was not necessary in our tests.
            System.out.println("Could not spawn new object.");
        }
        shapesWorld.removeShape(this);
    }

    /**
     * Nothing here. All actions are in {@link #userClicked(double, double)}
     */
    @Override
    public void play() {
    }

    /**
     * Draws the object as a rectangle.
     *
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect((int) (center.x - (radius / 2)), (int) (center.y - (radius / 2)), (int) radius, (int) radius);
    }
}
