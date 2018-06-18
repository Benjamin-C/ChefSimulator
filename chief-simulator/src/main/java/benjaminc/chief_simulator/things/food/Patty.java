package benjaminc.chief_simulator.things.food;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.FoodThing;

public class Patty implements FoodThing {

	public Patty() {
		super();
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(new Color(210, 105, 30));
		g.fillOval(x+(int)(w*0.05),  y+(int)(h*0.05), (int)(w*0.9),  (int)(h*0.9));
	}
	
	@Override
	public Thing getChoppedThing() {
		return this;
	}

	@Override
	public Thing duplicate() {
		return new Patty();
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