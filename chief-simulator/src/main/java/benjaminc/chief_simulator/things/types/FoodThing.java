package benjaminc.chief_simulator.things.types;

import benjaminc.chief_simulator.data.FoodState;
import benjaminc.chief_simulator.things.Thing;

public interface FoodThing extends Thing {
	public void setState(FoodState fs);
	public FoodState getState();
}
