package benjaminc.chef_simulator.data;

import java.util.ArrayList;
import java.util.List;

import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.util.JSONTools;

public class Inventory implements Savable {
	
	protected List<Thing> inv;
	
	/**
	 * Generates a new {@link Inventory} with the contents of inv
	 * @param inv the {@link List} of {@link Thing} to make the inventory from
	 */
	public Inventory(List<Thing> inv) {
		this.inv = inv;
	}
	/**
	 * Creates a blank {@link Inventory}
	 */
	public Inventory() {
		inv = new ArrayList<Thing>();
	}
	/**
	 * Creates an {@link Inventory} and add a {@link Thing}
	 * @param t the {@link Thing} to add
	 */
	public Inventory(Thing t) {
		this();
		add(t);
	}
	/**
	 * Creates an {@link Inventory} from JSON
	 * @param JSON the JSON {@link String}
	 */
	public Inventory(String json) {
		inv = new ArrayList<Thing>();
		
		List<String> jm = JSONTools.splitJSONArray(json);

		for(String s : jm) {
			inv.add(BasicThing.makeThingFromJSON(s));
		}
	}
	
	@Override
	public String asJSON() {
		String s = "[";
		boolean fencepost = false;
		for(Thing t : inv) {
			if(fencepost) {
				s = s + ", ";
			} else {
				fencepost = true;
			}
			s = s + t.asJSON();
		}
		s = s + "]";
		return s;
	}
	
	
	/**
	 * Adds the {@link Thing} to the {@link Inventory}
	 * @param t the {@link Thing} to add
	 */
	public void add(Thing t) {
		inv.add(t);
	}
	/**
	 * Add an {@link Thing} at a location
	 * @param loc The int location to put the {@link Thing}
	 * @param t the {@link Thing} item to put
	 */
	public void add(int loc, Thing t) {
		if(loc < inv.size() && loc >= 0) {
			inv.add(loc, t);
		}
	}
	/**
	 * Adds an the contents of another {@link Inventory} to this one
	 * @param add the {@link Inventory} to add;
	 */
	public void add(Inventory add) {
		inv.addAll(add.getAll());
	}
	/**
	 * Forcefully puts a {@link Thing} at a location. Overrides and returns whatever was there
	 * Adds the item to the end if the location was out of bounds
	 * @param t the {@link Thing} to place
	 * @param loc the int location to put it
	 * @return the {@link Thing} that was there, null if the range was out of bounds
	 */
	public Thing put(Thing t, int loc) {
		if(loc < inv.size() && loc >= 0) {
			return inv.set(loc, t);
		} else {
			add(t);
			return null;
		}
	}
	/**
	 * Gets the {@link Thing} at that location
	 * @param loc the int location for the item
	 * @return the {@link Thing} there
	 */
	public Thing get(int loc) {
		return inv.get(loc);
	}
	/**
	 * Gets the {@link List} of {@link Thing} of the {@link Inventory}
	 * @return the {@link List} of {@link Thing}
	 */
	public List<Thing> getAll() {
		return inv;
	}
	/**
	 * Removes a {@link Thing} from the {@link Inventory} at the specified location
	 * @param loc The int location to remove from
	 * @return the {@link Thing} removed
	 */
	public Thing remove(int loc) {
		return inv.remove(loc);
	}
	/**
	 * Removes a {@link Thing} from the {@link Inventory}. Uses {@link List#remove(Object)}
	 * @param t the {@link Thing} to remove
	 * @return a boolean of if an item was removed
	 */
	public boolean remove(Thing t) {
		return inv.remove(t);
	}
	/**
	 * Clears the {@link Inventory}.
	 * @return a copy of the {@link Inventory} before it was cleared
	 */
	public List<Thing> clear() {
		List<Thing> tmp = new ArrayList<Thing>();
		for(int i = 0; i < size(); i++) {
			tmp.add(inv.get(i));
		}
		inv.clear();
		return tmp;
	}
	/**
	 * Returns the size of the {@link Inventory}
	 * @return the int size
	 */
	public int size() {
		return inv.size();
	}
	/**
	 * Tests if the {@link Inventory} is empty.
	 * @return a boolean of if the {@link Inventory} is empty
	 */
	public boolean isEmpty() {
		return inv.isEmpty();
	}
	/**
	 * Clones the {@link Inventory}
	 * @return the cloned {@link Inventory}
	 */
	public Inventory clone() {
		List<Thing> newinv = new ArrayList<Thing>();
		for(int i = 0; i < size(); i++) {
			newinv.add(inv.get(i).clone());
		}
		return new Inventory(newinv);
	}
	/**
	 * Checks if this {@link Inventory} is identical to another
	 * @param in the {@link Inventory} to check against
	 * @return a boolean of identicality
	 */
	public boolean equals(Inventory in) {
		if(inv.size() == in.size()) {
			return false;
		}
		for(int i = 0; i < size(); i++) {
			if(!inv.get(i).equals(in.get(i))) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Checks if this {@link Inventory} has exactly the same contents, but in different spaces as the other
	 * @param in the {@link Inventory} to check against
	 * @return a boolean of if they have the same stuff
	 */
	public boolean hasSame(Inventory in) {
		Inventory test = in.clone();
		System.out.println("me " + getAll());
		System.out.println("in " + in.getAll());
		for(int i = 0; i < size(); i++) {
			if(inv.get(i) != null && !test.remove(inv.get(i))) {
				System.out.println("Cant't remove item fail");
				return false;
			}
		}
		System.out.println("Inv returns true");
		return true;
	}
}
