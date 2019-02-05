package benjaminc.chief_simulator.graphics;

import java.awt.Graphics;

import benjaminc.chief_simulator.things.food.FoodState;

public interface GraphicalThing {

	public abstract void draw(Graphics g, int x, int y, int w, int h);
}
