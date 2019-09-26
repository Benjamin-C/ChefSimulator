package benjaminc.chef_simulator.things.food;

import java.awt.Graphics;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.DataMapKey;
import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.Choppable;
import benjaminc.chef_simulator.things.types.FoodThing;
import benjaminc.chef_utils.data.FoodState;

public class Apple extends BasicThing implements FoodThing, Choppable {

	protected final static int VARIANT_COUNT = 1;
	public Apple() {
		this(null);
	}
	public Apple(DataMap dataMap) {
		super(dataMap, VARIANT_COUNT, Apple.class);
	}
	public Apple(int variant, FoodState state) {
		super(variant, state, VARIANT_COUNT, Apple.class);
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
	}
	public Thing getChoppedThing() {
		dataMap.put(DataMapKey.FOOD_STATE, FoodState.CHOPPED);
		return this;
	}

	public void setVariant(int var) {
		dataMap.put(DataMapKey.VARIANT, var);
	}
	public void setState(FoodState state) {
		dataMap.put(DataMapKey.FOOD_STATE, state);
	}
	public int getVariant() {
		return (int) dataMap.get(DataMapKey.VARIANT);
	}
	public FoodState getState() {
		return (FoodState) dataMap.get(DataMapKey.FOOD_STATE);
	}
	
	@Override
	public boolean isSame(Thing t) {
		if(t.getClass() == this.getClass()) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public DataMap getDataMap() {
		return dataMap;
	}
}
