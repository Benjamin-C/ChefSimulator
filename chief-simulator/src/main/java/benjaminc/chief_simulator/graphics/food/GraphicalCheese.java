package benjaminc.chief_simulator.graphics.food;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.graphics.GraphicalThing;
import benjaminc.chief_simulator.things.food.FoodState;

public class GraphicalCheese implements GraphicalThing {
	
	public static final int VARIANT_COUNT = 2;
	protected int variant;
	protected FoodState state;
	public GraphicalCheese(int variant, FoodState state) {
		System.out.println(variant + " " + state);
		this.variant = variant;
		this.state = state;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(Color.YELLOW);
		g.fillRect(x+(w/20), y+(h/20), w-(w/10), h-(h/10));
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
