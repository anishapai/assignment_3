import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import javax.swing.JComponent;

/* 
 *This class will allow the user to draw trees on a black background. The user can
 * choose the colour of the trunk and number of branches, which will be the same colour
 * as the trunk, but their angle and the colour of the blossom will be chosen at random.
 * @Author Anisha Pai
 * @Version 02/14/2017
 *
 */

public class TreePainting extends JComponent implements MouseListener {

	/** Number of branches **/
	public static final int NUM_BRANCHES = 8; 
 
	/** Golden ratio makes the trunk length:branch length ratio aesthetically 
	 * appealing **/
	public static final double GOLDEN_RATIO = 1.618;

	/** Thickness of the trunk. **/
	public static final int TRUNK_THICKNESS = 9;

	/** Stores the x & y coordinates of where the mouse is pressed.. **/
	private double lastX;
	private double lastY;
	
	/**... and where the mouse is released **/
	private double thisX;
	private double thisY;
	
	/** rgb float values for the brown colour of the trunk and branches **/
	float r = 150/255f;
	float g = 106/255f;
	float b = 11/255f;
	
	/** float value for the transparency of the trunk and branches **/
	float alpha = 9/20f;
	
	


	
	/*
	 *  This method is the constructor.
	 * It makes sure the class is listening for mouse events. 
	 * 
	 * */
	
	public TreePainting() {
		addMouseListener(this);
	}





	
	/* 
	 * This method calls the methods that draw on the canvas 
	 * and set the background to black. 
	 * 
	 * It is called via repaint() in the mouse release method
	 * to draw the tree.
	 * 
	 * */
	
	public void paint(Graphics graphic) {
		// sets background to black
		paintBackground(graphic);

		// makes sure lastX & lastY have values before drawing tree
		if (lastX != 0 && lastY != 0) 
		drawTree(graphic, lastX, lastY, thisX, thisY);
	}





	
	/* 
	 * This method is called in paint and will set the background 
	 * to black by creating a filled rectangle. 
	 * 
	 * */
	
	protected void paintBackground(Graphics graphic) {
		
		// sets colour to black...
		graphic.setColor(Color.BLACK);

		// ... and creates a rectangle for the background
		graphic.fillRoundRect(getX(), getY(), getWidth(), getHeight(), 45, 30);
	}



	
	/* 
	 * This calls methods that draw the separate parts of the tree.
	 * 
	 * @param graphic is the graphics interface
	 * @param beginX the X coordinate of where the line begins
	 * @param beginY the Y coordinate of where the line begins
	 * @param endX the X coordinate of where the line ends
	 * @param endY the Y coordinate of where the line ends
	 * 
	 * */
	
	public void drawTree(Graphics graphic, double beginX, double beginY, double endX, double endY) {
		drawTrunk(graphic, beginX, beginY, endX, endY);
		drawBranches(graphic, beginX, beginY, endX, endY);
	}
	


	
	
	/*
	 * This method draws the tree trunk brown and translucent from where the user
	 * clicks the mouse to where the mouse is released.
	 * 
	 * @param graphic is the graphics interface
	 * @param beginX the X coordinate of where the trunk begins
	 * @param beginY the Y coordinate of where the trunk begins
	 * @param endX the X coordinate of where the trunk ends
	 * @param endY the Y coordinate of where the trunk ends
	 */
		
	public void drawTrunk(Graphics graphic, double beginX, double beginY, double endX, double endY) {

		
		// sets the colour, and makes it translucent
		graphic.setColor(new Color(r, g, b, alpha));
		
		// float value of thickness of the lines
		float pt = TRUNK_THICKNESS+1f;
		
		// sets thickness of line
		BasicStroke l = new BasicStroke(pt, 1, 1);
		((Graphics2D)graphic).setStroke(l);
		
		// draws line to begin at mouse press and end at mouse release
		graphic.drawLine((int) beginX, (int)beginY, (int)endX, (int)endY);
	}

	




	
	/*
	 * This method draws the branches stemming from the end of the trunk where the
	 * user released the mouse. They will be proportional in length to the trunk via
	 * the golden ratio, shoot out at random angles and be both brown and translucent.
	 * 
	 * @param graphic is the graphics interface
	 * @param beginX the X coordinate of where the trunk begins
	 * @param beginY the Y coordinate of where the trunk begins
	 * @param endX the X coordinate of where the trunk ends
	 * @param endY the Y coordinate of where the trunk ends
	 */
		
	public void drawBranches(Graphics graphic, double beginX, double beginY, double endX, double endY) {
		
		// point where the trunk ends and branches will begin
		Point2D p = new Point2D.Double(endX, endY);
		
		// absolute values for lengths of a right-angle triangle where the trunk is the hypotenuse
		double lengthX = Math.abs(beginX-endX);
		double lengthY = Math.abs(beginY-endY);
		double hypotenuse = Math.hypot(lengthX,lengthY);
		
		// booleans that determine in which quartet of the radian circle the trunk has been drawn
		boolean right2left = beginX>endX;
		boolean down2up = beginY>endY;
		
		// will hold the angle of the trunk from the eastern region of the radian circle
		double stemAngle;
		
		// computes the angle of the trunk
		if (right2left && down2up)
			stemAngle = Math.atan(lengthY/lengthX);
		else if (down2up)
			stemAngle = Math.toRadians(180) - Math.atan(lengthY/lengthX) ;
		else if (right2left)
			stemAngle = Math.toRadians(360) - Math.atan(lengthY/lengthX);
		else
			stemAngle = Math.atan(lengthY/lengthX)+Math.toRadians(180);

		// float value to reduce thickness of the lines
		float pt = TRUNK_THICKNESS - 4f;
		
		// sets thickness of line
		((Graphics2D)graphic).setStroke(new BasicStroke(pt, 1, 1));

			
		/* draws branches at least 90 degrees away from the trunk */
		for (int i = 0; i<NUM_BRANCHES; i++) {
			double randomAngle = Math.random()*Math.toRadians(180);
			
			// finds the end point of each new branch
			Point2D newPoint = computeEndpoint(p, hypotenuse/GOLDEN_RATIO , stemAngle + Math.toRadians(90) + randomAngle);
			
			// sets the colour back to brown, and makes it translucent
			graphic.setColor(new Color(r, g, b, alpha));
			
			//draws the branch
			graphic.drawLine((int)endX, (int)endY, (int)newPoint.getX(), (int)newPoint.getY());
			// draws the blossom
			drawBlossom(graphic, newPoint.getX(), newPoint.getY());
		}	
	}
	


	
	
	
	/*
	 * This method calculates the endpoint of a branch starting at the tree trunk.
	 * (Taken from Assignment2 Instructions)
	 * 
	 */
	public Point2D computeEndpoint( Point2D p, double length, double angle )
	{
	    return new Point2D.Double( p.getX() + length*Math.cos(angle), // x is cos
                		       p.getY() + length*Math.sin(angle)); // y is sin
	}
	

	


	/*
	 * This method draws the blossoms on the ends of the branches in random colours.
	 * 
	 * @Param graphic the graphics interface
	 * @Param x the x coordinate of where the blossom should go
	 * @Param y the y coordinate of where the blossom should go
	 * 
	 */
	
	public void drawBlossom (Graphics graphic, double x, double y) {
		// rgb values for a random colour
		double r = Math.random()*255;
		double g = Math.random()*255;
		double b = Math.random()*255;
		
		// creates new colour
		graphic.setColor(new Color ((int)r, (int)g, (int)b));
		
		//the thickness of the blossom is triple the thickness of the lines
		double diam = TRUNK_THICKNESS*2.5;
		
		//draws the oval so it is centered at the end of the branch
		graphic.fillOval((int)(x-diam/2), (int)(y-diam/2), (int)diam, (int)diam);
	}





	/*
	 * Mouse press method records the x and y values of the
	 * point where the mouse is clicked
	 *
	 */
	
	public void	mousePressed(MouseEvent e) {
		// the X&Y values of where the mouse is pressed
		lastX = e.getX();
		lastY = e.getY();
	}



	/*
	 * Mouse release method records the x and y value of the
	 * point where the mouse click is released and calls
	 * the pain method again, to paint the tree.
	 *
	 */

	public void	mouseReleased(MouseEvent e) {
		// the X&Y values of where the mouse is released
		thisX = e.getX();
		thisY = e.getY();
		
		// call the paint method again to draw the tree
		repaint();
	}

	/** Required methods for MouseListener (unused) **/


	public void mouseClicked(MouseEvent e){
		
	}
	public void	mouseEntered(MouseEvent e) {
		
	}
	public void	mouseExited(MouseEvent e) {
		
	}
	
}
