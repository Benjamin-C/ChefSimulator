package benjaminc.chief_simulator.graphics.building;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

import benjaminc.chief_simulator.graphics.GraphicalThing;
import benjaminc.chief_simulator.things.data.DataMapKey;
import benjaminc.chief_simulator.things.data.DataMapValue;

public class GraphicalWindow implements GraphicalThing {

	public static final int VARIANT_COUNT = 1;
	protected Map<DataMapKey, DataMapValue> dataMap;
	public GraphicalWindow(Map<DataMapKey, DataMapValue> data) {
		dataMap = data;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		int indw = w / 8;
		int indh = h / 8;
		g.setColor(new Color(100, 100, 100));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x,  y,  w,  h);
		g.setColor(new Color(225, 225, 225));
		g.fillRect(x+(int)(indw*.7),  y,  w-(int)(1.4*indw),  h-(int)(indh*.7));
		g.setColor(new Color(192, 192, 192));
		g.fillRect(x+indw,  y,  w-(2*indw),  h-indh);
	}
}
