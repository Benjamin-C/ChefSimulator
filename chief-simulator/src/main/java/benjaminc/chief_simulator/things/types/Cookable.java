package benjaminc.chief_simulator.things.types;

import benjaminc.chief_simulator.things.Thing;

public interface Cookable extends FoodThing {
	public abstract Thing getCookedThing();
}
