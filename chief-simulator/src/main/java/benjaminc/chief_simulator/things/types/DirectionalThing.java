package benjaminc.chief_simulator.things.types;

import benjaminc.chief_simulator.control.Direction;

public interface DirectionalThing {
	public abstract void setDirection(Direction d);
	public abstract Direction getDirection();
}
