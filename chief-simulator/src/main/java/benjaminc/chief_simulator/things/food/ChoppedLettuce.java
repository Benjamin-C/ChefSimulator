package benjaminc.chief_simulator.things.food;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.FoodThing;

public class ChoppedLettuce extends Lettuce{
	public ChoppedLettuce() {
		super();
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(new Color(0, 255, 0));
		g.fillOval(x+(w/20),  y+(h/20),  w-(w/10),  h-(h/10));
		g.setColor(Color.GRAY);
		g.drawLine(x+(w/2), y+(h/20), x+(w/2), y+(h-(h/20)));
	}

	@Override
	public Thing duplicate() {
		return new ChoppedLettuce();
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
