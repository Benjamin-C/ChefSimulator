package benjaminc.chief_simulator.graphics.food;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

import benjaminc.chief_simulator.data.DataMapKey;
import benjaminc.chief_simulator.data.DataMapValue;
import benjaminc.chief_simulator.data.InvalidDatatypeException;
import benjaminc.chief_simulator.graphics.GraphicalThing;
import benjaminc.chief_simulator.things.food.FoodState;

public class GraphicalLettuce implements GraphicalThing {

	public static final int VARIANT_COUNT = 1;
	protected Map<DataMapKey, DataMapValue> dataMap;
	public GraphicalLettuce(Map<DataMapKey, DataMapValue> data) {
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

		
		switch(state) {
		case CHOPPED: {
			g.setColor(new Color(0, 180 ,0));
			g.fillRect(x+(int)(w*0.9), y+(int)(h*.425), (int)(w*.1), (int)(h*.15));
			g.setColor(new Color(0, 255, 0));
			g.fillOval(x+(w/20),  y+(h/5),  w-(int)(w*.15),  h-(int)(h/2.5));
		} break;
		case CHOPPED_COOKED:
			break;
		case COOKED:
			break;
		case RAW: {
			g.setColor(new Color(0, 255, 0));
			g.fillOval(x+(w/20),  y+(h/20),  w-(w/10),  h-(h/10));
		} break;
		}
	}
}
