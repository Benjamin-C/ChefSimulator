package benjaminc.chef_simulator.graphics;

import java.awt.Graphics;

/**
 * Drawables to put on the {@link GamePanel}
 * 
 * @author Benjamin-C
 */
public interface Drawable {
	
	/**
	 * Draws the Drawable
	 * @param g the {@link Graphics} to draw on
	 * @param x the int x ofset
	 * @param y the int y ofset
	 * @param boxWidth the int width of each cell
	 * @param boxHeight the int height of each cell
	 * @param fps the {@link Double} fps
	 */
	public abstract void draw(Graphics g, int x, int y, int boxWidth, int boxHeight, double fps);
	
	/**
	 * Gets the width of the Drawable in px
	 * @return the int width in px
	 */
	public abstract int getWidth();
	/**
	 * Gets the height of the Drawable in px
	 * @return the int height in px
	 */
	public abstract int getHeight();
}
