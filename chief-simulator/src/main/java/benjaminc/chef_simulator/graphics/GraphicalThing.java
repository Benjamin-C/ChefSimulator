package benjaminc.chef_simulator.graphics;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chef_simulator.control.Direction;
import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.DataMapKey;
import benjaminc.chef_simulator.data.FoodState;

public class GraphicalThing {

	protected int variant;
	protected int variantCount;
	protected Direction dir;
	protected FoodState state;
	
	protected Texture shapeList;
	
	protected DataMap dataMap;
	
	public GraphicalThing(DataMap data) {
		this(data, null);
	}
	public GraphicalThing(DataMap data, Texture shape) {
		this.shapeList = shape;
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
	
	public void setShapeList(Texture shape) {
		this.shapeList = shape;
	}
	public Texture getShapeList() {
		return shapeList;
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
		if(shapeList != null && shapeList.size() > 0) {
			for(int i = shapeList.size() - 1; i >= 0; i--) {
				shapeList.get(i).draw(x, y, w, h, g);
			}
		} else {
			g.setColor(Color.MAGENTA);
			g.fillRect(x, y, w/2, h/2);
			g.fillRect(x + w/2, y + h/2, w/2, h/2);
			g.setColor(Color.BLACK);
			g.fillRect(x, y + h/2, w/2, h/2);
			g.fillRect(x + w/2, y, w/2, h/2);
		}
	}
}