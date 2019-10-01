package benjaminc.chef_simulator.things.food;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.DataMapKey;
import benjaminc.chef_simulator.data.InvalidDatatypeException;
import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.Choppable;
import benjaminc.chef_simulator.things.types.Cookable;
import benjaminc.chef_simulator.things.types.FoodThing;
import benjaminc.chef_simulator.things.types.Fryable;
import benjaminc.chef_utils.data.FoodState;

public class Potato extends BasicThing implements FoodThing, Cookable, Choppable, Fryable {

	protected final static int VARIANT_COUNT = 1;
	public Potato() {
		this(null);
	}
	public Potato(DataMap dataMap) {
		super(dataMap, VARIANT_COUNT, Potato.class);
	}
	public Potato(int variant, FoodState state) {
		super(variant, state, VARIANT_COUNT, Potato.class);
	}
	
//	@Override
//	public void draw(Graphics g, int x, int y, int w, int h) {
//		graphics.draw(g, x, y, w, h);
//	}
	
	@Override
	public Thing getChoppedThing() {
		try { dataMap.put(DataMapKey.FOOD_STATE, FoodState.CHOPPED);
		} catch (InvalidDatatypeException e) { e.printStackTrace(); }
		return this;
	}

	public void setVariant(int var) {
		try { dataMap.put(DataMapKey.VARIANT, var);
		} catch (InvalidDatatypeException e) { e.printStackTrace(); }
	}
	public void setState(FoodState state) {
		try { dataMap.put(DataMapKey.FOOD_STATE, state);
		} catch (InvalidDatatypeException e) { e.printStackTrace(); };
	}
	public int getVariant() {
		try { return (int) dataMap.get(DataMapKey.VARIANT);
		} catch (InvalidDatatypeException e) { e.printStackTrace(); return -1; }
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
			case CHOPPED:
				dataMap.put(DataMapKey.FOOD_STATE, FoodState.CHOPPED_COOKED);
				break;
			case CHOPPED_COOKED:
				break;
			case COOKED:
				break;
			case RAW: 
				dataMap.put(DataMapKey.FOOD_STATE, FoodState.COOKED);
				break;
			default: break;
			}
		} catch (InvalidDatatypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
	@Override
	public Thing getFriedThing() {
		try {
			switch((FoodState) dataMap.get(DataMapKey.FOOD_STATE)) {
			case CHOPPED: dataMap.put(DataMapKey.FOOD_STATE, FoodState.CHOPPED_COOKED);
				break;
			case CHOPPED_COOKED:
				break;
			case COOKED:
				break;
			case RAW:
				break;
			default: break;
			}
		} catch (InvalidDatatypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
	@Override
	public DataMap getDataMap() {
		return dataMap;
	}
}
