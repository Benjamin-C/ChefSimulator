package benjaminc.chief_simulator.things;

import java.awt.Color;
import java.awt.Graphics;

public class Counter extends SolidThing {
	public Counter() {
		super();
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(new Color(64, 64, 64));
		g.fillRect(x,  y,  w,  h);
	}
}
