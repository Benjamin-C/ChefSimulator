package dev.orangeben.chef_simulator.things.food;

import java.util.UUID;

import dev.orangeben.chef_simulator.data.DataMap;
import dev.orangeben.chef_simulator.data.FoodState;
import dev.orangeben.chef_simulator.data.DataMap.DataMapKey;
import dev.orangeben.chef_simulator.things.BasicThing;
import dev.orangeben.chef_simulator.things.Thing;
import dev.orangeben.chef_simulator.things.types.Choppable;
import dev.orangeben.chef_simulator.things.types.FoodThing;

public class Apple extends BasicThing implements FoodThing, Choppable {

	protected final static int VARIANT_COUNT = 1;
	public Apple() {
		this(null, null);
	}
	public Apple(DataMap dataMap, UUID uuid) {
		super(dataMap, Apple.class, uuid);
	}
	public Apple(int variant, FoodState state) {
		super(variant, state, VARIANT_COUNT, Apple.class);
	}
	
//	@Override
//	public void draw(Graphics g, int x, int y, int w, int h) {
//		graphics.draw(g, x, y, w, h);
//	}
	public Thing getChoppedThing() {
		dataMap.put(DataMapKey.FOOD_STATE, FoodState.CHOPPED);
		return this;
	}

	public void setState(FoodState state) {
		dataMap.put(DataMapKey.FOOD_STATE, state);
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
