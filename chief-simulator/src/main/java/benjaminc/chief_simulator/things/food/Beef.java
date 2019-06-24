package benjaminc.chief_simulator.things.food;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import benjaminc.chief_simulator.data.DataMapKey;
import benjaminc.chief_simulator.data.DataMapValue;
import benjaminc.chief_simulator.data.InvalidDatatypeException;
import benjaminc.chief_simulator.graphics.food.GraphicalBeef;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.Choppable;
import benjaminc.chief_simulator.things.types.Cookable;
import benjaminc.chief_simulator.things.types.FoodThing;

public class Beef implements FoodThing, Cookable, Choppable {

	protected GraphicalBeef graphics;
	protected Map<DataMapKey, DataMapValue> dataMap;
	
	public Beef() {
		this(-1, FoodState.RAW);
	}
	public Beef(Map<DataMapKey, DataMapValue> data) {
		dataMap = data;
		graphics = new GraphicalBeef(dataMap);
	}
	public Beef(int variant, FoodState state) {
		super();
		if(variant == -1) {
			Random r = new Random();
			variant = r.nextInt(GraphicalBeef.VARIANT_COUNT);
		}
		
		graphics = new GraphicalBeef(dataMap);
		
		dataMap = new HashMap<DataMapKey, DataMapValue>();
		try {
			dataMap.put(DataMapKey.VARIANT, new DataMapValue(variant));
			dataMap.put(DataMapKey.FOOD_STATE, new DataMapValue(state));
		} catch (InvalidDatatypeException e) { e.printStackTrace(); }
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
	}
	
	@Override
	public Thing getChoppedThing() {
		try {
			if(dataMap.get(DataMapKey.FOOD_STATE).getFoodState() == FoodState.RAW) {
				dataMap.get(DataMapKey.FOOD_STATE).update(FoodState.CHOPPED);
			}
		} catch (InvalidDatatypeException e) { e.printStackTrace(); }
		return this;
	}

	public void setVariant(int var) {
		try { dataMap.get(DataMapKey.VARIANT).update(var);
		} catch (InvalidDatatypeException e) { e.printStackTrace(); }
	}
	public void setState(FoodState state) {
		try { dataMap.get(DataMapKey.FOOD_STATE).update(state);
		} catch (InvalidDatatypeException e) { e.printStackTrace(); };
	}
	public int getVariant() {
		try { return dataMap.get(DataMapKey.VARIANT).getInt();
		} catch (InvalidDatatypeException e) { e.printStackTrace(); return -1; }
	}
	public FoodState getState() {
		try { return dataMap.get(DataMapKey.FOOD_STATE).getFoodState();
		} catch (InvalidDatatypeException e) { e.printStackTrace(); return null; }
	}
	@Override
	public Thing duplicate() {
		return new Beef(dataMap);
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
			switch(dataMap.get(DataMapKey.FOOD_STATE).getFoodState()) {
			case CHOPPED: {
				dataMap.get(DataMapKey.FOOD_STATE).update(FoodState.CHOPPED_COOKED);
			} break;
			case CHOPPED_COOKED:
				break;
			case COOKED:
				break;
			case RAW: {
				dataMap.get(DataMapKey.FOOD_STATE).update(FoodState.COOKED);
			} break;
			}
		} catch (InvalidDatatypeException e) { e.printStackTrace(); }
		return this;
	}
	@Override
	public Map<DataMapKey, DataMapValue> getDataMap() {
		return dataMap;
	}
}
