package dev.orangeben.chef_simulator.events;

import java.util.Map;
import java.util.UUID;

import org.lwjglb.engine.items.OpenGLItem;

import dev.orangeben.chef_simulator.Game;
import dev.orangeben.chef_simulator.data.Savable;
import dev.orangeben.chef_simulator.data.DataMap.DataMapKey;
import dev.orangeben.chef_simulator.data.location.Location2d;
import dev.orangeben.chef_simulator.data.location.Location3d;
import dev.orangeben.chef_simulator.things.Thing;
import dev.orangeben.util.JSONTools;

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
		this.uuid = thing.getUUID();
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
	 * Gets the {@link Location2d} the {@link Thing} came from
	 * @return	the old {@link Location2d}
	 */
	public Location3d getFrom() {
		return from;
	}
	/**
	 * Gets the {@link Location2d} the {@link Thing} is going to
	 * @return	the new {@link Location2d}
	 */
	public Location3d getTo() {
		return to;
	}
	/**
	 * Sets the {@link Location2d} the {@link Thing} is going to
	 * @param to the new {@link Location2d}
	 */
	public void setTo(Location3d to) {
		this.to = to;
	}
	
	@Override
	public void run() {
		Thing t = Game.getRoom().getSpace(from).removeThing(uuid);
		Location3d newloc = Game.getRoom().getSpace(to).addThing(t);
        if(Game.usingOpenGL) {
		    Game.openglEngine.moveItem(t, newloc);
        }
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
