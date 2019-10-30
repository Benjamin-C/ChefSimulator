package benjaminc.chef_simulator.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import benjaminc.chef_simulator.things.BasicThing;

public class DataMap {
	
	/** the {@link Map} of {@link DataMapKey} to {@link Object} to store data */
	protected Map<DataMapKey, Object> dataMap;
	
	/**
	 * Creates a new {@link DataMap}
	 */
	public DataMap() {
		dataMap = new HashMap<DataMapKey, Object>();
		put(DataMapKey.UUID, UUID.randomUUID());
	}
	/**
	 * Gets the {@link Object} stored at the {@link DataMapKey}.
	 * Its type will always match that specified by {@link DataMapKey#type} or null
	 * @param key the {@link DataMapKey} to get
	 * @return the {@link Object} there
	 */
	public Object get(DataMapKey key) {
		Object o = dataMap.get(key);
		if (checkForType(o, key.getType())) {
			return o;
		} else {
			return null;
		}
	}
	/**
	 * Checks if the {@link DataMap} has the specified {@link DataMapKey}
	 * @param test the {@link DataMapKey} to check
	 * @return the boolean if it exits
	 */
	public boolean containsKey(DataMapKey test) {
		return dataMap.containsKey(test);
	}
	/**
	 * Puts an {@link Object} into the {@link DataMap} at the {@link DataMapKey}
	 * @param key the {@link DataMapKey} location
	 * @param value the {@link Object} to store
	 */
	public void put(DataMapKey key, Object value) {
		if(checkForType(value, key.getType())) {
			dataMap.put(key, value);
		} else {
			throw new InvalidDatatypeException("DataMapValue must be of type " + key.getType() + ", not " + value.getClass().getSimpleName());
		}
	}
	@Override
	public DataMap clone() {
		DataMap newdm = new DataMap();
		for(DataMapKey k : dataMap.keySet()) {
			switch(k) {
			case INVENTORY: newdm.put(k, ((Inventory) dataMap.get(k)).clone());
				break;
			case MAKES: newdm.put(k, ((BasicThing) dataMap.get(k)).clone());
				break;
			case FOOD_STATE: newdm.put(k, ((FoodState) dataMap.get(k)).clone((FoodState) dataMap.get(k)));
			default: newdm.put(k, dataMap.get(k)); break;
			
			}
		}
		System.out.println("Cloned data map " + this + " to " + newdm);
		
		return newdm;
	}
	
	/**
	 * Check if this {@link DataMap} equals another {@link DataMap}
	 * Returns false if one {@link DataMap} has a {@link DataMapKey} the other does not
	 * @param dm the {@link DataMap} to check
	 * @return the boolean if they match
	 */
	public boolean equals(DataMap dm) {
		return equals(dm, false);
	}
		
	/**
	 * Check if this {@link DataMap} equals another {@link DataMap}
	 * @param dm the {@link DataMap} to check
	 * @param onlyChekcSame a boolean to only check {@link DataMapKey} that both {@link DataMap} have. Defaults to false
	 * @return the boolean if they match
	 */
	public boolean equals(DataMap dm, boolean onlyCheckSame) {
		System.out.println("Checking DataMap for sameness");
		if(dataMap.keySet().size() != dm.dataMap.keySet().size()) {
			return false;
		}
		for(DataMapKey k : dataMap.keySet()) {
			if(!dm.containsKey(k) && onlyCheckSame) {
				return false;
			}
			if(k != DataMapKey.UUID && !dataMap.get(k).equals(dm.get(k))) {
				System.out.println(k + " did not match (they=" + dm.get(k) + " me=" + dataMap.get(k) + ")");
				return false;
			}
		}
		return true;
	}
	/**
	 * Checks of an object is of the correct type
	 * @param candidate the {@link Object} to test
	 * @param type the {@link Class} to test
	 * @return a boolean if they are work
	 */
	private boolean checkForType(Object candidate, Class<?> type){
	    return type.isInstance(candidate);
	}
	
	/**
	 * Saves the {@link DataMap} to a {@link String}
	 * @return the {@link String} representation of the {@link DataMap}
	 */
	public String save() {
		return "DataMap#save is unfinished, and does not work";
	}
	
	@Override
	public String toString() {
		String s = "DataMap[";
		boolean fencepost = false;
		for(DataMapKey k : dataMap.keySet()) {
			if(fencepost) {
				s = s + ", ";
			} else {
				fencepost = true;
			}
			s = s + k + "=\"" + dataMap.get(k) + "\"";
		}
		s = s + "]";
		return s;
	}

}
