package dev.orangeben.chef_simulator.things.types;

import dev.orangeben.chef_simulator.control.Direction;

public interface DirectionalThing {
	public abstract void setDirection(Direction d);
	public abstract Direction getDirection();
}
