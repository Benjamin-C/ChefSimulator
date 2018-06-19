package benjaminc.chief_simulator.things.building;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.AttachedThing;

public class Floor implements AttachedThing {

	public Floor() {
		super();
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(new Color(180, 180, 180));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(120, 120, 120));
		g.fillRect(x,  y,  w/2,  h/2);
		g.fillRect(x+(int)(w*.5),  y+(int)(h*.5),  w/2,  h/2);
		g.setColor(new Color(20, 20, 20));
		g.drawRect(x,  y,  w,  h);
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
