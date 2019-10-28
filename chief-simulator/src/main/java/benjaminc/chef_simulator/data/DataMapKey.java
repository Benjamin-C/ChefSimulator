package benjaminc.chef_simulator.data;

import benjaminc.chef_simulator.control.Direction;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.ContainerThing;
import benjaminc.chef_utils.graphics.Texture;

import java.util.UUID;

public enum DataMapKey {
/**
 * a {@link UUID} unique to this DataMap
 */
UUID("UUID", UUID.class, false),
/**
 * a {@link Double} of the last frame that the Object was moved
 */
LAST_MOVE_FRAME("Last_Move_Frame", Double.class, false),
/**
 * @deprecated
 * an {@link Integer} of the graphical variant of the object.
 * Not currently supported, unsure on future support.
 * Not to be used for any purpose beyond graphical differences
 */
VARIANT("Variant", Integer.class, true),
/**
 * @deprecated
 * an {@link Integer} of the total possible graphical variants of the object.
 * Not currently supported, unsure on future support.
 * Not to be used for any purpose beyond graphical differences
 */
VARIANT_COUNT("Variant_Count", Integer.class, false),
/**
 * the {@link FoodState} that the food is currently in
 */
FOOD_STATE("Food_State", FoodState.class, true),
/**
 * the {@link Direction} the thing is facing
 */
DIRECTION("Direction", Direction.class, true),
/**
 * the {@link Thing} that this thing makes
 * Intended for spawners
 */
MAKES("Makes", Thing.class, true),
/**
 * the {@link Inventory} of the thing
 * Used with {@link ContainerThing}
 */
INVENTORY("Item", Inventory.class, true),
/**
 * the {@link Texture} of the thing
 */
TEXTURE("Graphics", Texture.class, true),
/**
 * the {@link Boolean} of if the item has been used.
 * May not be accurate
 */
USED("Used", Boolean.class, true);
	
	String key;
	Class<?> type;
	boolean useredit;

	DataMapKey(String key, Class<?> type, Boolean useredit) {
		this.key = key;
		this.type = type;
		this.useredit = useredit;
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
	
	/**
	 * Gets wether the user is allowed to edit the value
	 */
	public boolean mayUserEdit() {
		return useredit;
	}
}
