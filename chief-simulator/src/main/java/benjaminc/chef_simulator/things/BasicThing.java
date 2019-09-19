package benjaminc.chef_simulator.things;

import java.awt.Graphics;
import java.util.Random;

import benjaminc.chef_simulator.control.Direction;
import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.DataMapKey;
import benjaminc.chef_simulator.data.FoodState;
import benjaminc.chef_simulator.graphics.GraphicalLoader;
import benjaminc.chef_simulator.graphics.GraphicalThing;

public class BasicThing implements Thing {

	protected GraphicalThing graphics;
	protected DataMap dataMap;
	protected Class<?> subclass;
	
	public BasicThing(DataMap dataMap, Class<?> subclass) {
		this.subclass = subclass;
		this.dataMap = dataMap;
		if(this.dataMap == null) {
			this.dataMap = new DataMap();
		}
		if(!this.dataMap.containsKey(DataMapKey.VARIANT_COUNT)) {
			this.dataMap.put(DataMapKey.VARIANT_COUNT, 1);
		}
		if(!this.dataMap.containsKey(DataMapKey.VARIANT)) {
			Random r = new Random();
			this.dataMap.put(DataMapKey.VARIANT_COUNT, r.nextInt((int) this.dataMap.get(DataMapKey.VARIANT_COUNT)));
		}
		if(!this.dataMap.containsKey(DataMapKey.DIRECTION)) {
			this.dataMap.put(DataMapKey.DIRECTION, Direction.UP);
		}
		
		String pkg = this.subclass.getPackage().getName();
		pkg = pkg.substring(pkg.lastIndexOf(".")+1);
		graphics = GraphicalLoader.load(pkg + "/" + this.subclass.getSimpleName(), this.dataMap);
	}
	public BasicThing(DataMap dataMap, int variantCount, Class<?> subclass) {
		this(dataMap, subclass);
		this.dataMap.put(DataMapKey.VARIANT_COUNT, variantCount);
	}
	public BasicThing(int variant, FoodState state, int variantCount, Class<?> subclass) {
		this(null, variantCount, subclass);
		dataMap.put(DataMapKey.VARIANT, variant);
		dataMap.put(DataMapKey.FOOD_STATE, state);
	}
	
	@Override
	public boolean equals(Thing t) {
		if(t instanceof BasicThing) {
			BasicThing bt = (BasicThing) t;
			if(bt.getSubclass().equals(subclass)) {
				if(dataMap.equals(t.getDataMap())) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public Thing clone() {
		return new BasicThing(dataMap.clone(), subclass);
		
	}
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
	}
	@Override
	public boolean isSame(Thing t) {
		if(!t.getClass().equals(getClass())) {
			return false;
		} else {
			if(! t.getDataMap().equals(dataMap)) {
				return false;
			}
		}
		return false;
	}
	@Override
	public DataMap getDataMap() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Class<?> getSubclass() {
		return subclass;
	}
}
