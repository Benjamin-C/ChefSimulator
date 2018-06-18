package benjaminc.chief_simulator.things.food;

import java.awt.Color;
import java.awt.Graphics;

public class ChoppedTomato extends Tomato{

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(new Color(255, 0, 0));
		g.fillRect(x+1,  y+1,  w-2,  h-2);
		g.setColor(Color.GRAY);
		g.drawLine(x+(w/2), y, x+(w/2), y+h);
	}
}
