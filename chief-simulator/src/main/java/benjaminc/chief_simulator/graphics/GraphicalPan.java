package benjaminc.chief_simulator.graphics;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.graphics.GraphicalThing;
import benjaminc.chief_simulator.things.food.FoodState;

public class GraphicalPan implements GraphicalThing {

	public static final int VARIANT_COUNT = 1;
	protected int variant;
	public GraphicalPan(int variant) {
		this.variant = variant;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(new Color(16, 16, 16));
		g.fillOval(x+(int)(w*0.05),  y+(int)(h*0.05), (int)(w*0.9),  (int)(h*0.9));
		g.setColor(new Color(32, 32, 32));
		g.fillOval(x+(int)(w*0.15),  y+(int)(h*0.15), (int)(w*0.7),  (int)(h*0.7));
	}

	@Override
	public void setVariant(int var) {
		variant = var;
	}

	@Override
	public void setState(FoodState state) {
		// TODO Auto-generated method stub
		
	}

}
