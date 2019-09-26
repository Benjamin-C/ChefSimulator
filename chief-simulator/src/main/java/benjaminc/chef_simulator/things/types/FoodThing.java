package benjaminc.chef_simulator.things.types;

import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_utils.data.FoodState;

public interface FoodThing extends Thing {
	public void setState(FoodState fs);
	public FoodState getState();
}
