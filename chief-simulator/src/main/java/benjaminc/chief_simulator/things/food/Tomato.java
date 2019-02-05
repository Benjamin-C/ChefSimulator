package benjaminc.chief_simulator.things.food;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import benjaminc.chief_simulator.graphics.food.GraphicalApple;
import benjaminc.chief_simulator.graphics.food.GraphicalTomato;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.data.DataMapKey;
import benjaminc.chief_simulator.things.data.DataMapValue;
import benjaminc.chief_simulator.things.data.InvalidDatatypeException;
import benjaminc.chief_simulator.things.types.Choppable;
import benjaminc.chief_simulator.things.types.FoodThing;

public class Tomato implements FoodThing, Choppable{

	protected GraphicalTomato graphics;
	protected Map<DataMapKey, DataMapValue> dataMap;
	
	public Tomato() {
		this(-1, FoodState.RAW);
	}
	public Tomato(int variant, FoodState state) {
		super();
		dataMap = new HashMap<DataMapKey, DataMapValue>();
		if(variant == -1) {
			Random r = new Random();
			variant = r.nextInt(GraphicalTomato.VARIANT_COUNT);
		}

		graphics = new GraphicalTomato(dataMap);
	}
	public Tomato(Map<DataMapKey, DataMapValue> dataMap) {
		this.dataMap = dataMap;
		graphics = new GraphicalTomato(dataMap);
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
	
	@Override
	public Thing duplicate() {
		return new Tomato(dataMap);
	}
	
	@Override
	public boolean isSame(Thing t) {
		if(t.getClass() == this.getClass()) {
			return true;
		} else {
			return false;
		}
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
	public Map<DataMapKey, DataMapValue> getDataMap() {
		return dataMap;
	}
}
