package benjaminc.chief_simulator.things.building;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.graphics.building.GraphicalFloor;
import benjaminc.chief_simulator.graphics.food.GraphicalApple;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.AttachedThing;

public class Floor implements AttachedThing {

	protected GraphicalFloor graphics;
	
	public Floor() {
		this(0);
	}
	public Floor(int var) {
		graphics = new GraphicalFloor(var);
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
	}

	@Override
	public Thing duplicate() {
		return new Floor();
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
