package benjaminc.chef_simulator.events;

import java.util.Map;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.control.Location3d;
import benjaminc.chef_simulator.data.Savable;
import benjaminc.chef_simulator.graphics.GameSpace;
import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.util.JSONTools;

/**
 * {@link Event} fired when a {@link GameSpace} changes
 * 
 * @author Benjamin Crall
 *
 */
public class GSChangeEvent extends Event implements Savable {
	
	public enum GSChangeEventTypes {
		ADD, REMOVE, UPDATE;
	}
	public enum GSChangeEventJsonKeys {
		TYPE, THING, LOCATION;
	}

	private GSChangeEventTypes type;
	private Thing thing;
	private Location3d location;
	
	/**
	 * @param type	the {@link GSChangeEventTypes} of the change
	 * @param t		the {@link Thing} affected
	 * @param loc	the {@link Location3d} of the thing
	 */
	public GSChangeEvent(GSChangeEventTypes type, Thing t, Location3d loc) {
		this.type = type;
		thing = t;
		this.location = loc;
	}
	
	public GSChangeEvent(String json) {
		System.out.println(json);
		if(json.charAt(0) == '{' && json.charAt(json.length()-1) == '}') {

			json = json.substring(1, json.length() - 1);
			Map<String, String> js = JSONTools.splitJSON(json);
			for(String s : js.keySet()) {
				try {
					GSChangeEventJsonKeys tdk = GSChangeEventJsonKeys.valueOf(s);
					switch(tdk) {
					case TYPE:
						type = GSChangeEventTypes.valueOf(js.get(s));
						break;
					case THING:
						thing = BasicThing.makeThingFromJSON(js.get(s));
						break;
					case LOCATION: 
						location = new Location3d(js.get(s));
						break;
					default:
						break;
					}
				} catch(IllegalArgumentException e) {}
			}
			if(type != null && thing != null && location != null) {
				return;
			}
		}
		throw new IllegalArgumentException("The GSChangeEvent could not be created from the provided JSON");
	}
	
	/**
	 * Gets the type of {@link GSChangeEvent}
	 * @return the {@link GSChangeEventTypes} type
	 */
	public GSChangeEventTypes getType() {
		return type;
	}
	/**
	 * Gets the {@link Thing} that was changed
	 * @return the {@link Thing}
	 */
	public Thing getThing() {
		return thing;
	}
	/**
	 * Gets the {@link Location} the change happened
	 * @return the {@link Location}
	 */
	public Location3d getLocation() {
		return location;
	}

	public void run() {
		switch(type) {
		case ADD: Game.getRoom().getSpace(location).addThing(thing); System.out.println("Adding Thing");
			break;
		case REMOVE: Game.getRoom().getSpace(location).removeThing(thing);
			break;
		case UPDATE: System.out.println("I can't do that yet!");
			break;
		default:
			break;
		
		}
	}
	
	@Override
	public String asJSON() {
		String out = "{";
		out += "\"" + GSChangeEventJsonKeys.TYPE + "\":\"" + type + "\",";
		out += "\"" + GSChangeEventJsonKeys.THING + "\":\"" + thing.asJSON() + "\",";
		out += "\"" + GSChangeEventJsonKeys.LOCATION + "\":\"" + location.asJSON() + "\"";
		return super.asJSON(EventTypes.GS_CHANGE_EVENT, out + "}");
	}
	
	@Override
	public String toString() {
		return "GSChangeEvent[" + type + " " + thing + " at " + location.toSimpleString() + "]";
	}
}
