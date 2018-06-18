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
	}

	@Override
	public Thing getChoppedThing() {
		return new ChoppedTomato();
	}

}
