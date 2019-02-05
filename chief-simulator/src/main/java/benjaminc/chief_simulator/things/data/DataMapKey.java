package benjaminc.chief_simulator.things.data;

public enum DataMapKey {
LAST_MOVE_FRAME("Last_Move_Frame"),
VARIANT("Variant"),
FOOD_STATE("Food_State"),
DIRECTION("Direction"),
MAKES("Makes");
	
	String key;
	
	DataMapKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
