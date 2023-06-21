package dev.orangeben.chef_simulator.things.types;

import dev.orangeben.chef_simulator.data.location.Location2d;
import dev.orangeben.chef_simulator.rooms.Room;

public interface Tickable {

	public abstract void tick(Room r, Location2d l, double f);
}
