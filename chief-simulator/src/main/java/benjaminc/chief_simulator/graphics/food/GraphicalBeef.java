package benjaminc.chief_simulator.graphics.food;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.graphics.GraphicalThing;
import benjaminc.chief_simulator.things.food.FoodState;

public class GraphicalBeef implements GraphicalThing {

	public static final int VARIANT_COUNT = 1;
	protected int variant;
	protected FoodState state;
	public GraphicalBeef(int variant, FoodState state) {
		this.variant = variant;
		this.state = state;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		switch(state) {
		case CHOPPED: {
			g.setColor(new Color(225, 60, 30));
			g.fillOval(x+(int)(w*0.05),  y+(int)(h*0.05), (int)(w*0.9),  (int)(h*0.9));
		} break;
		case CHOPPED_COOKED: {
			g.setColor(new Color(140, 70, 30));
			g.fillOval(x+(int)(w*0.05),  y+(int)(h*0.05), (int)(w*0.9),  (int)(h*0.9));
		} break;
		case COOKED: {
			g.setColor(new Color(140, 70, 30));
			g.fillRect(x+(int)(w*0.05),  y+(int)(h*0.05), (int)(w*0.9),  (int)(h*0.9));
		} break;
		case RAW: {
			g.setColor(new Color(225, 60, 30));
			g.fillRect(x+(int)(w*0.05),  y+(int)(h*0.05), (int)(w*0.9),  (int)(h*0.9));
		} break;
		}
	}
	
	@Override
	public void setState(FoodState state) {
		this.state = state;
	}

	@Override
	public void setVariant(int var) {
		variant = var;
	}

}
