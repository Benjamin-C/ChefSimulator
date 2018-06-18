package benjaminc.chief_simulator.things.building;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.things.types.AttachedThing;

public class Floor implements AttachedThing {

	public Floor() {
		super();
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(new Color(255, 128, 0));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(128, 64, 0));
		g.drawRect(x,  y,  w,  h);
	}
}
