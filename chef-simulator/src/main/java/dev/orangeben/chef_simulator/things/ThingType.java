package dev.orangeben.chef_simulator.things;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import dev.orangeben.chef_simulator.data.DataMap;
import dev.orangeben.chef_simulator.things.building.Belt;
import dev.orangeben.chef_simulator.things.building.Counter;
import dev.orangeben.chef_simulator.things.building.CuttingBoard;
import dev.orangeben.chef_simulator.things.building.DishCounter;
import dev.orangeben.chef_simulator.things.building.DishReturn;
import dev.orangeben.chef_simulator.things.building.Dishwasher;
import dev.orangeben.chef_simulator.things.building.Disposal;
import dev.orangeben.chef_simulator.things.building.Floor;
import dev.orangeben.chef_simulator.things.building.Fryer;
import dev.orangeben.chef_simulator.things.building.Remover;
import dev.orangeben.chef_simulator.things.building.Spawner;
import dev.orangeben.chef_simulator.things.building.Stove;
import dev.orangeben.chef_simulator.things.building.Window;
import dev.orangeben.chef_simulator.things.food.Apple;
import dev.orangeben.chef_simulator.things.food.Beef;
import dev.orangeben.chef_simulator.things.food.Bun;
import dev.orangeben.chef_simulator.things.food.Cheese;
import dev.orangeben.chef_simulator.things.food.Lettuce;
import dev.orangeben.chef_simulator.things.food.Potato;
import dev.orangeben.chef_simulator.things.food.Tomato;
import dev.orangeben.chef_simulator.things.tools.Dishbin;
import dev.orangeben.chef_simulator.things.tools.Pan;
import dev.orangeben.chef_simulator.things.tools.Plate;
import dev.orangeben.chef_textures.dialog.MessageDialog;

/**
 * @author Benjamin-C
 *
 */
public enum ThingType {
	
	/** {@link NullThing} */
	NULL(NullThing.class),
	/** {@link Belt} */
	BELT(Belt.class),
	/** {@link Counter} */
	COUNTER(Counter.class),
	/** {@link CuttingBoard} */
	CUTTING_BOARD(CuttingBoard.class),
	/** {@link DishCounter} */
	DISH_COUNTER(DishCounter.class),
	/** {@link DishReturn} */
	DISH_RETURN(DishReturn.class),
	/** {@link Dishwasher} */
	DISHWASHER(Dishwasher.class),
	/** {@link Disposal} */
	DISPOSAL(Disposal.class),
	/** {@link Floor} */
	FLOOR(Floor.class),
	/** {@link Fryer} */
	FRYER(Fryer.class),
	/** {@link Remover} */
	REMOVER(Remover.class),
	/** {@link Spawner} */
	SPAWNER(Spawner.class),
	/** {@link Stove} */
	STOVE(Stove.class),
	/** {@link Window} */
	WINDOW(Window.class),
	
	/** {@link Apple} */
	APPLE(Apple.class),
	/** {@link Beef} */
	BEEF(Beef.class),
	/** {@link Bun} */
	BUN(Bun.class),
	/** {@link Cheese} */
	CHEESE(Cheese.class),
	/** {@link Lettuce} */
	LETTUCE(Lettuce.class),
	/** {@link Potato} */
	POTATO(Potato.class),
	/** {@link Tomato} */
	TOMATO(Tomato.class),
	
	/** {@link Dishbin} */
	DISHBIN(Dishbin.class),
	/** {@link Pan} */
	PAN(Pan.class),
	/** {@link Plate} */
	PLATE(Plate.class);

	Class<?> myclass;
	
	ThingType(Class<?> myclass) {
		this.myclass = myclass;
	}
	
	/**
	 * Gets the {@link Class} that the value represents
	 * @return the {@link Class}
	 */
	public Class<?> getMyClass() {
		return myclass;
	}
	
	/**
	 * Gets a {@link Thing} from a {@link ThingType}
	 * @param t		the {@link ThingType} to make
	 * @param d 	the {@link DataMap} pass to the {@link Thing} constructor
	 * @return 		the new {@link Thing}. Null if something bad happened
	 */
	public static Thing getThingOfType(ThingType t, DataMap d) {
		return getThingOfType(t, d, UUID.randomUUID());
	}
	/**
	 * Gets a {@link Thing} from a {@link ThingType}
	 * @param t		the {@link ThingType} to make
	 * @param d		the {@link DataMap} pass to the {@link Thing} constructor
	 * @param uuid	the {@link UUID} of the new {@link Thing} 
	 * @return the	new {@link Thing}. Null if something bad happened
	 */
	public static Thing getThingOfType(ThingType t, DataMap d, UUID uuid) {
		Object newobj = null;

		try {
			Class<?> clazz = t.getMyClass();
			Constructor<?> ctor = clazz.getConstructor(DataMap.class, UUID.class);
			newobj = ctor.newInstance(d, uuid);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException err) {
			err.printStackTrace();
			new MessageDialog("Constructor fail", "The reflective constructor lost its shine " + err.getMessage());
		}
		
		if(newobj != null && newobj instanceof Thing) {
			return (Thing) newobj;
		} else {
			return null;
		}
	}
	
	/**
	 * Gets the {@link ThingType} of a {@link Thing}
	 * @param t the {@link Thing} to look at
	 * @return 	the {@link ThingType}
	 */
	public static ThingType getTypeOfThing(Thing t) {
		for(ThingType tt : ThingType.values()) {
			if(t.getSubclass().equals(tt.getMyClass())) {
				return tt;
			}
		}
		return null;
	}
}
