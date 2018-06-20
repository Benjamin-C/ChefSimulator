package benjaminc.chief_simulator.things.food;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import benjaminc.chief_simulator.graphics.food.GraphicalApple;
import benjaminc.chief_simulator.graphics.food.GraphicalPotato;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.FoodThing;

public class Potato implements FoodThing {

	protected GraphicalPotato graphics;
	protected int variant;
	protected FoodState state;
	
	public Potato() {
		this(-1, FoodState.RAW);
	}
	public Potato(int variant, FoodState state) {
		super();
		if(variant == -1) {
			Random r = new Random();
			variant = r.nextInt(GraphicalPotato.VARIANT_COUNT);
		}
		this.state = state;
		graphics = new GraphicalPotato(variant, state);
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
	}
	public Thing getChoppedThing() {
		if(state == FoodState.RAW) {
			state = FoodState.CHOPPED;
			graphics.setState(state);
		}
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
	@Override
	public Thing duplicate() {
		return new Potato(variant, state);
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
		switch(state) {
		case CHOPPED: state = FoodState.CHOPPED_COOKED;
			break;
		case CHOPPED_COOKED:
			break;
		case COOKED:
			break;
		case RAW: state = FoodState.COOKED;
			break;
		}
		graphics.setState(state);
		return this;
	}
}
