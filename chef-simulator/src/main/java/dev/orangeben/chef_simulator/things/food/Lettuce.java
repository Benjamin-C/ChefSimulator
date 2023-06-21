package dev.orangeben.chef_simulator.things.food;

import java.util.UUID;

import dev.orangeben.chef_simulator.data.DataMap;
import dev.orangeben.chef_simulator.data.FoodState;
import dev.orangeben.chef_simulator.data.InvalidDatatypeException;
import dev.orangeben.chef_simulator.data.DataMap.DataMapKey;
import dev.orangeben.chef_simulator.things.BasicThing;
import dev.orangeben.chef_simulator.things.Thing;
import dev.orangeben.chef_simulator.things.types.Choppable;
import dev.orangeben.chef_simulator.things.types.FoodThing;

public class Lettuce extends BasicThing implements FoodThing, Choppable {
	
	protected final static int VARIANT_COUNT = 1;
	public Lettuce() {
		this(null, null);
	}
	public Lettuce(DataMap dataMap, UUID uuid) {
		super(dataMap, Lettuce.class, uuid);
	}
	public Lettuce(int variant, FoodState state) {
		super(variant, state, VARIANT_COUNT, Lettuce.class);
	}
	
//	@Override
//	public void draw(Graphics g, int x, int y, int w, int h) {
//		graphics.draw(g, x, y, w, h);
//	}
	
	public Thing getChoppedThing() {
		try { dataMap.put(DataMapKey.FOOD_STATE, FoodState.CHOPPED);
		} catch (InvalidDatatypeException e) { e.printStackTrace(); }
		return this;
	}
	
	@Override
	public boolean isSame(Thing t) {
		if(t.getClass() == this.getClass()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setState(FoodState state) {
		try { dataMap.put(DataMapKey.FOOD_STATE, state);
		} catch (InvalidDatatypeException e) { e.printStackTrace(); };
	}
	public FoodState getState() {
		try { return (FoodState) dataMap.get(DataMapKey.FOOD_STATE);
		} catch (InvalidDatatypeException e) { e.printStackTrace(); return null; }
	}
	@Override
	public DataMap getDataMap() {
		return dataMap;
	}
}
