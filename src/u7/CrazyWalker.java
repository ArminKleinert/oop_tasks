package u7;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class CrazyWalker implements Shape, Animation {
	
	double radius;
	int step;
	Point center;
	Random rand;
	ShapesWorld theWorld;	   
	Color color;
	
	
	public CrazyWalker() {
		super();
		this.rand = new Random();
		this.radius = 8;
		this.step = 10;
		this.center = new Point(0,0);
		this.color = Color.magenta;
	}

	@Override
	public void play() {
		 int d = rand.nextInt(4);
		 int xd=0, yd=0;
         switch (d) {
             case 0:
	                 xd = 1; yd = 1; break;
             case 1:
                 	 xd = 1; yd = -1; break;
             case 2:
                 	 xd = -1; yd = 1; break;
             case 3:
                 	 xd = -1; yd = -1; break;
         }
         
		Footprint fp = new Footprint();
		
		fp.moveTo(this.getCenter().x, this.getCenter().y);
		theWorld.addShape(fp);
		double newX = this.center.x + rand.nextDouble()*step*xd;
		double newY = this.center.y + rand.nextDouble()*step*yd;
		this.moveTo( (int) newX, (int) newY);		
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawOval((int) center.x, (int) center.y, (int) radius, (int) radius);
	}

	@Override
	public boolean contains(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getRadius() {
		// TODO Auto-generated method stub
		return radius;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
	}

	@Override
	public Point getCenter() {
		// TODO Auto-generated method stub
		return center;
	}

	@Override
	public void setShapesWorld(ShapesWorld theWorld) {
		this.theWorld = theWorld;
	}

	@Override
	public void userClicked(double at_X, double at_Y) {
	}

	@Override
	public void userTyped(char key) {
	}

	@Override
	public void moveTo(double x, double y) {
		center.x = x;
		center.y = y;
	}

}
