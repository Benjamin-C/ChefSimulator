package benjaminc.chef_simulator.graphics;

import java.awt.Color;

public class LagOBar {
	
	protected double value;
	protected int height;
	protected Color color;
	
	public LagOBar(double value, int height, Color color) {
		super();
		this.value = value;
		this.height = height;
		this.color = color;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
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
