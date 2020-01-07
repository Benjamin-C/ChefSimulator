package benjaminc.chef_simulator.events;

import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.rooms.Room;
import benjaminc.chef_simulator.things.Thing;

/**
 * An event for when a {@link Thing} is added to a {@link Room}
 * 
 * @author Benjamin Crall
 *
 */
public class AddThingEvent extends Event {

	private Thing thing;
	private Location loc;
	
	/**
	 * Creates an {@link AddThingEvent}
	 * 
	 * @param t the {@link Thing} being added
	 * @param l the {@link Location} it is being added
	 */
	public AddThingEvent(Thing t, Location l) {
		thing = t;
		loc = l;
	}
	/**
	 * Gets the {@link Thing} being added
	 * 
	 * @return thing;
	 */
	public Thing getThing() {
		return thing;
	}
	/**
	 * Gets where the thing is being added
	 * 
	 * @return the {@link Location}
	 */
	public Location getLocation() {
		return loc;
	}
	/**
	 * Sets the thing that is being added
	 * 
	 * @param toAdd the new {@link Thing}
	 */
	public void setThing(Thing toAdd) {
		thing = toAdd;
	}
}
