package benjaminc.chef_simulator.things.types;

import benjaminc.chef_simulator.data.location.Location2d;
import benjaminc.chef_simulator.rooms.Room;

public interface Tickable {

	public abstract void tick(Room r, Location2d l, double f);
}
