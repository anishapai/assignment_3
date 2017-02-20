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

public class SummerTreePainting extends TreePainting {

	protected void paintBackground(Graphics graphic) {

		// creates a blue rectangle for the sky
		graphic.setColor(new Color (135, 206, 235));
		graphic.fillRoundRect(0, 0, getWidth(), getHeight()/2+60, 0, 0);

		// and a yellow rectangle for the sand
		graphic.setColor(Color.YELLOW);
		graphic.fillRoundRect(0, 0+60+getHeight()/2, getWidth(), getHeight()/2, 0, 0);

	}
}