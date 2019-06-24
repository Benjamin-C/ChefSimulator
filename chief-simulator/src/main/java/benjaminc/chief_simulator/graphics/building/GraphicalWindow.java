package benjaminc.chief_simulator.graphics.building;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.control.Direction;
import benjaminc.chief_simulator.data.DataMap;
import benjaminc.chief_simulator.data.DataMapKey;
import benjaminc.chief_simulator.graphics.GraphicalThing;
import benjaminc.chief_simulator.things.food.FoodState;

public class GraphicalWindow implements GraphicalThing {

	public static final int VARIANT_COUNT = 1;
	protected DataMap dataMap;
	public GraphicalWindow(DataMap data) {
		dataMap = data;
	}	
	
	@Override
	@SuppressWarnings("unused")
	public void draw(Graphics g, int x, int y, int w, int h) {
		int variant = (Integer) dataMap.get(DataMapKey.VARIANT);
		Direction dir = (Direction) dataMap.get(DataMapKey.DIRECTION);
		FoodState state = (FoodState) dataMap.get(DataMapKey.FOOD_STATE);
		
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
