package dev.orangeben.chef_simulator.things.types;

import dev.orangeben.chef_simulator.things.Thing;

public interface Cookable extends FoodThing {
	public abstract Thing getCookedThing();
}
