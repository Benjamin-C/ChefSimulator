package benjaminc.chef_simulator.things.building;

import benjaminc.chef_simulator.control.Direction;
import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.DataMapKey;
import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.DirectionalThing;
import benjaminc.chef_simulator.things.types.SolidThing;
import benjaminc.chef_utils.data.FoodState;

public class DishCounter extends BasicThing implements SolidThing, DirectionalThing {
	
	protected final static int VARIANT_COUNT = 1;
	public DishCounter() {
		this(null, null);
	}
	public DishCounter(DataMap dataMap) {
		this(dataMap, null, null);
	}
	public DishCounter(Direction dir, FoodState fs) {
		this(null, dir, fs);
	}
	public DishCounter(DataMap dm, Direction dir, FoodState fs) {
		super(dm, VARIANT_COUNT, DishCounter.class);
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
