package u7;

import java.awt.*;

/**
 * Author Armin Kleinert
 * TODO: Implementation!
 */
public class Feuerwerk extends AbstractAnimationShape {

    private static class FireworkSparkle extends AbstractAnimationShape {

        private double velocityX;
        private double velocityY;
        private int ticksToLive = 100;

        FireworkSparkle(double x, double y) {
            super(new Point(x, y), randomColor(), 10);

            velocityY = -(AbstractAnimationShape.rand.nextDouble() * 5 + 4.5);
            double temp = AbstractAnimationShape.rand.nextDouble() * 7 + 0.45;
            velocityX = (AbstractAnimationShape.rand.nextDouble() < 0.5) ? temp : -temp;
        }

        private static Color randomColor() {
            switch (AbstractAnimationShape.rand.nextInt(7)) {
                case 0:
                    return Color.WHITE;
                case 1:
                    return Color.BLUE;
                case 2:
                    return Color.GREEN;
                case 3:
                    return Color.CYAN;
                case 4:
                    return Color.YELLOW;
                case 5:
                    return Color.RED;
                case 6:
                    return Color.MAGENTA;
            }
            return null; // Will not happen, but Java will complain if it is not here.
        }

        @Override
        public void play() {
            System.out.println(ticksToLive);
            if (ticksToLive > 0) {
                if (ticksToLive % 4 == 0) velocityY *= 0.85;
            } else {
                if (ticksToLive < -30) {
                    shapesWorld.removeShape(this);
                    return;
                }
                velocityY = Math.abs(velocityY) * 1;
                if (ticksToLive % 5 == 0) color = color.darker();
            }
            moveTo(center.x + velocityX, center.y + velocityY);
            velocityX *= 0.925;
            ticksToLive--;
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(color);
            g.drawOval((int) center.x, (int) center.y, (int) radius, (int) radius);
        }
    }

    private int flairsSpawned;
    private boolean spawnFlairs;
    private int tickCount;

    public Feuerwerk() {
        super(new Point(), Color.LIGHT_GRAY, 25);
        spawnFlairs = false;
        flairsSpawned = 0;
    }

    @Override
    public void play() {
        if (spawnFlairs && tickCount % 5 == 0) {
            shapesWorld.addShape(new FireworkSparkle(center.x, center.y));
            flairsSpawned++;
            if (flairsSpawned > 10) {
                shapesWorld.removeShape(this);
            }
        }
        tickCount ++;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect((int) center.x, (int) center.y, (int) radius, (int) radius);
    }

    @Override
    public void userClicked(double at_X, double at_Y) {
        //for (int i = 0; i < 20; i++) {
        //    shapesWorld.addShape(new FireworkSparkle(at_X, at_Y));
        //}
        //shapesWorld.removeShape(this);
        spawnFlairs = true;
    }
}

