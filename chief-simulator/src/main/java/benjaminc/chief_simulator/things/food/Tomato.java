package benjaminc.chief_simulator.things.food;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import benjaminc.chief_simulator.graphics.food.GraphicalApple;
import benjaminc.chief_simulator.graphics.food.GraphicalBun;
import benjaminc.chief_simulator.graphics.food.GraphicalTomato;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.FoodThing;

public class Tomato implements FoodThing{

	protected GraphicalTomato graphics;
	protected int variant;
	protected FoodState state;
	
	public Tomato() {
		this(-1, FoodState.RAW);
	}
	public Tomato(int variant, FoodState state) {
		super();
		if(variant == -1) {
			Random r = new Random();
			variant = r.nextInt(GraphicalTomato.VARIANT_COUNT);
		}
		this.state = state;
		graphics = new GraphicalTomato(variant, state);
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
	}

	@Override
	public Thing getChoppedThing() {
		state = FoodState.CHOPPED;
		graphics.setState(state);
		return this;
	}
	
	@Override
	public Thing duplicate() {
		return new Tomato(variant, state);
	}
	
	@Override
	public boolean isSame(Thing t) {
		if(t.getClass() == this.getClass()) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public Thing getCookedThing() {
		return this;
	}
	public void setVariant(int var) {
		variant = var;
	}
	public void setState(FoodState state) {
		this.state = state;
	}
	public int getVariant() {
		return variant;
	}
	public FoodState getFoodState() {
		return state;
	}
}
