package benjaminc.chef_simulator.graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import benjaminc.chef_simulator.rooms.Room;
import benjaminc.chef_simulator.things.*;
import benjaminc.chef_simulator.things.building.Floor;
import benjaminc.chef_simulator.things.types.SolidThing;
import benjaminc.chef_simulator.things.types.Tickable;

/**
 * the spaces of the {@link Room}
 * @author Benjamin-C
 *
 */
public class GameSpace {

	protected List<Thing> things;
	protected Location2d loc;
	protected boolean isChanged;
	
	/**
	 * Make a new GameSpace
	 * 
	 * @param loc the {@link Location2d} the space exists at
	 */
	public GameSpace(Location2d loc) {
		this(loc, true);
	}
	
	/**
	 * Make a new GameSpace
	 * 
	 * @param loc 	the {@link Location2d} the space exists at
	 * @param floor the boolean to add a floor or not
	 */
	public GameSpace(Location2d loc, boolean floor) {
		things = new ArrayList<Thing>();
		if(floor) {
			things.add(new Floor());
		}
		this.loc = loc;
	}
	
	/**
	 * Makes a new GameSpace
	 * 
	 * @param loc		the {@link Location2d}
	 * @param things	the {@link List} of {@link Thing} at the space
	 */
	public GameSpace(Location2d loc, List<Thing> things) {
		this.loc = loc;
		this.things = things;
	}
	/**
	 * Gets the location the {@link GameSpace} represents
	 * 
	 * @return the {@link Location2d}
	 */
	public Location2d getLoc() {
		return loc;
	}
	/**
	 * Adds a {@link Thing} to the space<br/>
	 * Calls {@link #addThing(int, Thing)} with elev of {@link Integer#MAX_VALUE}
	 * 
	 * @param t the {@link Thing} to add
	 */
	public void addThing(Thing t) {
		isChanged = true;
		addThing(Integer.MAX_VALUE, t);
	}
	/**
	 * Adds a thing to this {@link GameSpace} at a specific elevation
	 * 
	 * @param elev 	the int elevation of the new {@link Thing}
	 * @param t the new {@link Thing}
	 */
	public void addThing(int elev, Thing t) {
		isChanged = true;
		if(t != null) {
			if(elev < size()) {
				things.add(elev, t);
//				EventHandler.onEvent(new ThingChangeEvent(GSChangeEventTypes.ADD, t, loc.toLocation3d(elev)));
			} else {
				things.add(t);
//				EventHandler.onEvent(new ThingChangeEvent(GSChangeEventTypes.ADD, t, loc.toLocation3d(size()-1)));
			}
		}
	}
	/**
	 * Removes a {@link Thing} by a {@link UUID}
	 * @param uuid	the {@link UUID} of the {@link Thing} to remove
	 * @return		the {@link Thing} that was removed
	 */
	public Thing removeThing(UUID uuid) {
		return removeThing(getThing(uuid));
	}
	/**
	 * Removes a {@link Thing} from the {@link GameSpace}
	 * 
	 * @param t 	the {@link Thing} to remove
	 * @return the 	removed {@link Thing}
	 */
	public Thing removeThing(Thing t) {
		if(t != null) {
			isChanged = true;
	//		EventHandler.onEvent(new ThingChangeEvent(GSChangeEventTypes.REMOVE, t, loc.toLocation3d(things.indexOf(t))));
			things.remove(t);
			return t;
		} else {
			return null;
		}
	}
	/**
	 * Removes all instances of a {@link Thing} from the space
	 * 
	 * @param t the {@link Thing}
	 */
	public void removeAll(Thing t) {
		isChanged = true;
		for(int i = 0; i < things.size(); i++) {
			if(things.get(i).getClass().isAssignableFrom( t.getClass() )) {
				System.out.println("Found thing to remove");
				/* Thing rm = */things.remove(i);
//				EventHandler.onEvent(new ThingChangeEvent(GSChangeEventTypes.REMOVE, things.remove(i), loc.toLocation3d(i)));
				i--;
			}
		}
	}
	/**
	 * Remove a {@link Thing} by its elevation
	 * 
	 * @param elev 	the int elevation of the {@link Thing} to remove
	 * @return the 	{@link Thing} that was removed
	 */
	public Thing removeThing(int elev) {
		isChanged = true;
		Thing temp = things.remove(elev);
//		EventHandler.onEvent(new ThingChangeEvent(GSChangeEventTypes.REMOVE, temp, loc.toLocation3d(elev)));
		if(things.size() < 1) {
			things.add(new Floor());
		}
		return temp;
	}
	/**
	 * Removes the top {@link Thing} from a room
	 * @return the {@link Thing} that was removed
	 */
	public Thing removeTopThing() {
		isChanged = true;
		Thing rm = removeThing(things.size()-1);
//		EventHandler.onEvent(new ThingChangeEvent(GSChangeEventTypes.REMOVE, rm, loc.toLocation3d(things.size()-1)));
		return rm;
	}
	/**
	 * Get the {@link Thing} at the elevation
	 * 
	 * @param elev the int elevation of the thing to get
	 * @return the {@link Thing}
	 */
	public Thing getThing(int elev) {
		if(elev < things.size() && elev >= 0) {
			return things.get(elev);
		} else {
			return null;
		}
	}
	/**
	 * Gets the top {@link Thing} in the space
	 * 
	 * @return the {@link Thing}
	 */
	public Thing getThing() {
		return things.get(things.size() - 1);
	}
	/**
	 * Gets a {@link Thing} by {@link UUID}
	 * @param uuid	the {@link UUID} of the {@link Thing}
	 * @return		the {@link Thing}
	 */
	public Thing getThing(UUID uuid) {
		for(Thing t : things) {
			if(t.getUUID().equals(uuid)) {
				return t;
			}
		}
		return null;
	}
	/**
	 * Gets all {@link Thing}s here
	 * @return a {@link List} of {@link Thing} in the gamespace
	 */
	public List<Thing> getThings() {
		return things;
	}
	
	/**
	 * Draw the {@link GameSpace}
	 * 
	 * @param g 	the {@link GraphicalDrawer} to use
	 * @param x 	the int x ofset
	 * @param y 	the int y ofset
	 * @param w 	the int width
	 * @param h 	the int height
	 */
	public void draw(GraphicalDrawer g, int x, int y, int w, int h) {
		for(int i = 0; i < things.size(); i++) {
			g.draw(things.get(i), x, y, w, h);
		}
	}
	/**
	 * Checks to see if the space contains a {@link Thing}
	 * 
	 * @param test 	the {@link Class} of the {@link Thing} to test for
	 * @return 		the boolean if true
	 */
	public boolean contains(Class<?> test) {
		for(Thing th : things) {
			if(th.getSubclass() == test) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Checks if any Thing is solid in the space
	 * 
	 * @return the boolean if solid
	 */
	public boolean isSolid() {
		for(Thing th : things) {
			if(th instanceof SolidThing) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Ticks the {@link GameSpace}
	 * 
	 * @param r 	the {@link Room} the {@link GameSpace} is in
	 * @param l 	the {@link Location2d} in the room
	 * @param 		frame the long frame of the game
	 */
	public void tick(Room r, Location2d l, long frame) {
		for(int i = 0; i < things.size(); i++) {
			Thing t = things.get(i);
			if(t instanceof Tickable) {
				((Tickable) t).tick(r, l, frame);
			}
		}
		if(isChanged) {
//			System.out.println("GameSpace at " + loc.getX() + "," + loc.getY() + "changed");
		}
		isChanged = false;
	}
	/**
	 * Gets the size of the {@link GameSpace}
	 * 
	 * @return the int size
	 */
	public int size() {
		return things.size();
	}
	/**
	 * Gets if the {@link GameSpace} has been changed
	 * 
	 * @return the boolean isChanged
	 */
	public boolean isChanged() {
		return isChanged;
	}
	/**
	 * Sets isChanged to true
	 */
	public void setChanged() {
		isChanged = true;
	}
	/**
	 * Sets if the {@link GameSpace} has been changed
	 * 
	 * @param isChanged the boolean isChanged
	 */
	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}
	@Override
	public String toString() {
		String str = "GameSpace(" + loc.getX() + "," + loc.getY() + ")[";
		for(int i = 0; i < things.size(); i++) {
			if(i != 0) {
				str = str + ",";
			}
			str = str + things.get(i).getName();
		}
		str = str + "]";
		return str;
	}
}
