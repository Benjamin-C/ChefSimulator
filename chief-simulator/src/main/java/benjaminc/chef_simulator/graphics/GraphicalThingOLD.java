package benjaminc.chef_simulator.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import benjaminc.chef_simulator.control.Direction;
import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.DataMapKey;
import benjaminc.chef_utils.data.FoodState;
import benjaminc.chef_utils.graphics.Shape;
import benjaminc.chef_utils.graphics.Texture;

public class GraphicalThingOLD {

	protected int variant;
	protected int variantCount;
	protected Direction dir;
	protected FoodState state;
	
	protected Texture shapeList;
	
	protected DataMap dataMap;
	
	public GraphicalThingOLD(DataMap data) {
		this(data, null);
	}
	public GraphicalThingOLD(DataMap data, Texture shape) {
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
		List<Shape> s = new ArrayList<Shape>();
		if(dataMap.containsKey(DataMapKey.FOOD_STATE)) {
			s = shapeList.get((FoodState) dataMap.get(DataMapKey.FOOD_STATE));
		} else {
			s = shapeList.get(FoodState.RAW);
		}
		if(s != null && s.size() > 0) {
			for(int i = s.size() - 1; i >= 0; i--) {
				s.get(i).draw(w, h, x, y, g);
			}
		} else {
			g.setColor(Color.MAGENTA);
			g.fillRect(x, y, w/2, h/2);
			g.fillRect(x + w/2, y + h/2, w/2, h/2);
			g.setColor(Color.BLACK);
			g.fillRect(x, y + h/2, w/2, h/2);
			g.fillRect(x + w/2, y, w/2, h/2);
			g.setColor(Color.WHITE);
			g.setFont(g.getFont().deriveFont(w/2f));
			String st = "s " + (w/32f);
			g.drawString(st, x, y);
		}
	}
}