package benjaminc.chef_simulator.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 * @author 705822
 *
 */
public class PauseScreen {
	
	private Color backgroundColor;
	private Color textColor;
	private String options = "[esc] Resume game\n[F1] Help\n[F2] Exit\n[F3] Debug\n";
	public PauseScreen(Color background, Color text) {
		this.backgroundColor = background;
		this.textColor = text;
	}
	
	/**
	 * Draws the {@link PauseScreen}. Scales to fit the allowed area.
	 * @param g the {@link Graphics} to draw on
	 * @param x the int x location
	 * @param y the int y location
	 * @param w the int width of the allowed area
	 * @param h the int height of the allowed area
	 */
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(backgroundColor);
		g.fillRect(x, y, w, h);
		
		g.setFont(g.getFont().deriveFont(128f));
		g.setColor(textColor);
		
		Rectangle2D txt = g.getFontMetrics().getStringBounds("TT paused", g);
		int xloc = (int) (w-txt.getWidth())/2;
		int yloc = (int) (h+txt.getHeight())/4;
		g.drawString("TT paused", xloc , yloc);
		
		g.setFont(g.getFont().deriveFont(16f));
		yloc += g.getFontMetrics().getHeight();
		String[] sarr = options.split("\n");
		for(String s : sarr) {
			yloc += g.getFontMetrics().getHeight();
			g.drawString(s, xloc, yloc);
		}
	}

}
