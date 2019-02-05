package benjaminc.chief_simulator.things.food;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import benjaminc.chief_simulator.graphics.food.GraphicalApple;
import benjaminc.chief_simulator.graphics.food.GraphicalPotato;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.data.DataMapKey;
import benjaminc.chief_simulator.things.data.DataMapValue;
import benjaminc.chief_simulator.things.data.InvalidDatatypeException;
import benjaminc.chief_simulator.things.types.Choppable;
import benjaminc.chief_simulator.things.types.Cookable;
import benjaminc.chief_simulator.things.types.FoodThing;
import benjaminc.chief_simulator.things.types.Fryable;

public class Potato implements FoodThing, Cookable, Choppable, Fryable {

	protected GraphicalPotato graphics;
	protected Map<DataMapKey, DataMapValue> dataMap;
	
	public Potato() {
		this(-1, FoodState.RAW);
	}
	public Potato(int variant, FoodState state) {
		super();
		dataMap = new HashMap<DataMapKey, DataMapValue>();
		if(variant == -1) {
			Random r = new Random();
			variant = r.nextInt(GraphicalPotato.VARIANT_COUNT);
		}

		graphics = new GraphicalPotato(dataMap);
	
		dataMap = new HashMap<DataMapKey, DataMapValue>();
		try {
			dataMap.put(DataMapKey.VARIANT, new DataMapValue(variant));
			dataMap.put(DataMapKey.FOOD_STATE, new DataMapValue(state));
		} catch (InvalidDatatypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("You goofed!");
		}
	}
	public Potato(Map<DataMapKey, DataMapValue> dataMap) {
		this.dataMap = dataMap;
		graphics = new GraphicalPotato(dataMap);
	}
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
	}
	
	@Override
	public Thing getChoppedThing() {
		try { dataMap.get(DataMapKey.FOOD_STATE).update(FoodState.CHOPPED);
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
		return new Apple(dataMap);
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
			case CHOPPED:
				dataMap.get(DataMapKey.FOOD_STATE).update(FoodState.CHOPPED_COOKED);
				break;
			case CHOPPED_COOKED:
				break;
			case COOKED:
				break;
			case RAW: 
				dataMap.get(DataMapKey.FOOD_STATE).update(FoodState.COOKED);
				break;
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
			switch(dataMap.get(DataMapKey.FOOD_STATE).getFoodState()) {
			case CHOPPED: dataMap.get(DataMapKey.FOOD_STATE).update(FoodState.CHOPPED_COOKED);
				break;
			case CHOPPED_COOKED:
				break;
			case COOKED:
				break;
			case RAW:
				break;
			}
		} catch (InvalidDatatypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
	@Override
	public Map<DataMapKey, DataMapValue> getDataMap() {
		return dataMap;
	}
}
