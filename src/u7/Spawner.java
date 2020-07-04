package u7;

import java.awt.*;


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
                Feuerwerk.class, Footprint.class, GoAndBack.class,
                Roboter.class, Scared.class, Spawner.class, Stein.class};

        int i = rand.nextInt(cs.length);
        try {
            // Creates a new Shape using the constructor of a random class.
            // Since all the classes have a constructor with no arguments, it works.
            Shape nshape = cs[i].getConstructor(new Class[]{}).newInstance();
            shapesWorld.addShape(nshape);
        } catch (Exception e) {
            System.out.println("Could not spawn new object");
        }
        shapesWorld.removeShape(this);
    }

    @Override
    public void play() {
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect((int) (center.x - radius / 2), (int) (center.y - radius / 2), (int) radius, (int) radius);
    }
}
