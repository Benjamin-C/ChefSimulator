package benjaminc.chef_simulator.events;

import java.util.Map;
import java.util.UUID;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.control.Location3d;
import benjaminc.chef_simulator.graphics.GameSpace;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.util.JSONTools;

/**
 * {@link Event} fired when a {@link GameSpace} changes
 * 
 * @author Benjamin Crall
 *
 */
public class ThingRemoveEvent extends Event {
	
	private enum ThingRemoveEventJsonKeys {
		UUID, LOCATION;
	}

	private UUID uuid;
	private Location3d location;
	
	/**
	 * @param type	the {@link GSChangeEventTypes} of the change
	 * @param t		the {@link Thing} affected
	 * @param loc	the {@link Location3d} of the thing
	 */
	public ThingRemoveEvent(UUID uuid, Location3d loc) {
		this.uuid = uuid;
		this.location = loc;
	}
	
	public ThingRemoveEvent(String json) {
		System.out.println(json);
		if(json.charAt(0) == '{' && json.charAt(json.length()-1) == '}') {

			json = json.substring(1, json.length() - 1);
			Map<String, String> js = JSONTools.splitJSON(json);
			for(String s : js.keySet()) {
				try {
					ThingRemoveEventJsonKeys tdk = ThingRemoveEventJsonKeys.valueOf(s);
					switch(tdk) {
					case UUID: uuid = UUID.fromString(js.get(s)); break;
					case LOCATION: location = new Location3d(js.get(s)); break;
					default: break;
					}
				} catch(IllegalArgumentException e) {}
			}
			if(uuid != null && location != null) {
				return;
			}
		}
		throw new IllegalArgumentException("The GSChangeEvent could not be created from the provided JSON");
	}
	
	/**
	 * Gets the {@link UUID} of the {@link Thing} that was changed
	 * @return the {@link Thing}
	 */
	public UUID getUUID() {
		return uuid;
	}
	/**
	 * Gets the {@link Location} the change happened
	 * @return the {@link Location}
	 */
	public Location3d getLocation() {
		return location;
	}

	public void run() {
		GameSpace sp = Game.getRoom().getSpace(location);
		sp.removeThing(sp.getThing(uuid));
	}
	
	@Override
	public String asJSON() {
		String out = "{";
		out += "\"" + ThingRemoveEventJsonKeys.UUID + "\":\"" + uuid.toString() + "\",";
		out += "\"" + ThingRemoveEventJsonKeys.LOCATION + "\":\"" + location.asJSON() + "\"";
		return super.asJSON(EventTypes.THING_REMOVE_EVENT, out + "}");
	}
	
	@Override
	public String toString() {
		return "GSChangeEvent[" + uuid + " at " + location.toSimpleString() + "]";
	}
}
