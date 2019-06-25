package benjaminc.chief_simulator.graphics;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.control.Direction;
import benjaminc.chief_simulator.data.DataMap;
import benjaminc.chief_simulator.data.DataMapKey;
import benjaminc.chief_simulator.data.FoodState;

public class GraphicalThing {

	protected int variant;
	protected int variantCount;
	protected Direction dir;
	protected FoodState state;
	
	protected DataMap dataMap;
	
	public GraphicalThing(DataMap data) {
		dataMap = data;
		if(dataMap == null) {
			dataMap = new DataMap();
		}
		makeSureHas(DataMapKey.VARIANT, 0);
		makeSureHas(DataMapKey.VARIANT_COUNT, 1);
		makeSureHas(DataMapKey.DIRECTION, Direction.UP);
		makeSureHas(DataMapKey.FOOD_STATE, FoodState.RAW);
		prep();
	}
	
	private void makeSureHas(DataMapKey k, Object v) {
		if(!dataMap.containsKey(k)) {
			dataMap.put(k, v);
		}
	}
	public void prep() {
		variant = (Integer) dataMap.get(DataMapKey.VARIANT);
		variantCount = (Integer) dataMap.get(DataMapKey.VARIANT_COUNT);
		dir = (Direction) dataMap.get(DataMapKey.DIRECTION);
		state = (FoodState) dataMap.get(DataMapKey.FOOD_STATE);
	}
	
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, w/2, h/2);
		g.fillRect(x + w/2, y + h/2, w/2, h/2);
		g.setColor(Color.BLACK);
		g.fillRect(x, y + h/2, w/2, h/2);
		g.fillRect(x + w/2, y, w/2, h/2);
	}
}