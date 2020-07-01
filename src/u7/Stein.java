package u7;

import java.awt.*;

/**
 * Author Armin Kleinert
 * TODO: Implementation!
 */
public class Stein extends AbstractAnimationShape {

    private static class MiniStein extends AbstractAnimationShape {

        public MiniStein() {
            super(new Point(), Color.lightGray, 12.5);
        }

        @Override
        public void play() {

        }

        @Override
        public void draw(Graphics g) {

        }
    }

    public Stein() {
        super(new Point(), Color.lightGray, 25);
    }

    @Override
    public void play() {

    }

    @Override
    public void draw(Graphics g) {

    }
}
