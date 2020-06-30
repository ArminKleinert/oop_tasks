package u7;
import java.util.ArrayList;

/**
 * @author M. Esponda
 * @version 1.01
 */

// import java.util.Vector;

class ShapesList extends Thread{
	
    ArrayList<Shape> shapes; 
    
    public ShapesList(){
    	this.shapes = new ArrayList<Shape>();
    	this.start();
    }

    public synchronized void addShape( Shape newShape ) {
        for ( Shape s: this.shapes ) {
        	if ( s.equals(newShape))
                  return;
        }
        shapes.add(newShape);
    } // end of addShape

    public synchronized void removeShape( Shape shapeToBeRemoved ) {
         shapes.remove(shapeToBeRemoved);
    } // end of removeShape

    public synchronized Shape[] getShapes() {
        Shape[] only_shapes = new Shape[shapes.size()];
        for ( int i=0; i<shapes.size(); i++) {
            only_shapes[i] = shapes.get(i);
        }
        return only_shapes;
    }// end of getShapes

    public synchronized Shape hit( double x, double y ) {        
        for ( Shape shape: shapes ) {
            if ( shape.contains(x, y) ) {
                    return shape;
            }// if
         }// for
         return null;
    }// hit

    public synchronized Shape getClosest( Shape myShape ) {
        Shape closest = null;
        double x = myShape.getCenter().x;
        double y = myShape.getCenter().y;
        double min_dist = 0, new_dist = 0;
        double x2, y2, dx, dy;

        for ( Shape shape: shapes ) {
            if ( shape == myShape ) { continue; }
            x2 = shape.getCenter().x;
            y2 = shape.getCenter().y;
            dx = Math.abs(x-x2);
            dy = Math.abs(y-y2);
            new_dist = Math.sqrt(dx*dx + dy*dy) - (shape.getRadius()+myShape.getRadius());
            if ( closest == null ) {
                    closest = shape;
                    min_dist = new_dist;
            } else if ( new_dist < min_dist ) {
                    closest = shape;
                    min_dist = new_dist;
            }
        }//for
        return closest;
    }//end of getClosest
    
    public void run(){
        while ( true ) {
        	for(int i=0; i<shapes.size(); i++)
        		((Animation) shapes.get(i)).play();
            try {
                Thread.sleep( Animation.sleep_time );
            } catch ( InterruptedException ie ) {
                System.out.println( ie.getMessage() );
            }
        }
    }

}// End of class ShapesList
