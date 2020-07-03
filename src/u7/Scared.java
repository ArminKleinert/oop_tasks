package u7;

import u6.Rectangle;

import java.awt.*;

/**
 * @author Armin Kleinert
 * @version 1.0
 */
public class Scared extends AbstractAnimationShape {

    private static final Color normalColor = Color.blue;
    private static final Color shakingColor = Color.green; // Color when scared

    private static final double normalRadius = 25;
    private static final double scareRadius = 40; // The radius within which the object starts being scared.

    private boolean explodeOnNextCollision = false; // See onCollision()
    private double shakeVelocity; // How fast the object shakes left and right when in proximity to another object.

    public Scared() {
        super(new Point(), normalColor, normalRadius, true);
        explodeOnNextCollision = false;
        shakeVelocity = 5;
    }

    @Override
    public void play() {
        Shape closest = shapesWorld.getClosestShape(this);

        // If there is no other object, the object won't be scaredd
        if (closest == null) {
            return;
        }

        // Calculate distance to other object.
        Point p = closest.getCenter();
        double dist_x = Math.abs(p.x - this.center.x);
        double dist_y = Math.abs(p.y - this.center.y);
        double dist = Math.sqrt(dist_x * dist_x + dist_y * dist_y);

        if (dist < (radius/2 + closest.getRadius())) { // If nearest intersects with normalRadius
            onCollision();
        } else if (dist < scareRadius + closest.getRadius()) { // If nearest intersects with scareRadius
            color = shakingColor; // Change color
            shakeVelocity = -shakeVelocity; // If the object was shaking right, it now shakes left or the other way around
            moveTo(center.x + shakeVelocity, center.y + shakeVelocity); // The object shakes
        } else { // No collision
            color = normalColor; // Change color to normal color
        }
    }

    /**
     * If the object collides with another, it is randomly teleported. If it was teleported
     * onto another object, it explodes and is removed.
     */
    private void onCollision() {
        if (explodeOnNextCollision) {
            // The object has already collided with another object.
            // Now, after being teleported, it is on top of another object, it explodes.
            shatter();
        } else {
            // Teleport the object
            explodeOnNextCollision = true;
            center.x = randomXInWorld(radius);
            center.y = randomYInWorld(radius);

            // Call play(). If the object collides again directly,
            // play() will run onCollision() recursively and the above case will be run.
            play();

            // If the object still exists, make it not explode on direct teleport-
            explodeOnNextCollision = false;
        }
    }

    /**
     * If the object shatters, it spawns 5 small stones
     */
    private void shatter() {
        Stein.MiniStein ms;
        for (int i = 0; i < 5; i++) {
            ms = new Stein.MiniStein(center.x, center.y, shapesWorld.getMax_Y(), radius * 0.33);
            ms.setShapesWorld(shapesWorld);
            shapesWorld.addShape(ms);
        }
        Stein stein = new Stein(center.x, center.y, radius / 2);
        shapesWorld.addShape(stein);
        // Remove the original object
        shapesWorld.removeShape(this);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillOval((int) (getCenter().x - getRadius() / 2),
                (int) (getCenter().y - getRadius() / 2),
                (int) getRadius(), (int) getRadius());
    }
}
