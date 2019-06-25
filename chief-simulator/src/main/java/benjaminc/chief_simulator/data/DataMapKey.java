package benjaminc.chief_simulator.data;

import benjaminc.chief_simulator.control.Direction;
import benjaminc.chief_simulator.things.Thing;

public enum DataMapKey {
LAST_MOVE_FRAME("Last_Move_Frame", Double.class),
VARIANT("Variant", Integer.class),
VARIANT_COUNT("Variant_Count", Integer.class),
FOOD_STATE("Food_State", FoodState.class),
DIRECTION("Direction", Direction.class),
MAKES("Makes", Thing.class),
INVENTORY("Item", Inventory.class);
	
	String key;
	Class<?> type;
	
	DataMapKey(String key, Class<?> type) {
		this.key = key;
		this.type = type;
	}
	
	public String getKey() {
		return key;
	}
	
	public Class<?> getType() {
		return type;
	}
}
