package benjaminc.chef_simulator.things.types;

import benjaminc.chef_simulator.things.Thing;

public interface Cookable extends FoodThing {
	public abstract Thing getCookedThing();
}
