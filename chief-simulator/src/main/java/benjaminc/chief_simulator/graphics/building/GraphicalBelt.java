package benjaminc.chief_simulator.graphics.building;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.control.Direction;
import benjaminc.chief_simulator.data.DataMap;
import benjaminc.chief_simulator.data.DataMapKey;
import benjaminc.chief_simulator.graphics.DirectionalGraphicalThing;

public class GraphicalBelt implements DirectionalGraphicalThing {

	public static final int VARIANT_COUNT = 1;
	protected DataMap dataMap;
	public GraphicalBelt(DataMap data) {
		dataMap = data;
	}	
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		@SuppressWarnings("unused")
		int variant = (Integer) dataMap.get(DataMapKey.VARIANT);
		Direction dir = (Direction) dataMap.get(DataMapKey.DIRECTION);
		
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
