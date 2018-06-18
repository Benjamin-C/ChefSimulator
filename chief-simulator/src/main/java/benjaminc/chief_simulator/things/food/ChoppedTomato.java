package benjaminc.chief_simulator.things.food;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.things.Thing;

public class ChoppedTomato extends Tomato{

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(new Color(255, 0, 0));
		g.fillRect(x+1,  y+1,  w-2,  h-2);
		g.setColor(Color.GRAY);
		g.drawLine(x+(w/2), y, x+(w/2), y+h);
	}
	
	@Override
	public Thing duplicate() {
		return new ChoppedTomato();
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