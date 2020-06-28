package u6;

/**
 * @author M. Esponda
 * @version 1.0
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestRectangle extends JFrame {

	/* This is a simple java class as support for the testing of your Rectangle class. 
	 * The class creates a window on your screen, visualizes some 
	 * Rectangle-Objects and call the methods of your Rectangle class.
	 * 
	 * It's really not necessary to change anything in this code.
	 * 
	 * You also don't need to understand all the details of the methods on this class 
	 * to write your solutions.
	 * 
	 * Please ask your tutor in your exercise session if you want to learn 
	 * more detail about this class.
	 */

	private static final long serialVersionUID = 1L;

	RectanglesPanel rectanglesPanel = new RectanglesPanel();

	public TestRectangle(){

		setSize(400,850);
		setLocation(300,50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testOverlaps();
		testContains();		
		testCompareTo();
		testSection();
		testSmallestBoundingRectangle();

		add(rectanglesPanel);
		setVisible(true); 
	}

	public void testOverlaps(){
		// overlaps: First case    
		LabeledRectangle lr1 = new LabeledRectangle( 35, 25, 20, 20 );
		LabeledRectangle lr2 = new LabeledRectangle( 20, 30, 30, 30 );
		if ( lr1.r.overlaps( lr2.r ) )
			lr1.label = "overlap";
		else
			lr1.label = "no overlap";
		rectanglesPanel.add( lr1 );
		rectanglesPanel.add( lr2 );

		// overlaps: Second case    
		LabeledRectangle lr3 = new LabeledRectangle( 105, 25, 30, 40 );
		LabeledRectangle lr4 = new LabeledRectangle( 110, 30, 40, 20 );
		if ( lr3.r.overlaps( lr4.r ) )
			lr3.label = "overlap";
		else
			lr3.label = "no overlap";
		rectanglesPanel.add( lr3 );
		rectanglesPanel.add( lr4 );

		// overlaps: 3th case    
		LabeledRectangle lr5 = new LabeledRectangle( 215, 20, 20, 40 );
		LabeledRectangle lr6 = new LabeledRectangle( 200, 30, 60, 20);	   
		if ( lr5.r.overlaps( lr6.r ) )
			lr5.label = "overlap";
		else
			lr5.label = "no overlap";
		rectanglesPanel.add( lr5 );
		rectanglesPanel.add( lr6 );

		// overlaps: 4th case    
		LabeledRectangle lr7 = new LabeledRectangle( 20, 120, 60, 60 );
		LabeledRectangle lr8 = new LabeledRectangle( 30, 130, 30, 30 );
		if ( lr7.r.overlaps( lr8.r ) )
			lr7.label = "overlap";
		else
			lr7.label = "no overlap";
		rectanglesPanel.add( lr7 );
		rectanglesPanel.add( lr8 );

		// overlaps: 5th case    
		LabeledRectangle lr9 = new LabeledRectangle( 120, 120, 20, 30 );
		LabeledRectangle lr10 = new LabeledRectangle( 146, 115, 30, 30 );
		if ( lr9.r.overlaps( lr10.r ) )
			lr9.label = "overlap";
		else
			lr9.label = "no overlap";
		rectanglesPanel.add( lr9 );
		rectanglesPanel.add( lr10 );

		// overlaps: 6th case    
		LabeledRectangle lr11 = new LabeledRectangle( 220, 120, 30, 30 );
		LabeledRectangle lr12 = new LabeledRectangle( 200, 160, 30, 20 );
		if ( lr11.r.overlaps( lr12.r ) )
			lr11.label = "overlap";
		else
			lr11.label = "no overlap";
		rectanglesPanel.add( lr11 );
		rectanglesPanel.add( lr12 );

		// overlaps: 7th case    
		LabeledRectangle lr13 = new LabeledRectangle( 20, 220, 30, 30 );
		LabeledRectangle lr14 = new LabeledRectangle( 10, 215, 30, 20 );
		if ( lr13.r.overlaps( lr14.r ) )
			lr13.label = "overlap";
		else
			lr13.label = "no overlap";
		rectanglesPanel.add( lr13 );
		rectanglesPanel.add( lr14 );
	}

	public void testContains(){
		// contains: First case
		LabeledRectangle lr1 = new LabeledRectangle( 110, 220, 40, 40 );
		LabeledRectangle lr2 = new LabeledRectangle( 120, 230, 20, 20 );
		if ( lr1.r.contains( lr2.r ) )
			lr1.label = "contain :)";
		else
			lr1.label = "no contain";
		rectanglesPanel.add( lr1 );
		rectanglesPanel.add( lr2 );

		// contains: Second case
		LabeledRectangle lr3 = new LabeledRectangle( 200, 225, 20, 20 );
		LabeledRectangle lr4 = new LabeledRectangle( 240, 235, 40, 40 );
		if ( lr3.r.contains( lr4.r ) )
			lr3.label = "contain :)";
		else
			lr3.label = "no contain";
		rectanglesPanel.add( lr3 );
		rectanglesPanel.add( lr4 );

		// contains: 3th case
		LabeledRectangle lr5 = new LabeledRectangle( 20, 300, 40, 50 );
		LabeledRectangle lr6 = new LabeledRectangle( 40, 315, 50, 20 );
		if ( lr5.r.contains( lr6.r ) )
			lr5.label = "contain :)";
		else
			lr5.label = "no contain";
		rectanglesPanel.add( lr5 );
		rectanglesPanel.add( lr6 );
	}

	public void testSmallestBoundingRectangle(){
		// smallestBoundingRectangle: First example
		Rectangle[] recs = { new Rectangle (120, 345, 20,10),
				new Rectangle (200, 350, 10,20),
				new Rectangle(215, 320, 25,15),
				new Rectangle (160, 310, 30,25),
				new Rectangle (150, 315, 20,30),
				new Rectangle (230, 325, 15,35)
		};
		Rectangle sbr = Rectangle.smallestBoundingRectangle(recs);
		LabeledRectangle lr1 = new LabeledRectangle(sbr.x, sbr.y, sbr.width, sbr.height);
		lr1.label = "                      calculated bounding rectangle";
		rectanglesPanel.add(lr1);
		int counter=0;
		for (Rectangle r : recs){
			LabeledRectangle lr = new LabeledRectangle(r.x, r.y, r.width, r.height);
			rectanglesPanel.add(lr);
			lr.label = ""+counter++;
		} 
	}	  

	public void testCompareTo(){
		// compareTo: First case    
		LabeledRectangle lr1 = new LabeledRectangle( 15, 410, 20, 20 );
		LabeledRectangle lr2 = new LabeledRectangle( 40, 410, 20, 20 );
		if ( lr1.r.compareAreaTo( lr2.r )==0 )
			lr1.label = "equal = 0";
		else if ( lr1.r.compareAreaTo( lr2.r )==1 )
			lr1.label = "bigger : 1";
		else
			lr1.label = "smaller : -1";
		rectanglesPanel.add( lr1 );
		rectanglesPanel.add( lr2 );

		// compareTo: Second case    
		LabeledRectangle lr3 = new LabeledRectangle( 115, 410, 40, 40 );
		LabeledRectangle lr4 = new LabeledRectangle( 125, 430, 40, 25 );
		if ( lr3.r.compareAreaTo( lr4.r )==0 )
			lr3.label = "equal = 0";
		else if ( lr3.r.compareAreaTo( lr4.r )==1 )
			lr3.label = "bigger : 1";
		else
			lr3.label = "smaller : -1";
		rectanglesPanel.add( lr3 );
		rectanglesPanel.add( lr4 );
	}

	public void testSection(){
		// section: First case    
		LabeledRectangle lr1 = new LabeledRectangle( 15, 525, 20, 20 );
		LabeledRectangle lr2 = new LabeledRectangle( 20, 530, 30, 30 );
		Rectangle sec = lr1.r.section(lr2.r);
		rectanglesPanel.add( lr1 );
		rectanglesPanel.add( lr2 );
		if (sec != null) {
			lr1.label = "filled section";
			rectanglesPanel.add(new LabeledRectangle(sec.x, sec.y, sec.width, sec.height, true));
		}
		// section: Second case    
		LabeledRectangle lr3 = new LabeledRectangle( 105, 525, 30, 40 );
		LabeledRectangle lr4 = new LabeledRectangle( 110, 530, 40, 20 );
		Rectangle sec2 = lr3.r.section(lr4.r);
		rectanglesPanel.add( lr3 );
		rectanglesPanel.add( lr4 );
		if (sec2 != null){
			lr3.label = "filled section";
			rectanglesPanel.add(new LabeledRectangle(sec2.x, sec2.y, sec2.width, sec2.height, true));
		}
		// section: 3th case    
		LabeledRectangle lr5 = new LabeledRectangle( 215, 520, 20, 40 );
		LabeledRectangle lr6 = new LabeledRectangle( 200, 530, 60, 20);	   
		Rectangle sec3 = lr5.r.section(lr6.r);
		rectanglesPanel.add( lr5 );
		rectanglesPanel.add( lr6 );
		if (sec3 != null){
			lr5.label = "filled section";
			rectanglesPanel.add(new LabeledRectangle(sec3.x, sec3.y, sec3.width, sec3.height, true));
		}
		// section: 4th case    
		LabeledRectangle lr7 = new LabeledRectangle( 20, 620, 60, 60 );
		LabeledRectangle lr8 = new LabeledRectangle( 30, 630, 30, 30 );
		Rectangle sec4 = lr7.r.section(lr8.r);
		rectanglesPanel.add( lr7 );
		rectanglesPanel.add( lr8 );
		if (sec4 != null){
			lr7.label = "filled section";
			rectanglesPanel.add(new LabeledRectangle(sec4.x, sec4.y, sec4.width, sec4.height, true));
		}
		// section: 5th case    
		LabeledRectangle lr9 = new LabeledRectangle( 120, 620, 20, 30 );
		LabeledRectangle lr10 = new LabeledRectangle( 146, 615, 30, 30 );
		Rectangle sec5 = lr9.r.section(lr10.r);
		rectanglesPanel.add( lr9 );
		rectanglesPanel.add( lr10 );
		lr9.label = "no section";
		
		if (sec5 != null){
			rectanglesPanel.add(new LabeledRectangle(sec5.x, sec5.y, sec5.width, sec5.height, true));
		}
		// overlaps: 6th case    
		LabeledRectangle lr11 = new LabeledRectangle( 220, 620, 30, 30 );
		LabeledRectangle lr12 = new LabeledRectangle( 200, 660, 30, 20 );
		Rectangle sec6 = lr11.r.section(lr12.r);
		rectanglesPanel.add( lr11 );
		rectanglesPanel.add( lr12 );
		lr11.label = "no section";
		
		if (sec6 != null){
			rectanglesPanel.add(new LabeledRectangle(sec6.x, sec6.y, sec6.width, sec6.height, true));
		}
		// overlaps: 7th case    
		LabeledRectangle lr13 = new LabeledRectangle( 20, 720, 30, 30 );
		LabeledRectangle lr14 = new LabeledRectangle( 10, 715, 30, 20 );
		Rectangle sec7 = lr13.r.section(lr14.r);
		rectanglesPanel.add( lr13 );
		rectanglesPanel.add( lr14 );
		
		if (sec7 != null){
			lr13.label = "filled section";
			rectanglesPanel.add(new LabeledRectangle(sec7.x, sec7.y, sec7.width, sec7.height, true)); 
		}
	}

	public class RectanglesPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		ArrayList <LabeledRectangle> rects;
		Random rand = new Random();
		Font font = new Font("Arial",Font.BOLD,10);

		public RectanglesPanel(int width, int height) {
			this.setSize(width, height);
		}

		public RectanglesPanel() {
			rects = new ArrayList<LabeledRectangle>();
			setBackground( Color.black );
		}

		public void add( LabeledRectangle r ) {
			rects.add( r );
		}

		public Color randomColor(){
			int nb = 75; // to avoid a random dark color
			int maxValue = 255-nb;
			return new Color(rand.nextInt(maxValue)+nb,rand.nextInt(maxValue)+nb,rand.nextInt(maxValue)+nb);
		}

		public void paint( Graphics g ) {
			super.paint(g);
			g.setFont(font);
			for ( int i=0; i<rects.size(); i++ ) {
				LabeledRectangle lr = rects.get(i);
				int x=(int) lr.r.x, y= (int)lr.r.y, width=(int) lr.r.width, height=(int) lr.r.height;
				g.setColor( (Color) randomColor() );
				if (lr.filled)
					g.fillRect( x, y, width, height );
				else
					g.drawRect( x, y, width, height );
				g.setColor(Color.WHITE);
				g.drawString(lr.label, x, y-7);
			}
		}

	} // end of class RectanglesPanel

	public class LabeledRectangle{

		String label;
		Rectangle r;
		boolean filled;

		public LabeledRectangle(int x, int y, int width, int height){
			r = new Rectangle(x, y, width, height);
			label = "";
		}  

		public LabeledRectangle(int x, int y, int width, int height, boolean filled){
			r = new Rectangle(x, y, width, height);
			label = "";
			this.filled = filled;
		}
		
		public LabeledRectangle(double x, double y, double width, double height, boolean filled){
			r = new Rectangle(x, y, width, height);
			label = "";
			this.filled = filled;
		}
		
		public LabeledRectangle(double x, double y, double width, double height){
			r = new Rectangle(x, y, width, height);
			label = "";
		}

	} // end of class LabeledRectangle

	public static void main( String[] argv ) {   
		new TestRectangle();
	}

} // end of class TestRectangle
