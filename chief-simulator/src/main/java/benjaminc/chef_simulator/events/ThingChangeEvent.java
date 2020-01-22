package benjaminc.chef_simulator.events;

import java.util.Map;
import java.util.UUID;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.location.Location2d;
import benjaminc.chef_simulator.data.location.Location3d;
import benjaminc.chef_simulator.graphics.GameSpace;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.util.JSONTools;

/**
 * {@link Event} fired when a {@link GameSpace} changes
 * 
 * @author Benjamin Crall
 *
 */
public class ThingChangeEvent extends Event {
	
	private enum ThingChangeEventJsonKeys {
		UUID, DATA, LOCATION;
	}
	
	private UUID uuid;
	private DataMap data;
	private Location3d location;
	
	/**
	 * @param type	the {@link GSChangeEventTypes} of the change
	 * @param t		the {@link Thing} affected
	 * @param loc	the {@link Location3d} of the thing
	 */
	public ThingChangeEvent(UUID uuid, DataMap data, Location3d loc) {
		this.uuid = uuid;
		this.data = data;
		this.location = loc;
	}
	
	public ThingChangeEvent(String json) {
		System.out.println(json);
		if(json.charAt(0) == '{' && json.charAt(json.length()-1) == '}') {

			json = json.substring(1, json.length() - 1);
			Map<String, String> js = JSONTools.splitJSON(json);
			for(String s : js.keySet()) {
				try {
					ThingChangeEventJsonKeys tdk = ThingChangeEventJsonKeys.valueOf(s);
					switch(tdk) {
					case UUID: uuid = UUID.fromString(js.get(s)); break;
					case DATA: data = new DataMap(js.get(s)); break;
					case LOCATION: location = new Location3d(js.get(s)); break;
					default: break;
					}
				} catch(IllegalArgumentException e) {}
			}
			if(uuid != null && data != null && location != null) {
				return;
			}
		}
		throw new IllegalArgumentException("The ThingChangeEvent could not be created from the provided JSON");
	}
	
	/**
	 * Gets the {@link UUID} of the {@link Thing} that was changed
	 * @return the {@link Thing}
	 */
	public UUID getThing() {
		return uuid;
	}
	/**
	 * Gets the {@link DataMap} of the thing;
	 * @return
	 */
	public DataMap getData() {
		return data;
	}
	/**
	 * Gets the {@link Location2d} the change happened
	 * @return the {@link Location2d}
	 */
	public Location3d getLocation() {
		return location;
	}
	/**
	 * Sets the Thing being changed
	 * @param thing	the new {@link Thing}
	 */
	public void setData(DataMap data) {
		this.data = data;
	}

	public void run() {
		Thing th = Game.getRoom().getSpace(location).getThing(location.getZ());
		if(th.getUUID().equals(uuid)) {
			th.setDataMap(data);
		}
	}
	
	@Override
	public String asJSON() {
		String out = "{";
		out += "\"" + ThingChangeEventJsonKeys.UUID + "\":\"" + uuid.toString() + "\",";
		out += "\"" + ThingChangeEventJsonKeys.DATA + "\":\"" + data.asJSON() + "\",";
		out += "\"" + ThingChangeEventJsonKeys.LOCATION + "\":\"" + location.asJSON() + "\"";
		return super.asJSON(EventTypes.THING_CHANGE_EVENT, out + "}");
	}
	
	@Override
	public String toString() {
		return "ThingChangeEvent[" + uuid + " " + data + " at " + location.toSimpleString() + "]";
	}
}
