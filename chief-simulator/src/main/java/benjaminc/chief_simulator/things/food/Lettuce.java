package benjaminc.chief_simulator.things.food;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.FoodThing;

public class Lettuce implements FoodThing{
	public Lettuce() {
		super();
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(new Color(0, 255, 0));
		g.fillOval(x+(w/20),  y+(h/20),  w-(w/10),  h-(h/10));
	}
	
	@Override
	public Thing getChoppedThing() {
		return new ChoppedLettuce();
	}
	
	@Override
	public Thing duplicate() {
		return new Lettuce();
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
