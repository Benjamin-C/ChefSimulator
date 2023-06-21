package dev.orangeben.chef_simulator.things.types;

import dev.orangeben.chef_simulator.data.FoodState;
import dev.orangeben.chef_simulator.things.Thing;

public interface FoodThing extends Thing {
	public void setState(FoodState fs);
	public FoodState getState();
}
