package benjaminc.chief_simulator.graphics.building;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.graphics.GraphicalThing;
import benjaminc.chief_simulator.things.food.FoodState;

public class GraphicalFloor implements GraphicalThing {

	public static final int VARIANT_COUNT = 1;
	protected int variant;
	public GraphicalFloor(int variant) {
		this.variant = variant;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(new Color(180, 180, 180));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(120, 120, 120));
		g.fillRect(x,  y,  w/2,  h/2);
		g.fillRect(x+(int)(w*.5),  y+(int)(h*.5),  w/2,  h/2);
		g.setColor(new Color(20, 20, 20));
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
