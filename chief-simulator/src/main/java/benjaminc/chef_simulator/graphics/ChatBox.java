package benjaminc.chef_simulator.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import benjaminc.chef_simulator.Game;

public class ChatBox {
	
	protected List<ChatBoxElement> elems;
	protected int maxElems;
	protected int width;
	protected int textsize;
	protected Color backgroundColor;
	protected Color textColor;
	protected String osData;
	protected int textTime;
	
	private long lastTick = 0;
	
	public PrintStream out;
	
	/**
	 * @param maxElems the int max number of elements
	 * @param width the int widht in pixels
	 * @param textsize the int text size
	 * @param backgroundColor the {@link Color} background color
	 * @param textColor the {@link Color} text color
	 * @param textTime the int time to show the text in game ticks
	 */
	public ChatBox(int maxElems, int width, int textsize, Color backgroundColor, Color textColor, int textTime) {
		out = new PrintStream(new OutputStream() {
			@Override
			public void write(byte[] dta, int start, int length) {
				String inStr = "";
				start = Math.min(start, dta.length);
				if(start+length > dta.length) {
					length = dta.length-start;
				}
				for(int i = start; i < start + length; i++) {
					inStr = inStr + (char) dta[i];
				}
				String splitStr[] = inStr.split("\\r?\\n");
				for(String s : splitStr) {
					addElement(s, textTime);
				}
			}
			
			@Override
			public void write(int arg0) throws IOException {
				addElement((char) arg0 + "", textTime);
			}
		});
		this.elems = new ArrayList<ChatBoxElement>();
		this.maxElems = maxElems;
		this.width = width;
		this.textsize = textsize;
		this.backgroundColor = backgroundColor;
		this.textColor = textColor;
		this.textTime = textTime;
	}

	/**
	 * Adds an element to the ChatBox
	 * @param msg the String message to show
	 * @param time the long time to display the message for in frames
	 */
	public void addElement(String msg, int time) {
		elems.add(new ChatBoxElement(msg, lastTick + time));
		if(Game.isMultiplayer()) {
			try {
				if(Game.getServer() != null && Game.getServer().getOutputStream() != null) {
					Game.getServer().getOutputStream().write(msg.getBytes());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Draws the ChatBox
	 * @param g the {@link Graphics} to draw on
	 * @param x the int x position of lower right corner
	 * @param y the int y position of lower right corner
	 */
	public void draw(Graphics g, int x, int y, long frame) {
		int i = elems.size() - 1;
		int loc = y;
		while(i >= 0) {
			if(i >= elems.size()) {
				i = elems.size() - 1;
			} else if(elems.get(i).getTimeout() < frame) {
				elems.remove(i);
				i++;
			} else {
				loc -= elems.get(i).draw(g, x, loc, width, textsize, textColor, backgroundColor);
				i--;
			}
		}
		lastTick = frame;
	}
	
	/**
	 * @return the {@link List} of {@link ChatBoxElement} elems
	 */
	public List<ChatBoxElement> getElems() {
		return elems;
	}

	/**
	 * @param elems the {@link List} of {@link ChatBoxElement} elems to set
	 */
	public void setElems(List<ChatBoxElement> elems) {
		this.elems = elems;
	}

	/**
	 * @return the int maxElems
	 */
	public int getMaxElems() {
		return maxElems;
	}

	/**
	 * @param maxElems the int maxElems to set
	 */
	public void setMaxElems(int maxElems) {
		this.maxElems = maxElems;
	}

	/**
	 * @return the int width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the int width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the int textsize
	 */
	public int getTextsize() {
		return textsize;
	}

	/**
	 * @param textsize the int textsize to set
	 */
	public void setTextsize(int textsize) {
		this.textsize = textsize;
	}

	/**
	 * @return the {@link Color} backgroundColor
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param backgroundColor the {@link Color} backgroundColor to set
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @return the {@link Color} textColor
	 */
	public Color getTextColor() {
		return textColor;
	}

	/**
	 * @param textColor the {@link Color} textColor to set
	 */
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
}
