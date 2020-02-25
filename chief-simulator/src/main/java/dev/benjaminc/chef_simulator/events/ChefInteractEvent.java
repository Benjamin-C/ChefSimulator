package dev.benjaminc.chef_simulator.events;

import java.util.Map;
import java.util.UUID;

import dev.benjaminc.chef_simulator.Game;
import dev.benjaminc.chef_simulator.control.Chef;
import dev.benjaminc.chef_simulator.data.location.Location2d;
import dev.benjaminc.chef_simulator.data.location.Location3d;
import dev.benjaminc.chef_simulator.things.Thing;
import dev.benjaminc.util.JSONTools;

public class ChefInteractEvent extends Event {

	private enum ChefInteractEventJsonKeys {
		TYPE, CHEF_NAME, THING;
	}
	public enum ChefInteractEventTypes {
		PICK_UP, PUT_DOWN;
	}

	private ChefInteractEventTypes type;
	private String chefName = "";
	private UUID uuid;
	
	public ChefInteractEvent(ChefInteractEventTypes type, String chefName, Thing thing) {
		this.type = type;
		this.chefName = chefName;
		this.uuid = thing.getUUID();
	}
	
	public ChefInteractEvent(String json) {
		if(json.charAt(0) == '{' && json.charAt(json.length()-1) == '}') {

			json = json.substring(1, json.length() - 1);
			System.out.println(json);
			Map<String, String> js = JSONTools.splitJSON(json);
			for(String s : js.keySet()) {
				ChefInteractEventJsonKeys tdk = ChefInteractEventJsonKeys.valueOf(s);
				switch(tdk) {
				case TYPE: type = ChefInteractEventTypes.valueOf(js.get(s)); break;
				case CHEF_NAME: chefName = js.get(s); break;
				case THING: uuid = UUID.fromString(js.get(s));
				default: break;
				}
			}
			if(!chefName.equals("") && type != null && uuid != null) {
				return;
			}
		}
		throw new IllegalArgumentException("The ChefInteractEvent could not be created from the provided JSON");
	}
	
	public ChefInteractEventTypes getType() {
		return type;
	}
	public void setMessage(ChefInteractEventTypes type) { 
		this.type = type;
	}
	public String getName() {
		return chefName;
	}
	public void setTime(String name) {
		this.chefName = name;
	}
	public void run() {
		Chef c = Game.getChefByName(chefName);
		Location2d newloc = c.getHandLocation();
		
		switch(type) {
		case PICK_UP: {
			Thing thing = Game.getRoom().getSpace(newloc).getThing(uuid);
			c.setHand(thing);
			Game.getRoom().getSpace(newloc).removeThing(thing);
			Game.openglEngine.moveItem(thing, new Location3d(-1, -1, -1));
		} break;
		case PUT_DOWN: {
			Thing thing = c.getHand();
			Location3d addat = Game.getRoom().getSpace(newloc).addThing(thing);
			Game.openglEngine.moveItem(thing, addat);
			c.setHand(null);
		} break;
		}
	}
	
	@Override
	public String asJSON() {
		String out = "{";
		out += "\"" + ChefInteractEventJsonKeys.TYPE + "\":\"" + type + "\",";
		out += "\"" + ChefInteractEventJsonKeys.CHEF_NAME + "\":\"" + chefName + "\",";
		out += "\"" + ChefInteractEventJsonKeys.THING + "\":\"" + uuid + "\"";
		return super.asJSON(EventTypes.CHEF_INTERACT_EVENT, out + "}");
	}
	
	@Override
	public String toString() {
		return "ChefInteractEvent[" + type + " from " + chefName + "]";
	}
}
