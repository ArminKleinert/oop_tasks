package u7;

/**
 * @author M. Esponda
 * @version 1.01
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShapeFrame extends JFrame {
	
	public static final long serialVersionUID = 1;
	 
    ShapesPanel shapesPanel = new ShapesPanel(new Dimension(500,400));
    ShapesList allShapes = shapesPanel.allShapes;

    public ShapeFrame( String args[] ) {
    	
        super("_.-'-._.-'-._.-'-._  Shapes World  _.-'-._.-'-._.-'-._");
        this.add( shapesPanel, "Center" );
        this.setResizable(false);
        this.setLocation(400, 50);
        this.add( createButtonsPanel(args), "South" );
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.pack();
        this.setVisible( true );
    }
    
    JPanel createButtonsPanel( String args[] ){
    	 JPanel shapeButtons = new JPanel( new FlowLayout() );  
    	 for ( int i=0; i<args.length; i++ ) {
            JButton shapeButton = new JButton( args[i] );
            shapeButton.addActionListener( new ButtonsActionListener() );
            shapeButtons.add( shapeButton );
        }        
    	if ( args.length == 0 ) {
            JLabel l= new JLabel("Die Namen der Shape-Klassen sollen als Argumente eingebeben werden");
            shapeButtons.add(l);
        }      
        return shapeButtons;
    } 
    
    public class ButtonsActionListener implements ActionListener{
    	
        public void actionPerformed( ActionEvent aevt ) {
    	    String name = ShapesWorld_Main.class.getName();
    	    String newName = name.replace("ShapesWorld_Main",aevt.getActionCommand());
            try {
                  Class c = Class.forName( newName );
                  if ( c != null ) {
                	  Shape shape = (Shape) c.getConstructor().newInstance();
                	  shape.setShapesWorld(shapesPanel);
                      allShapes.addShape( shape );
                  }
            } catch ( Exception e ) {
                System.out.println( e.getMessage() );
            }
        }// end of actionPerformed
        
    } // end of class ButtonsActionListener

    public class ShapesPanel extends JPanel implements ShapesWorld, Runnable {
    	
    	public static final long serialVersionUID = 1;
    	
        Color bgcolor = new Color(0,0,120);
        Dimension dim;
        int[][] stars;
        
        public ShapesList allShapes = new ShapesList();
        private Thread anim;
        private Shape selected = null;

        public ShapesPanel( Dimension dim ) {
        	this.setBackground(bgcolor);
        	this.dim = dim;
        	this.setSize(dim);
            stars = createStars();
            this.setFocusable(true);
            this.addMouseListener( new ShapeMouseMotionListener() );
    		this.addKeyListener(new ShapeKeyListener());
            anim = new Thread( this );
            anim.start();
        }
        
        public Dimension getPreferredSize() { 
        	return dim;  
        }
         
        public void paintComponent(Graphics g){
        	 super.paintComponent(g);
        	 this.paintStars(g);
             g.translate(+dim.width/2, +dim.height/2);
             Shape[] shapes = allShapes.getShapes();
             for (int i=0; i<shapes.length; i++) {
                     if ( shapes[i] == selected ) {
                         g.setColor( Color.red );
                     } else {
                         g.setColor( shapes[i].getColor()  );
                     }
                     shapes[i].draw(g);
             } // end of for
         } // end of paintComponent
          
        // paints some stars on the background
        
         public void paintStars(Graphics g){
        	 g.setColor(Color.LIGHT_GRAY);
        	 for( int i=0; i<stars.length-2; i+=3 ){
        		 g.fillOval(stars[i][0], stars[i][1], 1, 1);
        		 g.fillOval(stars[i+1][0], stars[i+1][1], 2, 2);
        	 }
         }
         
         // create some random stars to be painted on the background after each update
         
         public int[][] createStars(){
        	 Random rand = new Random();
        	 int[][] stars = new int[600][2];
        	 for( int[] s: stars ){
        		 s[0] = rand.nextInt(dim.width);
        		 s[1] = rand.nextInt(dim.height);
        	 }
        	 return stars;
         }

        /* the following methods are the implementation of the ShapesWorld-interface */  
         
    	public int getMin_X() { 
    	    int width = this.dim.width;
    		return  -width/2; 
    	}
    	
    	public int getMax_X() { 
    	    int width = this.dim.width;       	
    		return  width/2; 
    	}
    	
    	public int getMin_Y() { 
    	    int height = this.dim.height;       	
    		return  -height/2; 
    	}
    	
    	public int getMax_Y() { 
    	    int height = this.dim.height;        	
    		return  height/2; 
    	}
    	
    	public Shape getClosestShape( Shape myShape ) {
    	    return allShapes.getClosest( myShape );
    	}
    	
    	public void addShape( Shape newShape ) {
    	    newShape.setShapesWorld( this );
    	    allShapes.addShape( newShape );
    	}       
    	
    	public void removeShape( Shape toBeRemoved ) {
    	    allShapes.removeShape( toBeRemoved );
    	    System.out.println("removeShape");
    	}
    			
     // implementation of the Runnable-Interface		
    	public void run() {
    	    while ( anim == Thread.currentThread() ) {
    	        try {
    	              Thread.sleep(Animation.sleep_time/2);
    	        } catch ( InterruptedException ie ) {
    	             System.out.println( ie.getMessage() );
    	        }
    	        repaint();
    	    }
    	} // end of run
    	
    	
    	/* We use inner classes for the implementation of the Listener classes */
    	
    	class ShapeMouseMotionListener implements MouseListener {
    		
    		public void mouseClicked( MouseEvent evt ) {
    		    double x = evt.getX()-dim.width/2.0;
    		    double y = evt.getY()-dim.height/2.0;
    		    selected = allShapes.hit( x, y );
    		    if ( selected != null ) {
    		        selected.userClicked( x, y );
    		    }
    		    requestFocusInWindow(true);
    		    System.out.println(x+y);
    		}			
    		public void mousePressed(MouseEvent me) {}
    		public void mouseEntered(MouseEvent arg0) {}
    		public void mouseExited(MouseEvent arg0) {}
    		public void mouseReleased(MouseEvent me) {}
    		
    	}// end of class ShapeMouseMotionListener
    	
    	class ShapeKeyListener implements KeyListener {		
    		public void keyTyped(KeyEvent e) {
    			if(selected != null)
    				selected.userTyped(e.getKeyChar());
    			System.out.println(e.getKeyChar());
    		}
    		public void keyPressed(KeyEvent e) {}
    		public void keyReleased(KeyEvent e) {}
    		
    	}// end of class ShapeKeyListener

		public Color getBackgroundColor() {
			return bgcolor;
		}
    	
    }// end of class ShapesPanel
    	
} // end of class ShapeWorld
