package dev.benjaminc.chef_simulator.things.types;

import dev.benjaminc.chef_simulator.control.Direction;

public interface DirectionalThing {
	public abstract void setDirection(Direction d);
	public abstract Direction getDirection();
}
