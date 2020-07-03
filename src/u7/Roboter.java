package u7;

import java.awt.*;

/**
 * @author Armin Kleinert
 * @version 1.0
 */
public class Roboter extends AbstractAnimationShape {

    public Roboter() {
        super(new Point(), Color.lightGray, 50, true);
    }

    @Override
    public void play() {

    }

    @Override
    public void draw(Graphics g) {
        //Body
        g.setColor(color);
        g.fillRect((int) center.x, (int) center.y, (int) radius/2, (int) ((radius/3) * 2));

        //Body
        int headX = (int) (center.x + (radius / 8));
        int headY = (int) (center.y - (radius / 4));
        int headWidth=(int) (radius/4);
        int headHeight = (int) (radius/4);
        g.setColor(color);
        g.fillRect(headX,headY ,headWidth ,headHeight );

        // Arms
        int armWidth = (int) (radius/3);
        int armHeight =(int) (radius/4) ;
        g.setColor(Color.RED);
        g.fillRect((int) (center.x+(radius/2)-1), (int) center.y, armWidth, armHeight);
        g.setColor(Color.BLUE);
        g.fillRect((int) (center.x-(radius/3)), (int) center.y, armWidth, armHeight);

        // Legs
        int legsY = (int) (center.y + ((radius/3) * 2));
        int legsHeight = (int) (radius/4);
        int legsWidth = (int) (radius/5);
        g.setColor(Color.YELLOW);
        g.fillRect((int) center.x, legsY, legsWidth, legsHeight);
        g.setColor(Color.GREEN);
        g.fillRect((int) (center.x+((radius/5)*1.5)), legsY, legsWidth, legsHeight);
    }
}