package dev.benjaminc.chef_simulator.things.types;

import java.util.List;

import dev.benjaminc.chef_simulator.rooms.Room;
import dev.benjaminc.chef_simulator.things.Thing;

public interface ToolThing extends Thing {

	/**
	 * Uses the Tool
	 * @param t	the {@link Thing} to use which is removed from the {@link Room}
	 * @return	the {@link List} of {@link Thing} to add to the room
	 */
	public abstract List<Thing> useTool(Thing t);
	
}
