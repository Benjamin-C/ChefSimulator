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
UUID(UUID.class, false, true),
/**
 * a {@link Double} of the last frame that the Object was moved
 */
LAST_MOVE_FRAME(Double.class, false, false),
/**
 * @deprecated
 * an {@link Integer} of the graphical variant of the object.
 * Not currently supported, unsure on future support.
 * Not to be used for any purpose beyond graphical differences
 */
VARIANT(Integer.class, true, true),
/**
 * @deprecated
 * an {@link Integer} of the total possible graphical variants of the object.
 * Not currently supported, unsure on future support.
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
 * the {@link Inventory} of the thing
 * Used with {@link ContainerThing}
 */
INVENTORY(Inventory.class, true, true),
/**
 * the {@link Texture} of the thing
 */
TEXTURE(Texture.class, true, false),
/**
 * the {@link Boolean} of if the item has been used.
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
