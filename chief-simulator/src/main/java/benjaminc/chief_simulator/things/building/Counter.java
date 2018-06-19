package benjaminc.chief_simulator.things.building;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.graphics.building.GraphicalCounter;
import benjaminc.chief_simulator.graphics.food.GraphicalApple;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.SolidThing;

public class Counter implements SolidThing {
	
	protected GraphicalCounter graphics;
	
	public Counter() {
		this(0);
	}
	public Counter(int variant) {
		super();
		graphics = new GraphicalCounter(variant);
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
	}

	@Override
	public Thing duplicate() {
		return new Counter();
	}

	@Override
	public boolean isSame(Thing t) {
		if(t.getClass() == this.getClass()) {
			return true;
		} else {
			return false;
		}
	}
}
