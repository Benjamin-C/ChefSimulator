package benjaminc.chief_simulator.things.food;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.FoodThing;

public class Tomato implements FoodThing{

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(new Color(255, 0, 0));
		g.fillRect(x+1,  y+1,  w-2,  h-2);
		g.setColor(new Color(200, 0, 0));
		g.fillRect(x+3, y+3, w/2, h/2);
	}

	@Override
	public Thing getChoppedThing() {
		return new ChoppedTomato();
	}
	
	@Override
	public Thing duplicate() {
		return new Tomato();
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
