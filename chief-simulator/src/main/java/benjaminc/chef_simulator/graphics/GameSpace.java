package benjaminc.chef_simulator.graphics;

import java.util.ArrayList;
import java.util.List;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.rooms.Room;
import benjaminc.chef_simulator.things.*;
import benjaminc.chef_simulator.things.building.Floor;
import benjaminc.chef_simulator.things.types.SolidThing;
import benjaminc.chef_simulator.things.types.Tickable;

public class GameSpace {

	protected List<Thing> things;
	protected Location loc;
	
	
	public GameSpace(Location l) {
		things = new ArrayList<Thing>();
		things.add(new Floor());
		loc = l;
	}
	
	/**
	 * Sets the location the {@link GameSpace} represents
	 * @param l the new {@link Location}
	 */
	public void setLoc(Location l) {
		loc = l;
	}
	/**
	 * Gets the location the {@link GameSpace} represents
	 * @return the {@link Location}
	 */
	public Location getLoc() {
		return loc;
	}
	/**
	 * Adds a {@link Thing} to the space
	 * @param t the {@link Thing} to add
	 */
	public void addThing(Thing t) {
		if(t != null) {
			things.add(t);
		}
	}
	/**
	 * Adds a thing to this {@link GameSpace} at a specific elevation
	 * @param elev the int elevation of the new {@link Thing}
	 * @param t the new {@link Thing}
	 */
	public void addThing(int elev, Thing t) {
		if(t != null) {
			things.add(elev, t);
		}
	}
	/**
	 * Removes a {@link Thing} from the {@link GameSpace}
	 * @param t the {@link Thing} to remove
	 * @return the removed {@link Thing}
	 */
	public Thing removeThing(Thing t) {
		things.remove(t);
		return t;
	}
	/**
	 * Removes all instances of a {@link Thing} from the space
	 * @param t the {@link Thing}
	 */
	public void removeAll(Thing t) {
		for(int i = 0; i < things.size(); i++) {
			if(things.get(i).getClass().isAssignableFrom( t.getClass() )) {
				things.remove(i--);
			}
		}
	}
	/**
	 * Remove a {@link Thing} by its elevation
	 * @param elev the int elevation of the {@link Thing} to remove
	 * @return the {@link Thing} that was removed
	 */
	public Thing removeThing(int elev) {
		Thing temp = things.remove(elev);
		if(things.size() < 1) {
			things.add(new Floor());
		}
		return temp;
	}
	/**
	 * Removes the top {@link Thing} from a room
	 * @return
	 */
	public Thing removeTopThing() {
		return removeThing(things.size()-1);
	}
	/**
	 * Get the {@link Thing} at the elevation
	 * @param elev the int elevation of the thing to get
	 * @return
	 */
	public Thing getThing(int elev) {
		return things.get(elev);
	}
	/**
	 * Gets the top {@link Thing} in the space
	 * @return the {@link Thing}
	 */
	public Thing getThing() {
		return things.get(things.size() - 1);
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
	 * @param g the {@link GraphicalDrawer} to use
	 * @param x the int x ofset
	 * @param y the int y ofset
	 * @param w the int width
	 * @param h the int height
	 */
	public void draw(GraphicalDrawer g, int x, int y, int w, int h) {
		for(int i = 0; i < things.size(); i++) {
			g.draw(things.get(i), x, y, w, h);
		}
	}
	/**
	 * Checks to see if the space contains a {@link Thing}
	 * @param test the {@link Class} of the {@link Thing} to test for
	 * @return the boolean if true
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
	 * @param r the {@link Room} the {@link GameSpace} is in
	 * @param l the {@link Location} in the room
	 * @param frame the long frame of the game
	 * @param g the {@link Game} the room is in
	 */
	public void tick(Room r, Location l, long frame, Game g) {
		for(int i = 0; i < things.size(); i++) {
			Thing t = things.get(i);
			if(t instanceof Tickable) {
				((Tickable) t).tick(r, l, frame, g);
			}
		}
	}
}
