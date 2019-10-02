package benjaminc.chef_simulator.data;

import benjaminc.chef_simulator.control.Direction;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.ContainerThing;
import benjaminc.chef_utils.data.FoodState;
import benjaminc.chef_utils.graphics.Texture;

import java.util.UUID;

public enum DataMapKey {
/**
 * a {@link UUID} unique to this DataMap
 */
UUID("UUID", UUID.class),
/**
 * a {@link Double} of the last frame that the Object was moved
 */
LAST_MOVE_FRAME("Last_Move_Frame", Double.class),
/**
 * @deprecated
 * an {@link Integer} of the graphical variant of the object.
 * Not currently supported, unsure on future support.
 * Not to be used for any purpose beyond graphical differences
 */
VARIANT("Variant", Integer.class),
/**
 * @deprecated
 * an {@link Integer} of the total possible graphical variants of the object.
 * Not currently supported, unsure on future support.
 * Not to be used for any purpose beyond graphical differences
 */
VARIANT_COUNT("Variant_Count", Integer.class),
/**
 * the {@link FoodState} that the food is currently in
 */
FOOD_STATE("Food_State", FoodState.class),
/**
 * the {@link Direction} the thing is facing
 */
DIRECTION("Direction", Direction.class),
/**
 * the {@link Thing} that this thing makes
 * Intended for spawners
 */
MAKES("Makes", Thing.class),
/**
 * the {@link Inventory} of the thing
 * Used with {@link ContainerThing}
 */
INVENTORY("Item", Inventory.class),
/**
 * the {@link Texture} of the thing
 */
TEXTURE("Graphics", Texture.class);
	
	String key;
	Class<?> type;

	DataMapKey(String key, Class<?> type) {
		this.key = key;
		this.type = type;
	}
	
	/**
	 * Gets the human readable name of the key
	 * @return the {@link String} name
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Gets the allowable class that the value of the key can contain
	 * @return the {@link Class}<?>
	 */
	public Class<?> getType() {
		return type;
	}
}
