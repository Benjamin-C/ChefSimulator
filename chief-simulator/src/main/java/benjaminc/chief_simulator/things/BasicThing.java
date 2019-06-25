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
		if(dataMap == null) {
			dataMap = new DataMap();
		}
		if(!dataMap.containsKey(DataMapKey.VARIANT_COUNT)) {
			dataMap.put(DataMapKey.VARIANT_COUNT, 1);
		}
		if(!dataMap.containsKey(DataMapKey.VARIANT)) {
			Random r = new Random();
			dataMap.put(DataMapKey.VARIANT_COUNT, r.nextInt((int) dataMap.get(DataMapKey.VARIANT_COUNT)));
		}
		if(!dataMap.containsKey(DataMapKey.DIRECTION)) {
			dataMap.put(DataMapKey.DIRECTION, Direction.UP);
		}
		this.dataMap = dataMap;
		
		graphics = GraphicalLoader.load(this.subclass.getSimpleName(), this.dataMap);
	}
	public BasicThing(DataMap dataMap, int variantCount, Class<?> subclass) {
		this(dataMap, subclass);
		this.dataMap.put(DataMapKey.VARIANT_COUNT, variantCount);
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
