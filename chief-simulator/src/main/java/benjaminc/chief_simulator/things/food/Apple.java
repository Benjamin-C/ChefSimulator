package benjaminc.chief_simulator.things.food;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.FoodThing;

public class Apple implements FoodThing {

	public Apple() {
		super();
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		int five_twelths = (int) ((int) w*0.4583333333333333333);
		g.setColor(new Color(255, 0, 0));
		g.fillOval(x+(int)(w*0.05),  y+(int)(h*0.05), (int)(w*0.9),  (int)(h*0.9));
		g.setColor(new Color(0, 128, 0));
		g.fillRect(x+five_twelths, y-(int)(h/16), (int)(w/12), (int)(h/4));
	}
	public Thing getChoppedThing() {
		return new ChoppedApple();
	}

	@Override
	public Thing duplicate() {
		return new Apple();
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
