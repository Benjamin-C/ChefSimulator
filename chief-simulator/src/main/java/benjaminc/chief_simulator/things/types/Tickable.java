package benjaminc.chief_simulator.things.types;

import benjaminc.chief_simulator.Game;
import benjaminc.chief_simulator.control.Location;
import benjaminc.chief_simulator.graphics.Room;

public interface Tickable {

	public abstract void tick(Room r, Location l, long f, Game g);
}
