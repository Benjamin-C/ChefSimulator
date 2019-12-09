package benjaminc.chef_simulator.graphics;

import java.awt.Color;
import java.awt.Graphics;

public class ChatBoxElement {

	/** the String message */
	protected String msg;
	/** the long time in frames to remove the message */
	protected long timeout;
	
	/**
	 * @param msg the {@link String} message
	 * @param timeout the long timeout
	 */
	public ChatBoxElement(String msg, long timeout) {
		this.msg = msg;
		this.timeout = timeout;
	}

	/**
	 * Draws the chat box
	 * @param g the {@link Graphics} to draw on
	 * @param x the int X location
	 * @param y the int Y location
	 * @param width the int width of the box in pixels
	 * @param size int the size of the text
	 * @param text the {@link Color} of the text
	 * @param back the {@link Color} of the background
	 * @return the int height of the textbox
	 */
	public int draw(Graphics g, int x, int y, int width, int size, Color text, Color back) {
		g.setFont(g.getFont().deriveFont(size));
		
		int height = g.getFontMetrics().getHeight();
		
		int xloc = x;
		int yloc = y - g.getFontMetrics().getDescent();
		
		g.setColor(back);
		g.fillRect(x, y-height, width, height);
		
		g.setColor(text);
		g.drawString(msg, xloc, yloc);
		
		return height;
	}
	
	/**
	 * @return the {@link String} msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the {@link String} msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the long timeout
	 */
	public long getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the long timeout to set
	 */
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	
}
