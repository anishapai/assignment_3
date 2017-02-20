import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D; // optional, for drawing lines with varying thickness
import java.awt.BasicStroke; // optional, for drawing lines with varying thickness
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import javax.swing.JComponent;

/*
 * This class implements tree painting and has the same functionality except the
 * background is changed from black to white for winter.
 *
 */

public class WinterTreePainting extends TreePainting {

	protected void paintBackground(Graphics graphic) {

		// sets colour to black...
		graphic.setColor(Color.WHITE);

		// ... and creates a rectangle for the background
		graphic.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
	}
}