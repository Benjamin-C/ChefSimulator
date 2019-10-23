package benjaminc.chef_simulator.things;

import benjaminc.chef_simulator.things.building.Belt;
import benjaminc.chef_simulator.things.building.Counter;
import benjaminc.chef_simulator.things.building.CuttingBoard;
import benjaminc.chef_simulator.things.building.DishCounter;
import benjaminc.chef_simulator.things.building.DishReturn;
import benjaminc.chef_simulator.things.building.Dishwasher;
import benjaminc.chef_simulator.things.building.Disposal;
import benjaminc.chef_simulator.things.building.Floor;
import benjaminc.chef_simulator.things.building.Fryer;
import benjaminc.chef_simulator.things.building.Remover;
import benjaminc.chef_simulator.things.building.Spawner;
import benjaminc.chef_simulator.things.building.Stove;
import benjaminc.chef_simulator.things.building.Window;
import benjaminc.chef_simulator.things.food.Apple;
import benjaminc.chef_simulator.things.food.Beef;
import benjaminc.chef_simulator.things.food.Bun;
import benjaminc.chef_simulator.things.food.Cheese;
import benjaminc.chef_simulator.things.food.Lettuce;
import benjaminc.chef_simulator.things.food.Potato;
import benjaminc.chef_simulator.things.food.Tomato;
import benjaminc.chef_simulator.things.tools.Dishbin;
import benjaminc.chef_simulator.things.tools.Pan;
import benjaminc.chef_simulator.things.tools.Plate;

/**
 * @author Benjamin-C
 *
 */
public enum ThingType {
	
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
}
