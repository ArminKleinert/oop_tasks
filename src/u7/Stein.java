package u7;

import java.awt.*;
import java.util.Random;

/**
 * Author Armin Kleinert
 * TODO: Implementation!
 */
public class Stein extends AbstractAnimationShape {

    protected double velocityY;
    protected double velocityX;

    private static class MiniStein extends Stein {

        int ticksUntilFall;
        double startY;

        MiniStein(double x, double y, double radius) {
            super(radius);
            center.x = x;
            center.y = y;
            startY = y;

            velocityY = -AbstractAnimationShape.rand.nextDouble() * 10;
            double temp = AbstractAnimationShape.rand.nextDouble() * 5;
            velocityX = (AbstractAnimationShape.rand.nextDouble() < 0.5) ? temp : -temp;

            ticksUntilFall = ((int) Math.abs(velocityY));
        }

        @Override
        public void play() {
            //System.out.println(getCenter().y + " " + startY + " " + ticksUntilFall);
            if (getCenter().y < startY || ticksUntilFall > 0) {
                if (ticksUntilFall > 0) {
                    ticksUntilFall--;
                    velocityY *= 0.99;
                } else {
                    velocityY = Math.abs(velocityY) * 1.1;
                    if (getCenter().y > startY) {
                        getCenter().y = startY;
                        ticksUntilFall = 0;
                    }
                }
                moveTo(center.x + velocityX, center.y + velocityY);
            }
        }
    }

    protected Stein(double radius) {
        super(new Point(), Color.lightGray, radius);
        velocityY = 0.0;
    }

    public Stein() {
        this(25);
    }

    @Override
    public void setShapesWorld(ShapesWorld theWorld) {
        super.setShapesWorld(theWorld);
        if (center.x == 0.0) {
            double additionX = AbstractAnimationShape.rand.nextInt(shapesWorld.getMax_X() - shapesWorld.getMin_X());
            center.x = shapesWorld.getMin_X() + additionX;
            center.y = shapesWorld.getMin_Y();
        }
    }

    @Override
    public void play() {
        if (getCenter().y >= shapesWorld.getMax_Y() - 10) {
            center.y = shapesWorld.getMax_Y() - 10;
            shatter();
        } else {
            velocityY += 0.01;
            velocityY *= 1.1;
            moveTo(center.x + velocityX, center.y + velocityY);
        }
    }

    private void shatter() {
        MiniStein ms;
        for (int i = 0; i < 5; i++) {
            ms = new MiniStein(center.x, center.y, radius * 0.33);
            ms.setShapesWorld(shapesWorld);
            shapesWorld.addShape(ms);
        }
        shapesWorld.removeShape(this);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillArc((int) getCenter().x, (int) getCenter().y,
                (int) radius, (int) radius, 0, 180);
    }
}
