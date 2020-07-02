package u7;

import java.awt.*;

/**
 * Author Armin Kleinert
 */
public class Captive extends AbstractAnimationShape {

    private final Rectangle shapesWorldRectangle;
    private final Rectangle collisionRect;

    private double velocityX;
    private double velocityY;

    public Captive() {
        super(new Point(), Color.lightGray, 25);
        setRandomVelocity();
        collisionRect = new Rectangle();
        shapesWorldRectangle = new Rectangle();
        updateCollisionRectangle();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.yellow);
        g.fillOval((int) (getCenter().x - getRadius() / 2),
                (int) (getCenter().y - getRadius() / 2),
                (int) (getRadius() / 2), (int) (getRadius() * 0.75));
        g.setColor(Color.white);
        g.fillOval((int) (getCenter().x - getRadius() * 0.4), (int) (getCenter().y - getRadius() * 0.75),
                (int) (getRadius() * 0.25), (int) (getRadius() * 0.25));
        g.setColor(Color.black);
    }

    @Override
    public void play() {
        double oldX = getCenter().x;
        double oldY = getCenter().y;
        moveTo(getCenter().x + velocityX, getCenter().y + velocityY);

        updateCollisionRectangle();
        shapesWorldRectangle.setBounds(
                shapesWorld.getMin_X(), shapesWorld.getMin_Y(),
                shapesWorld.getMax_X() - shapesWorld.getMin_X(),
                shapesWorld.getMax_Y() - shapesWorld.getMin_Y());

        if (!shapesWorldRectangle.contains(collisionRect)) {
            moveTo(oldX, oldY);
            setRandomVelocity();
        }
    }

    private void setRandomVelocity() {
        velocityX = (AbstractAnimationShape.rand.nextDouble() - 0.5) * 5;
        velocityY = (AbstractAnimationShape.rand.nextDouble() - 0.5) * 5;
    }

    private void updateCollisionRectangle() {
        double collRecX = (getCenter().x - getRadius() / 2);
        double collRecY = (getCenter().y - getRadius() * 0.6);
        collisionRect.setBounds((int) collRecX, (int) collRecY,
                (int) (getCenter().x + getRadius() / 2 - collRecX),
                (int) (getCenter().y + getRadius() * 0.75 - collRecY));
    }
}
