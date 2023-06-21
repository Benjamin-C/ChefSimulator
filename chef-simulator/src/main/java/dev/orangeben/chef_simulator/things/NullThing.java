package dev.orangeben.chef_simulator.things;

import dev.orangeben.chef_simulator.data.DataMap;
import dev.orangeben.chef_simulator.data.FoodState;
import dev.orangeben.chef_simulator.data.DataMap.DataMapKey;
import dev.orangeben.chef_simulator.things.BasicThing;
import dev.orangeben.chef_simulator.things.Thing;

public class NullThing extends BasicThing {

	protected final static int VARIANT_COUNT = 1;
	public NullThing() {
		this(null);
	}
	public NullThing(DataMap dataMap) {
		super(dataMap, VARIANT_COUNT, NullThing.class);
	}
	public NullThing(int variant, FoodState state) {
		super(variant, state, VARIANT_COUNT, NullThing.class);
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
