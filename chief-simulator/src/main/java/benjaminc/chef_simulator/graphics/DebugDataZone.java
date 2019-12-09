package benjaminc.chef_simulator.graphics;

import java.awt.Color;
import java.awt.Graphics;

public class DebugDataZone {

	/** the {@link String} title to show */
	protected String title;
	/** the {@link DebugDataZoneDataGetter} to get the data to display */
	protected DebugDataZoneDataGetter data;
	/** The {@link String} units of the data */
	protected String unit;
	/** the boolean if the display is enabled */
	protected boolean enabled;
	/** the int size of the display */
	protected int size;
	/** the {@link Color} text color */
	protected Color textColor;
	/** the {@link Color} background color */
	protected Color backgroundColor;
	
	/**
	 * @param the {@link String} title to show
	 * @param the [type] data to display
	 * @param The {@link String} units of the data
	 */
	public DebugDataZone(String title, DebugDataZoneDataGetter data, String unit) {
		this(title, data, unit, true);
	}

	/**
	 * @param the {@link String} title to show
	 * @param the [type] data to display
	 * @param The {@link String} units of the data
	 * @param the boolean if the display is enabled
	 */
	public DebugDataZone(String title, DebugDataZoneDataGetter data, String unit, boolean enabled) {
		this(title, data, unit, enabled, 16, new Color(255, 255, 255, 192), new Color(64, 64, 64, 128));
	}

	/**
	 * @param the {@link String} title to show
	 * @param the [type] data to display
	 * @param The {@link String} units of the data
	 * @param the boolean if the display is enabled
	 * @param the int size of the display
	 * @param the {@link Color} text color
	 * @param the {@link Color} background color
	 */
	public DebugDataZone(String title, DebugDataZoneDataGetter data, String unit, boolean enabled, int size, Color textColor, Color backgroundColor) {
		this.title = title;
		this.data = data;
		this.unit = unit;
		this.enabled = enabled;
		this.size = size;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}

	/**
	 * Draws the {@link DebugDataZone}. The data is gotten from {@link DebugDataZoneDataGetter#getData()}.
	 * @param g the {@link Graphics} to draw on
	 * @param x the int x location
	 * @param y the in ty location
	 * @param datastr the {@link String} data to show
	 * @return the int height of the meter. Returns -1 if the {@link DebugDataZoneDataGetter} is null.
	 */
	public int draw(Graphics g, int x, int y) {
		if(data!= null) {
			return draw(g, x, y, data.getData());
		} else {
			return -1;
		}
	}
	
	/**
	 * Draws the {@link DebugDataZone}
	 * @param g the {@link Graphics} to draw on
	 * @param x the int x location
	 * @param y the in ty location
	 * @param datastr the {@link String} data to show
	 * @return the int height of the meter
	 */
	public int draw(Graphics g, int x, int y, String datastr) {
		int height = 0;
		
		if(enabled) {
			String dta = title + ": " + datastr + " " + unit;
			
			g.setFont(g.getFont().deriveFont(size));
			
			int width = g.getFontMetrics().stringWidth(dta);
			height = g.getFontMetrics().getHeight();
			
			int xloc = x;
			int yloc = y + g.getFontMetrics().getLeading() + g.getFontMetrics().getAscent();
			
			g.setColor(backgroundColor);
			g.fillRect(x, y, width, height);
			
			g.setColor(textColor);
			g.drawString(dta, xloc, yloc);
		}
		
		return height;
	}
	
	/**
	 * @return the {@link String} title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the {@link String} title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the {@link DebugDataZoneDataGetter} data
	 */
	public DebugDataZoneDataGetter getData() {
		return data;
	}

	/**
	 * @param data the {@link DebugDataZoneDataGetter} data to set
	 */
	public void setData(DebugDataZoneDataGetter data) {
		this.data = data;
	}

	/**
	 * @return the {@link String} unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit the {@link String} unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @return the {@link boolean} enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the {@link boolean} enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the int size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the int size to set
	 */
	public void setSize(int size) {
		this.size = size;
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

	public interface DebugDataZoneDataGetter {
		abstract String getData();
	}
}
