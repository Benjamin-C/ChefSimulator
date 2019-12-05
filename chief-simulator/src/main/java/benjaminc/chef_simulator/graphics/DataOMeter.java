package benjaminc.chef_simulator.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import benjaminc.util.Util;

/**
 * @author Benjamin-C
 *
 */
public class DataOMeter {

	/** the boolean to show {@link DataOMeter} */
	private boolean enabled;
	/** the {@link Double} max value to display */
	private double maxValue;
	/** the {@link Double} last max balue */
	private double lastMax;
	/** the {@link List} of {@link DataOBar} to store the lagometer data in */
	private List<DataOBar> data;
	/** the long ms for the next special line to be drawn at */
	private long nextlinems;
	/** The delay between lines */
	private int linedel;
	/** the height of the {@link DataOMeter} */
	private int height;
	/** the width of the {@link DataOMeter} */
	private int width;
	/** the boolean to lock the max value */
	private boolean lockMax = false;
	/** the {@link String} title */
	private String title;
	
	/**
	 * Makes a {@link DataOMeter}
	 * @param enabled the {@link Boolean} of enabled
	 * @param linedel the int time between special lines
	 * @param width the int width of the meter
	 * @param height the int height of the meter
	 * @param title the {@link String} title of the meter
	 */
	public DataOMeter(boolean enabled, int linedel, int width, int height, String title) {
		super();
		this.enabled = enabled;
		this.linedel = linedel;
		this.height = height;
		this.width = width;
		this.title = title;
		data = new ArrayList<DataOBar>();
	}
	
	/**
	 * Makes a {@link DataOMeter}, locking the max value
	 * @param enabled the {@link Boolean} of enabled
	 * @param linedel the int time between special lines
	 * @param width the int width of the meter
	 * @param height the int height of the meter
	 * @param maxValue the int max value
	 * @param title the {@link String} title of the meter
	 */
	public DataOMeter(boolean enabled, int linedel, int width, int height, int maxValue, String title) {
		this(enabled, linedel, width, height, title);
		
		this.maxValue = maxValue;
		lockMax = true;
		data = new ArrayList<DataOBar>();
	}
	
	/**
	 * Sets the max value of the meter
	 * @param max the int max meter
	 */
	public void setMax(int max) {
		maxValue = max;
	}
	/**
	 * Sets the new width of the {@link DataOMeter}
	 * @param width the int new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * Gets the current width
	 * @return the int width
	 */
	public int getWidth() { 
		return width;
	}
	/**
	 * Sets if the datameter is enabled
	 * @param enabled the boolean if enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * See if the datameter is enabled
	 * @return a boolean of if enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * Draws the datameter
	 * @param g the Graphics object to draw in
	 * @param fps how many fps to add to the list
	 */
	public void draw(Graphics g, int left, int bottom, double fps) {
		if(title.contains("tps")) {
			if(fps > 100) {
				System.out.println("Big TPS");
			}
		}
		if(title.contains("heap")) {
			if(data != null && data.size() > 0) {
				if(fps > data.get(Math.max(data.size()-1, 0)).getFPS()+10) {
					System.out.println("Heap jump");
					Util.showHeapStats(true);
				}
			}
		}
		int av = 255-64;
		
		int side = width;
		
		if(!lockMax) {
			lastMax = maxValue;
			
			maxValue = 5;
			for(DataOBar l : data) {
				maxValue = Math.max(maxValue, l.getFPS());
			}
			maxValue = Math.max(maxValue, fps);
		}
		
		if(nextlinems < System.currentTimeMillis()) {
			data.add(new DataOBar(fps, true).update(maxValue, height, av));
			nextlinems = System.currentTimeMillis() + linedel;
		} else {
			data.add(new DataOBar(fps, false).update(maxValue, height, av));
		}
		
		while(data.size() > width) {
			data.remove(0);
		}
		
		if(enabled) {
			boolean recalc = maxValue != lastMax;
			
			for(int i = 0; i < data.size(); i++) {
				DataOBar l = data.get(i);
				if(recalc) {
					l.update(maxValue, height, av);
				}
				g.setColor(l.getColor());
				g.drawLine(left+i, bottom, left+i, (int) (bottom - l.getHeight()));
			}
			g.setColor(new Color(255, 0, 0, 255));
			double locs[] = {.5d, .75d, 1d};
			g.setColor(new Color(255, 0, 0, 255));
			g.setFont(g.getFont().deriveFont(12f));
			for(int i = 0; i < locs.length; i++) {
				int l = bottom - (int) (locs[i] * height);
				g.drawLine(left, l, left+side, l);
				String txt = Integer.toString((int) (maxValue * locs[i]));
				int height = g.getFontMetrics().getHeight();
				g.drawString(txt, left, l + (int) (height*.9));
			}
			g.drawLine(left, bottom, left, bottom-height);
			g.setColor(Color.WHITE);
			g.setFont(g.getFont().deriveFont(16f));
			String fpss = Double.toString(round(fps, 1));
			g.drawString(fpss.substring(0, Math.min(fpss.length(), 16)) + " " + title, left, bottom);
		}
	}
	
	private Double round(Double num, int places) {
		double mlt = Math.pow(10, places);
		return Math.round(num*mlt)/mlt;
	}
	public int getMeterSize() {
		return data.size();
	}
}
