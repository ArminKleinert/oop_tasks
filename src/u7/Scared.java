package u7;

import u6.Rectangle;

import java.awt.*;

/**
 * Author Armin Kleinert
 * TODO: Implementation!
 */
public class Scared extends AbstractAnimationShape {

    private static final Color normalColor = Color.blue;
    private static final Color shakingColor = Color.green;

    private static final double normalRadius = 25;
    private static final double scareRadius = 50;

    private boolean explodeOnNextCollision = false;
    private boolean scared = false;

    private static double shakeVelocity;

    public Scared() {
        super(new Point(), normalColor, normalRadius);
        explodeOnNextCollision = false;
        scared = false;
        shakeVelocity = 5;
    }

    @Override
    public void play() {
        Shape closest = shapesWorld.getClosestShape(this);
        if (closest == null) {
            scared = false;
            return;
        }

        Point p = closest.getCenter();
        double dist_x = Math.abs(p.x - this.center.x);
        double dist_y = Math.abs(p.y - this.center.y);
        double dist = Math.sqrt(dist_x * dist_x + dist_y * dist_y);

        if (dist < (radius + closest.getRadius())) { // If nearest intersects with normalRadius
            onCollision();
        } else if (dist < scareRadius + closest.getRadius()) { // If nearest intersects with scareRadius
            color = shakingColor;
            scared = true;
            shakeVelocity = -shakeVelocity;
            moveTo(center.x + shakeVelocity, center.y + shakeVelocity);
        } else { // No collision
            color = normalColor;
            scared = false;
        }
    }

    /*
    private boolean collidesWithAny() {
        Shape closest = shapesWorld.getClosestShape(this);
        if (closest != null) {
            Point p = closest.getCenter();
            double dist_x = Math.abs(p.x - this.center.x);
            double dist_y = Math.abs(p.y - this.center.y);
            double dist = Math.sqrt(dist_x * dist_x + dist_y * dist_y);
            if (dist < (radius + closest.getRadius())) {
                // Collision
            }
        }
    }
     */

    private void onCollision() {
        if (explodeOnNextCollision) {
            shatter();
            shapesWorld.removeShape(this);
        } else {
            explodeOnNextCollision = true;
            center.x = randomXInWorld();
            center.y = randomYInWorld();
            play();
            explodeOnNextCollision = false;
        }
    }

    private void shatter() {
        Stein.MiniStein ms;
        for (int i = 0; i < 5; i++) {
            ms = new Stein.MiniStein(center.x, center.y, shapesWorld.getMax_Y(), radius * 0.33);
            ms.setShapesWorld(shapesWorld);
            shapesWorld.addShape(ms);
        }
        shapesWorld.removeShape(this);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillOval((int) (getCenter().x - getRadius() / 2),
                (int) (getCenter().y - getRadius() / 2),
                (int) getRadius(), (int) getRadius());
    }

    @Override
    public void setShapesWorld(ShapesWorld theWorld) {
        super.setShapesWorld(theWorld);
        if (center.x == 0.0 && center.y == 0.0) {
            double additionX = AbstractAnimationShape.rand.nextInt(shapesWorld.getMax_X() - shapesWorld.getMin_X());
            center.x = shapesWorld.getMin_X() + additionX;
            center.y = shapesWorld.getMin_Y();
        }
    }
}
