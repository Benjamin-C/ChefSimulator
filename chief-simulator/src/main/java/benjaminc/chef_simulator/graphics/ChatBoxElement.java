package benjaminc.chef_simulator.graphics;

import java.awt.Color;
import java.awt.Graphics;

public class ChatBoxElement {

	/** the String message */
	protected String msg;
	/** the long time in frames to remove the message */
	protected long timeout;
	
	/** the int size that the {@link ChatBoxElement} was calced for */
	private int calcSize = 0;
	/** the int width that the {@link ChatBoxElement} was calced for */
	private int calcWidth = 0;
	
	
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
		g.setFont(g.getFont().deriveFont((float) size));
		
		if(calcSize != size || calcWidth != width) {
			int textWidth = g.getFontMetrics().stringWidth(msg);
			if(textWidth > width) {
				String str[] = msg.split(" ");
				msg = "";
				for(int i = 0; i < str.length; i++) {
					if(!str[i].contains("\n")) {
						if(g.getFontMetrics().getStringBounds(msg.substring(Math.max(0, msg.lastIndexOf("\n"))), g).getWidth() > width*.9) {
							msg += "\n";
						} else if (i != 0) {
							msg += " ";
						}
						msg += str[i];
					} else {
						msg += " " + str[i];
					}
				}
				
			}
			calcSize = size;
			calcWidth = width;
		}
		
		int height = 0;
		String str[] = msg.split("\n");
		for(int i = str.length-1; i >= 0 ; i--) {
			int h = g.getFontMetrics().getHeight();
			
			int yloc = y - g.getFontMetrics().getDescent();
			
			g.setColor(back);
			g.fillRect(x, y-height-h, width, h);
			
			g.setColor(text);
			g.drawString(str[i], x, yloc-height);
			
			height += h;
		}
		
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
