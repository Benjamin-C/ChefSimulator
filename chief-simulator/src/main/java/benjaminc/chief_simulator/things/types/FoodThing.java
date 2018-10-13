package benjaminc.chief_simulator.things.types;

import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.food.FoodState;

public interface FoodThing extends Thing {
	public void setState(FoodState fs);
	public FoodState getState();
}
