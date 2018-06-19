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
		g.fillRect(x+1, y+1, w-2, h-2);
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
