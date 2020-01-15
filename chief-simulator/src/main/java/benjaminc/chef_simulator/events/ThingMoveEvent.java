package benjaminc.chef_simulator.events;

import java.util.Map;
import java.util.UUID;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.control.Location3d;
import benjaminc.chef_simulator.data.Savable;
import benjaminc.chef_simulator.data.DataMap.DataMapKey;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.util.JSONTools;

/**
 * {@link Event} fired when a {@link Thing} moves
 * 
 * @author Benjamin Crall
 *
 */
public class ThingMoveEvent extends Event implements Savable {
	
	private enum ThingRemoveEventJsonKeys {
		UUID, FROM, TO;
	}

	private UUID uuid;
	private Location3d from;
	private Location3d to;
	
	/**
	 * @param type	the {@link GSChangeEventTypes} of the change
	 * @param t		the {@link Thing} affected
	 * @param loc	the {@link Location3d} of the thing
	 */
	public ThingMoveEvent(Thing thing, Location3d from, Location3d to) {
		this.uuid = (UUID) thing.getDataMap().get(DataMapKey.UUID);
		this.from = from;
		this.to = to;
	}
	
	/**
	 * @param type	the {@link GSChangeEventTypes} of the change
	 * @param t		the {@link Thing} affected
	 * @param loc	the {@link Location3d} of the thing
	 */
	public ThingMoveEvent(UUID uuid, Location3d from, Location3d to) {
		this.uuid = uuid;
		this.from = from;
		this.to = to;
	}
	
	public ThingMoveEvent(String json) {
		System.out.println(json);
		if(json.charAt(0) == '{' && json.charAt(json.length()-1) == '}') {

			json = json.substring(1, json.length() - 1);
			Map<String, String> js = JSONTools.splitJSON(json);
			for(String s : js.keySet()) {
				try {
					ThingRemoveEventJsonKeys tdk = ThingRemoveEventJsonKeys.valueOf(s);
					switch(tdk) {
					case FROM: from = new Location3d(js.get(s)); break;
					case TO: to = new Location3d(js.get(s)); break;
					case UUID: uuid = UUID.fromString(js.get(s)); break;
					default: break;
					 }
				} catch(IllegalArgumentException e) {}
			}
			
			if(uuid != null && from != null && to != null) {
				return;
			}
		}
		throw new IllegalArgumentException("The GSChangeEvent could not be created from the provided JSON");
	}

	/**
	 * Gets the {@link UUID} of the affected {@link Thing}
	 * @return	the {@link UUID}
	 */
	public UUID getUUID() {
		return uuid;
	}
	/**
	 * Gets the {@link Location} the {@link Thing} came from
	 * @return	the old {@link Location}
	 */
	public Location3d getFrom() {
		return from;
	}
	/**
	 * Gets the {@link Location} the {@link Thing} is going to
	 * @return	the new {@link Location}
	 */
	public Location3d getTo() {
		return to;
	}
	/**
	 * Sets the {@link Location} the {@link Thing} is going to
	 * @param to the new {@link Location}
	 */
	public void setTo(Location3d to) {
		this.to = to;
	}
	
	@Override
	public void run() {
		Game.getRoom().getSpace(to).addThing(Game.getRoom().getSpace(from).removeThing(uuid));
	}
	
	@Override
	public String asJSON() {
		String out = "{";
		out += "\"" + ThingRemoveEventJsonKeys.UUID + "\":\"" + uuid + "\",";
		out += "\"" + ThingRemoveEventJsonKeys.FROM + "\":\"" + from.asJSON() + "\",";
		out += "\"" + ThingRemoveEventJsonKeys.TO + "\":\"" + to.asJSON() + "\"";
		return super.asJSON(EventTypes.THING_MOVE_EVENT, out + "}");
	}
	
	@Override
	public String toString() {
		return "ThingMoveEvent[" + uuid + " " + from.toSimpleString() + " at " + to.toSimpleString() + "]";
	}
}
