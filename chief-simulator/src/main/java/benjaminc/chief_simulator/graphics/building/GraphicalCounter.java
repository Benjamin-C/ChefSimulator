package benjaminc.chief_simulator.graphics.building;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.graphics.GraphicalThing;
import benjaminc.chief_simulator.things.food.FoodState;

public class GraphicalCounter implements GraphicalThing {

	public static final int VARIANT_COUNT = 1;
	protected int variant;
	public GraphicalCounter(int variant) {
		this.variant = variant;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(new Color(64, 64, 64));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x,  y,  w,  h);
	}

	@Override
	public void setState(FoodState state) {
		
	}

	@Override
	public void setVariant(int var) {
		variant = var;
	}

}
