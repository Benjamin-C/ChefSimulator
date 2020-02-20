package dev.benjaminc.chef_simulator.data;

import java.util.HashMap;
import java.util.Map;

import dev.benjaminc.chef_simulator.chef_graphics.GraphicalLoader;
import dev.benjaminc.chef_simulator.control.Direction;
import dev.benjaminc.chef_simulator.things.BasicThing;
import dev.benjaminc.chef_simulator.things.Thing;
import dev.benjaminc.chef_simulator.things.types.ContainerThing;
import dev.benjaminc.chef_utils.graphics.Texture;
import dev.benjaminc.util.JSONTools;

public class DataMap implements Savable {
	
	/** the {@link Map} of {@link DataMapKey} to {@link Object} to store data */
	protected Map<DataMapKey, Object> dataMap;
	
	/**
	 * Creates a new {@link DataMap}
	 */
	public DataMap() {
		dataMap = new HashMap<DataMapKey, Object>();
	}
	
	public DataMap(String json) {
		dataMap = new HashMap<DataMapKey, Object>();
		json = JSONTools.peelChar(json, '{');
		Map<String, String> js = JSONTools.splitJSON(json);
		
		for(String s : js.keySet()) {
			DataMapKey dmk = DataMapKey.valueOf(s.toUpperCase());
			switch(dmk) {
			case DIRECTION: dataMap.put(dmk, Direction.valueOf(js.get(s.toUpperCase()))); break;
			case FOOD_STATE: dataMap.put(dmk, FoodState.valueOf(js.get(s.toUpperCase()))); break;
			case INVENTORY: dataMap.put(dmk, new Inventory(js.get(s))); break;
			case MAKES: dataMap.put(dmk, BasicThing.makeThingFromJSON(js.get(s))); break;
			case TEXTURE: dataMap.put(DataMapKey.TEXTURE, GraphicalLoader.load(js.get(s)));
			default: break;
			}
		}
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
	
	@Override
	public String asJSON() {
		String s = "{";
		boolean fencepost = false;
		for(DataMapKey k : dataMap.keySet()) {
			if(k.toSave()) {
				if(fencepost) {
					s = s + ", ";
				} else {
					fencepost = true;
				}
				s = s + "\"" + k + "\":";
				Object d = dataMap.get(k);
				if(d instanceof Savable) {
					s = s + ((Savable) d).asJSON();
				} else {
					s = s + "\"" + dataMap.get(k) + "\"";
				}
			}
		}
		s = s + "}";
		return s;
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

	public enum DataMapKey {
		/**
		 * a {@link Double} of the last frame that the Object was moved
		 */
		LAST_MOVE_FRAME(Double.class, false, false),
		/**
		 * @deprecated
		 * an {@link Integer} of the graphical variant of the object.<br/>
		 * Not currently supported, unsure on future support.<br/>
		 * Not to be used for any purpose beyond graphical differences
		 */
		VARIANT(Integer.class, true, true),
		/**
		 * @deprecated
		 * an {@link Integer} of the total possible graphical variants of the object.<br/>
		 * Not currently supported, unsure on future support.<br/>
		 * Not to be used for any purpose beyond graphical differences
		 */
		VARIANT_COUNT(Integer.class, false, false),
		/**
		 * the {@link FoodState} that the food is currently in
		 */
		FOOD_STATE(FoodState.class, true, true),
		/**
		 * the {@link Direction} the thing is facing
		 */
		DIRECTION(Direction.class, true, true),
		/**
		 * the {@link Thing} that this thing makes
		 * Intended for spawners
		 */
		MAKES(Thing.class, true, true),
		/**
		 * the {@link Inventory} of the thing<br/>
		 * Used with {@link ContainerThing}
		 */
		INVENTORY(Inventory.class, true, true),
		/**
		 * the {@link Texture} of the thing
		 */
		TEXTURE(Texture.class, true, false),
		/**
		 * the {@link Boolean} of if the item has been used.<br/>
		 * May not be accurate
		 */
		USED(Boolean.class, true, false);
			
			Class<?> type;
			boolean useredit;
			boolean toSave;

			DataMapKey(Class<?> type, boolean useredit, boolean toSave) {
				this.type = type;
				this.useredit = useredit;
				this.toSave = toSave;
			}
			
			/**
			 * Gets the allowable class that the value of the key can contain
			 * @return the {@link Class}<?>
			 */
			public Class<?> getType() {
				return type;
			}
			
			/**
			 * Gets wether the user is allowed to edit the value
			 */
			public boolean mayUserEdit() {
				return useredit;
			}
			
			/**
			 * Gets wether or not to save the value
			 * @return the boolean of toSave
			 */
			public boolean toSave() {
				return toSave;
			}
		}
}
