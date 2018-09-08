package benjaminc.chief_simulator.graphics;

import benjaminc.chief_simulator.control.Direction;

public interface DirectionalGraphicalThing extends GraphicalThing{

	public abstract void setDirection(Direction d);
	public abstract Direction getDirection();
}
