package dev.benjaminc.chef_simulator.events;

import java.util.Map;

import dev.benjaminc.chef_simulator.Game;
import dev.benjaminc.chef_simulator.control.Chef;
import dev.benjaminc.chef_simulator.control.Direction;
import dev.benjaminc.chef_simulator.data.location.Location2d;
import dev.benjaminc.util.JSONTools;

/**
 * Event fired when a cook moves
 * 
 * @author Benjamin Crall
 *
 */
public class ChefMoveEvent extends Event {
	
	private enum ChefMoveEventJsonKeys {
		START_DIR, END_DIR, START_LOC, END_LOC, COOK_ID
	}
	
	private Direction startDir;
	private Direction endDir;
	private Location2d startLoc;
	private Location2d endLoc;
	private Chef cook;
	
	/**
	 * Makes a new Chef Move Event
	 * @param startDir	the {@link Direction} the {@link Chef} was looking
	 * @param endDir	the {@link Direction} the {@link Chef} is now looking
	 * @param startLoc	the {@link Location2d} the {@link Chef} was in
	 * @param endLoc	the {@link Location2d} the {@link Chef} is now in
	 * @param cook		the {@link Chef}
	 */
	public ChefMoveEvent(Direction startDir, Direction endDir, Location2d startLoc, Location2d endLoc, Chef cook) {
		super();
		this.startDir = startDir;
		this.endDir = endDir;
		this.startLoc = startLoc;
		this.endLoc = endLoc;
		this.cook = cook;
	}

	public ChefMoveEvent(String json) {
		if(json.charAt(0) == '{' && json.charAt(json.length()-1) == '}') {
			json = json.substring(1, json.length() - 1);
			Map<String, String> js = JSONTools.splitJSON(json);
			for(String s : js.keySet()) {
				try {
					ChefMoveEventJsonKeys tdk = ChefMoveEventJsonKeys.valueOf(s);
					switch(tdk) {
					case COOK_ID: cook = Game.getChefByName(js.get(s)); break;
					case END_DIR: endDir = Direction.valueOf(js.get(s)); break;
					case START_DIR:  startDir = Direction.valueOf(js.get(s));break;
					case END_LOC: endLoc = new Location2d(js.get(s)); break;
					case START_LOC: startLoc = new Location2d(js.get(s)); break;
					default: break;
					}
				} catch(IllegalArgumentException e) {}
			}
			if(cook != null && startDir != null && endDir != null && startLoc != null && endLoc != null) {
				return;
			}
		}
		throw new IllegalArgumentException("The ChefMoveEvent could not be created from the provided JSON");
	}
	
	/**
	 * Gets the {@link Direction} the {@link Chef} is now looking
	 * @return the {@link Direction}
	 */
	public Direction getEndDir() {
		return endDir;
	}
	/**
	 * Gets the {@link Location2d} the {@link Chef} is now at
	 * @return the {@link Location2d}
	 */
	public Location2d getEndLoc() {
		return endLoc;
	}
	/**
	 * Gets the {@link Direction} the {@link Chef} was looking
	 * @return the {@link Direction}
	 */
	public Direction getStartDir() {
		return startDir;
	}
	/**
	 * Gets the {@link Location2d} the {@link Chef} was at
	 * @return the {@link Location2d}
	 */
	public Location2d getStartLoc() {
		return startLoc;
	}
	/**
	 * Gets the {@link Chef} that moved
	 * @return the {@link Chef}
	 */
	public Chef getCook() {
		return cook;
	}
	
	/**
	 * Sets the {@link Location2d} the cook is going to
	 * @param endLoc the new {@link Location2d}
	 */
	public void setEndLoc(Location2d endLoc) {
		this.endLoc = endLoc;
	}
	/**
	 * Sets the {@link Direction} the {@link Chef} will be facing
	 * @param endDir the new {@link Direction}
	 */
	public void setEndDir(Direction endDir) {
		this.endDir = endDir;
	}
	
	@Override
	public String toString() {
		return "ChefMoveEvent[from " + startLoc.toSimpleString() + "," + startDir + " to "
				+ endLoc.toSimpleString() + "," + endDir + "]";
	}
	
	@Override
	public String asJSON() {
		String out = "{";
		out += "\"" + ChefMoveEventJsonKeys.START_DIR + "\":\"" + startDir + "\",";
		out += "\"" + ChefMoveEventJsonKeys.END_DIR + "\":\"" + endDir + "\",";
		out += "\"" + ChefMoveEventJsonKeys.START_LOC + "\":\"" + startLoc.asJSON() + "\",";
		out += "\"" + ChefMoveEventJsonKeys.END_LOC + "\":\"" + endLoc.asJSON() + "\",";
		out += "\"" + ChefMoveEventJsonKeys.COOK_ID + "\":\"" + cook.getName() + "\"";
		return super.asJSON(EventTypes.CHEF_MOVE_EVENT, out + "}");
	}
	
	@Override
	public void run() {
		cook.setLocation(endLoc);
		cook.setDirection(endDir);
	}
}
