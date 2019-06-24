package benjaminc.chief_simulator.graphics.building;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

import benjaminc.chief_simulator.data.DataMapKey;
import benjaminc.chief_simulator.data.DataMapValue;
import benjaminc.chief_simulator.graphics.GraphicalThing;

public class GraphicalCuttingBoard implements GraphicalThing {

	public static final int VARIANT_COUNT = 1;
	protected Map<DataMapKey, DataMapValue> dataMap;
	public GraphicalCuttingBoard(Map<DataMapKey, DataMapValue> data) {
		dataMap = data;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		int indw = w / 8;
		int indh = h / 8;
		g.setColor(new Color(64, 64, 64));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x,  y,  w,  h);
		g.setColor(new Color(235, 235, 235));
		g.fillRect(x+indw,  y+(int)(h*.075),  w-(2*indw),  h-indh);
		g.setColor(new Color(64, 64, 64));
		g.fillOval(x+(int)(w*.35), y+(int)(h*.1), (int)(w*.3), (int)(h*.1));
		g.setColor(new Color(200, 200 ,200));
		g.drawRect(x+(int)(w*0.175), y+(int)(h*0.25), (int)(w*0.65), (int)(h*.65));
	}
}
