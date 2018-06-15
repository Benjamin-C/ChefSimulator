package benjaminc.chief_simulator.things;

import java.awt.Color;
import java.awt.Graphics;

public class Apple extends Thing {

	public Apple() {
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(new Color(255, 0, 0));
		g.fillRect(x+1,  y+1,  w-2,  h-2);
	}
}
