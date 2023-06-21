package dev.orangeben.chef_simulator.chef_graphics.debug;

import java.awt.Color;

public class DataOBar {
	
	protected double fps;
	protected int height;
	protected Color color;
	protected boolean special;
	
	public DataOBar(double fps, boolean special) {
		super();
		this.fps = fps;
		this.special = special;
	}

	public DataOBar update(double maxFPS, int maxHeight, int av) {
		double value = fps / maxFPS;

		height = (int) (value * maxHeight);
		
		int rv = 128;
		int gv = 128;
		
		rv = rv + (int) (rv * (1 - value));
		gv = gv + (int) (gv * value);
		
		if(special) {
			color = new Color(128, 128, 128, av);
		} else {
			color = new Color(Math.max(Math.min(rv, 255), 0), Math.max(Math.min(gv, 255), 0), 0, Math.max(Math.min(av, 255), 0));
		}
		return this;
	}
	public double getFPS() {
		return fps;
	}
	public void setFPS(double fps) {
		this.fps = fps;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
