package benjaminc.chef_simulator.graphics.building;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.graphics.DirectionalGraphicalThing;
import benjaminc.chef_simulator.graphics.GraphicalThing;

public class GraphicalBelt extends GraphicalThing implements DirectionalGraphicalThing {

	public GraphicalBelt(DataMap data) {
		super(data);
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		prep();
		
		g.setColor(new Color(7, 99, 186));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x,  y,  w,  h);
		g.setColor(new Color(186, 99, 7));
		switch(dir) {
		case UP: g.drawLine(x + (w/2), y + (h/2), x + (w/2), y); break; // up
		case DOWN: g.drawLine(x + (w/2), y + (h/2), x + (w/2), y + h); break; // down
		case LEFT: g.drawLine(x + (w/2), y + (h/2), x, y + (h/2)); break; // left
		case RIGHT: g.drawLine(x + (w/2), y + (h/2), x + w, y + (h/2));break; // right
		}
	}
}
