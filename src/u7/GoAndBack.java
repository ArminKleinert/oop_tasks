package u7;

import java.awt.*;

/**
 * Author Armin Kleinert
 */
public class GoAndBack extends AbstractAnimationShape {

    private ShapesWorld shapesWorld;

    private boolean goRight;

    public GoAndBack() {
        super(new Point(), Color.getHSBColor((float) Math.random(), (float) Math.random(), (float) Math.random()), 25);
        goRight = Math.random() < 0.5; // Random direction (50/50 chance of going left or right)
    }

    @Override
    public void setShapesWorld(ShapesWorld theWorld) {
        this.shapesWorld = theWorld;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillOval((int) (getCenter().x - getRadius() / 2),
                (int) (getCenter().y - getRadius() / 2),
                (int) getRadius(), (int) getRadius());
    }

    @Override
    public void userClicked(double atX, double atY) {
        goRight = !goRight;
    }

    // implement the Animation-Interface
    @Override
    public void play() {
        if ((getCenter().x + getRadius() / 2) >= shapesWorld.getMax_X()
                || (getCenter().x - getRadius() / 2) <= shapesWorld.getMin_X()) {
            goRight = !goRight;
        }
        int velocity = 3;
        if (goRight) {
            getCenter().x += velocity;
        } else {
            getCenter().x -= velocity;
        }
    }

}
