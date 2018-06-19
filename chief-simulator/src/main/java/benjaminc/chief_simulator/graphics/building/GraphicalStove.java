package benjaminc.chief_simulator.graphics.building;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.graphics.GraphicalThing;
import benjaminc.chief_simulator.things.food.FoodState;

public class GraphicalStove implements GraphicalThing {

	public static final int VARIANT_COUNT = 1;
	protected int variant;
	public GraphicalStove(int variant) {
		this.variant = variant;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		int indw = w / 8;
		int indh = h / 8;
		g.setColor(new Color(64, 64, 64));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x,  y,  w,  h);
		g.setColor(new Color(255, 128, 0));
		g.fillRect(x + (w/4), y + (w/4), (w/2), (h/2));
	}
	
	@Override
	public void setState(FoodState state) {
		
	}

	@Override
	public void setVariant(int var) {
		variant = var;
	}

}
