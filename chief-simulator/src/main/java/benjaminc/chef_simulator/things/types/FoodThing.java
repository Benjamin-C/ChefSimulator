package benjaminc.chef_simulator.things.types;

import benjaminc.chef_simulator.data.FoodState;
import benjaminc.chef_simulator.things.Thing;

public interface FoodThing extends Thing {
	public void setState(FoodState fs);
	public FoodState getState();
}
