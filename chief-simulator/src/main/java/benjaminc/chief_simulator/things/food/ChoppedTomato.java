package benjaminc.chief_simulator.things.food;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.things.Thing;

public class ChoppedTomato extends Tomato{

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(new Color(255, 0, 0));
		g.fillOval(x+(int)(w*.05), y+(int)(h*.05), (int)(w*.9), (int)(h*.9));
		g.setColor(new Color(200, 0, 0));
		g.fillOval(x+(int)(w*.1), y+(int)(h*.1), (int)(w*0.8), (int)(h*0.8));
		g.setColor(new Color(255, 0, 0));
		g.fillRect(x+(int)(w*0.45), y+(int)(h*.1), (int)(w*0.1), (int)(h*0.8));
		g.fillRect(x+(int)(w*.1), y+(int)(h*0.45), (int)(w*0.8), (int)(h*0.1));
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
