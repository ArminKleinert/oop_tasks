package u7;

public class Point {
	
	double x, y;
		
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Point(){
		this(0, 0);
	}
	
	public void translate( double dx, double dy){
		x += dx; y += dy;
	}
	
	public void rotate( double angle){
		x = (int) (x*Math.cos(angle) - y*Math.sin(angle));
		y = (int) (x*Math.sin(angle) + y*Math.cos(angle));
	}
	
} // end of Point class
