package benjaminc.chef_simulator.things.food;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.FoodState;
import benjaminc.chef_simulator.data.InvalidDatatypeException;
import benjaminc.chef_simulator.data.keys.DataMapKey;
import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.Choppable;
import benjaminc.chef_simulator.things.types.Cookable;
import benjaminc.chef_simulator.things.types.FoodThing;

public class Beef extends BasicThing implements FoodThing, Cookable, Choppable {

	protected final static int VARIANT_COUNT = 1;
	public Beef() {
		this(null);
	}
	public Beef(DataMap dataMap) {
		super(dataMap, VARIANT_COUNT, Beef.class);
	}
	public Beef(int variant, FoodState state) {
		super(variant, state, VARIANT_COUNT, Beef.class);
	}
	
//	@Override
//	public void draw(Graphics g, int x, int y, int w, int h) {
//		graphics.draw(g, x, y, w, h);
//	}
	
	@Override
	public Thing getChoppedThing() {
		try {
			if(dataMap.get(DataMapKey.FOOD_STATE) == FoodState.RAW) {
				dataMap.put(DataMapKey.FOOD_STATE, FoodState.CHOPPED);
			}
		} catch (InvalidDatatypeException e) { e.printStackTrace(); }
		return this;
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
	public boolean isSame(Thing t) {
		if(t.getClass() == this.getClass()) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public Thing getCookedThing() {
		try {
			switch((FoodState) dataMap.get(DataMapKey.FOOD_STATE)) {
			case CHOPPED: {
				dataMap.put(DataMapKey.FOOD_STATE, FoodState.CHOPPED_COOKED);
			} break;
			case CHOPPED_COOKED:
				break;
			case COOKED:
				break;
			case RAW: {
				dataMap.put(DataMapKey.FOOD_STATE, FoodState.COOKED);
			} break;
			default: break;
			}
		} catch (InvalidDatatypeException e) { e.printStackTrace(); }
		return this;
	}
	@Override
	public DataMap getDataMap() {
		return dataMap;
	}
}
