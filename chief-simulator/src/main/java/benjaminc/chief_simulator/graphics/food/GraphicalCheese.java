package benjaminc.chief_simulator.graphics.food;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

import benjaminc.chief_simulator.graphics.GraphicalThing;
import benjaminc.chief_simulator.things.data.DataMapKey;
import benjaminc.chief_simulator.things.data.DataMapValue;
import benjaminc.chief_simulator.things.data.InvalidDatatypeException;
import benjaminc.chief_simulator.things.food.FoodState;

public class GraphicalCheese implements GraphicalThing {
	
	public static final int VARIANT_COUNT = 2;
	protected Map<DataMapKey, DataMapValue> dataMap;
	public GraphicalCheese(Map<DataMapKey, DataMapValue> data) {
		dataMap = data;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		
		FoodState state = null;
		int variant = -1;
		try {
			state = dataMap.get(DataMapKey.FOOD_STATE).getFoodState();
			variant = dataMap.get(DataMapKey.VARIANT).getInt();
		} catch (InvalidDatatypeException e) { e.printStackTrace(); }

		g.setColor(Color.YELLOW);
		g.fillRect(x+(w/20), y+(h/20), w-(w/10), h-(h/10));
	}
}
