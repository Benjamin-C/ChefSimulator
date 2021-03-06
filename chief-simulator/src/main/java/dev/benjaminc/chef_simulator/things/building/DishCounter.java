package dev.benjaminc.chef_simulator.things.building;

import java.util.UUID;

import dev.benjaminc.chef_simulator.control.Direction;
import dev.benjaminc.chef_simulator.data.DataMap;
import dev.benjaminc.chef_simulator.data.FoodState;
import dev.benjaminc.chef_simulator.data.DataMap.DataMapKey;
import dev.benjaminc.chef_simulator.things.BasicThing;
import dev.benjaminc.chef_simulator.things.Thing;
import dev.benjaminc.chef_simulator.things.types.DirectionalThing;
import dev.benjaminc.chef_simulator.things.types.SolidThing;

public class DishCounter extends BasicThing implements SolidThing, DirectionalThing {
	
	protected final static int VARIANT_COUNT = 1;
	public DishCounter() {
		this(null, (UUID) null);
	}
	public DishCounter(DataMap dataMap, UUID uuid) {
		super(dataMap, DishCounter.class, uuid);
	}
	public DishCounter(Direction dir, FoodState fs) {
		this(null, dir, fs);
	}
	public DishCounter(DataMap dm, Direction dir, FoodState fs) {
		super(dm, VARIANT_COUNT, DishCounter.class);
		if(dm == null) {
			if(dir != null) {
				dataMap.put(DataMapKey.DIRECTION, dir);
			} else {
				dataMap.put(DataMapKey.DIRECTION, Direction.UP);
			}
			if(fs != null) {
				dataMap.put(DataMapKey.FOOD_STATE, fs);
			} else {
				dataMap.put(DataMapKey.FOOD_STATE, FoodState.RAW);
			}
		}
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
	@Override
	public void setDirection(Direction d) {
		dataMap.put(DataMapKey.DIRECTION, d);
	}
	@Override
	public Direction getDirection() {
		return (Direction) dataMap.get(DataMapKey.DIRECTION);
	}
}
