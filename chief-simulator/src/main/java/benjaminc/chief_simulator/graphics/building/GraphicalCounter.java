package benjaminc.chief_simulator.graphics.building;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

import benjaminc.chief_simulator.graphics.GraphicalThing;
import benjaminc.chief_simulator.things.data.DataMapKey;
import benjaminc.chief_simulator.things.data.DataMapValue;
import benjaminc.chief_simulator.things.food.FoodState;

public class GraphicalCounter implements GraphicalThing {

	public static final int VARIANT_COUNT = 1;
	protected Map<DataMapKey, DataMapValue> dataMap;
	public GraphicalCounter(Map<DataMapKey, DataMapValue> data) {
		dataMap = data;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {	
		@SuppressWarnings("unused")
		FoodState state = dataMap.get(DataMapKey.FOOD_STATE).getFoodState();
		
		g.setColor(new Color(64, 64, 64));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x,  y,  w,  h);
	}
}
