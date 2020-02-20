package dev.benjaminc.chef_simulator.things.types;

import dev.benjaminc.chef_simulator.things.Thing;

public interface Cookable extends FoodThing {
	public abstract Thing getCookedThing();
}
