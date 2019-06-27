package benjaminc.chief_simulator.things;

import java.util.Random;

import benjaminc.chief_simulator.control.Direction;
import benjaminc.chief_simulator.data.DataMap;
import benjaminc.chief_simulator.data.DataMapKey;
import benjaminc.chief_simulator.data.FoodState;
import benjaminc.chief_simulator.graphics.GraphicalLoader;
import benjaminc.chief_simulator.graphics.GraphicalThing;

public class BasicThing {

	protected GraphicalThing graphics;
	protected DataMap dataMap;
	protected Class<?> subclass;
	
	public BasicThing(DataMap dataMap, Class<?> subclass) {
		this.subclass = subclass;
		this.dataMap = dataMap;
		if(this.dataMap == null) {
			this.dataMap = new DataMap();
		}
		if(!this.dataMap.containsKey(DataMapKey.VARIANT_COUNT)) {
			this.dataMap.put(DataMapKey.VARIANT_COUNT, 1);
		}
		if(!this.dataMap.containsKey(DataMapKey.VARIANT)) {
			Random r = new Random();
			this.dataMap.put(DataMapKey.VARIANT_COUNT, r.nextInt((int) this.dataMap.get(DataMapKey.VARIANT_COUNT)));
		}
		if(!this.dataMap.containsKey(DataMapKey.DIRECTION)) {
			this.dataMap.put(DataMapKey.DIRECTION, Direction.UP);
		}
		
		
		graphics = GraphicalLoader.load(this.subclass.getSimpleName(), this.dataMap);
	}
	public BasicThing(DataMap dataMap, int variantCount, Class<?> subclass) {
		this(dataMap, subclass);
		this.dataMap.put(DataMapKey.VARIANT_COUNT, variantCount);
		System.out.println("Making graphics for " + subclass.getSimpleName() + " #" + this.dataMap.get(DataMapKey.VARIANT_COUNT) + " and it is " + graphics);
	}
	public BasicThing(int variant, FoodState state, int variantCount, Class<?> subclass) {
		this(null, variantCount, subclass);
		dataMap.put(DataMapKey.VARIANT, variant);
		dataMap.put(DataMapKey.FOOD_STATE, state);
	}
	
	public boolean equals(Thing t) {
		if(dataMap.equals(t.getDataMap())) {
			return true;
		} else {
			return false;
		}
	}
}
