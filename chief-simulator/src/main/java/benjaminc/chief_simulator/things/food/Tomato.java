package benjaminc.chief_simulator.things.food;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.FoodThing;

public class Tomato implements FoodThing{

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(new Color(255, 0, 0));
		g.fillOval(x+(int)(w*.05), y+(int)(h*.12), (int)(w*.9), (int)(h*.76));
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
