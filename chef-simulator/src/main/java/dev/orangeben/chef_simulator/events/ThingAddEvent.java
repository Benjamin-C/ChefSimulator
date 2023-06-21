package dev.orangeben.chef_simulator.events;

import java.util.Map;

import dev.orangeben.chef_simulator.Game;
import dev.orangeben.chef_simulator.data.location.Location2d;
import dev.orangeben.chef_simulator.data.location.Location3d;
import dev.orangeben.chef_simulator.rooms.Room;
import dev.orangeben.chef_simulator.things.BasicThing;
import dev.orangeben.chef_simulator.things.Thing;
import dev.orangeben.util.JSONTools;

/**
 * An event for when a {@link Thing} is added to a {@link Room}
 * 
 * @author Benjamin Crall
 *
 */
public class ThingAddEvent extends Event {

	private enum ThingAddEventJsonKeys {
		THING, LOCATION;
	}
	
	private Thing thing;
	private Location2d loc;
	
	/**
	 * Creates an {@link ThingAddEvent}
	 * 
	 * @param t the {@link Thing} being added
	 * @param l the {@link Location2d} it is being added
	 */
	public ThingAddEvent(Thing t, Location2d l) {
		thing = t;
		loc = l;
	}
	
	public ThingAddEvent(String json) {
		System.out.println(json);
		if(json.charAt(0) == '{' && json.charAt(json.length()-1) == '}') {

			json = json.substring(1, json.length() - 1);
			Map<String, String> js = JSONTools.splitJSON(json);
			for(String s : js.keySet()) {
				try {
					ThingAddEventJsonKeys tdk = ThingAddEventJsonKeys.valueOf(s);
					switch(tdk) {
					case THING: thing = BasicThing.makeThingFromJSON(js.get(s)); break;
					case LOCATION: loc = new Location3d(js.get(s)); break;
					default: break;
					}
				} catch(IllegalArgumentException e) {}
			}
			if(thing != null && loc != null) {
				return;
			}
		}
		throw new IllegalArgumentException("The ThingChangeEvent could not be created from the provided JSON");
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
	 * @return the {@link Location2d}
	 */
	public Location2d getLocation() {
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
	
	@Override
	public void run() {
		Location3d l3d = Game.getRoom().getSpace(loc).addThing(thing);
        if(Game.usingOpenGL) {
		    Game.openglEngine.newThing(thing, l3d);
        }
	}
	
	@Override
	public String asJSON() {
		String out = "{";
		out += "\"" + ThingAddEventJsonKeys.THING + "\":\"" + thing.asJSON() + "\",";
		out += "\"" + ThingAddEventJsonKeys.LOCATION + "\":\"" + loc.asJSON() + "\"";
		return super.asJSON(EventTypes.THING_ADD_EVENT, out + "}");
	}
	
	@Override
	public String toString() {
		return "ThingAddEvent[" + thing + " at " + loc.toSimpleString() + "]";
	}
}
