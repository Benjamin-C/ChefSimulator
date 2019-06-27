package benjaminc.chief_simulator.data;

import java.util.ArrayList;
import java.util.List;

import benjaminc.chief_simulator.things.Thing;

public class Inventory {
	
	protected Thing[] inv;
	
	/**
	 * Generates a new inventory with the contents of inv, and a max size the same as inv
	 * @param inv the List<Thing> to make the inventory from
	 */
	public Inventory(Thing[] inv) {
		this.inv = inv;
	}
	/**
	 * Generates a new inventory with the contents of inv, and a max size the same as inv
	 * @param inv the List<Thing> to make the inventory from
	 */
	public Inventory(List<Thing> inv) {
		this.inv = (Thing[]) inv.toArray();
	}
	/**
	 * Creates a blank inventory with the max size of maxSize
	 * @param maxSize the int maximum size of the inventory
	 */
	public Inventory(int maxSize) {
		inv = new Thing[maxSize];
	}
	public Inventory(Thing t) {
		inv = new Thing[1];
		inv[0] = t;
	}
	/**
	 * Adds the item to the first available spot in the inventory
	 * @param t the Thing to add
	 * @return success
	 */
	public boolean add(Thing t) {
		int i = 0;
		while(i < inv.length) {
			if(inv[i] == null) {
				inv[i] = t;
				return true;
			}
		}
		return false;
	}
	/**
	 * Add an item at a location, failing if the spot is already occupied
	 * @param loc The int location to put the item
	 * @param t the Thing item to put
	 * @return success, false if spot already occupied.
	 */
	public boolean add(int loc, Thing t) {
		if(inv[loc] == null) {
			inv[loc] = t;
			return true;
		}
		return false;
	} // TODO add itmecount
	/**
	 * Adds an the contents of another inventory to this one
	 * @param add the Inventory to add;
	 * @return a Thing[] of all items that did not fit in this one
	 */
	public Thing[] add(Inventory add) {
		Inventory failed = new Inventory(add.size());
		for(Thing t : add.getAll()) {
			if(t != null) {
				if(!add(t)) {
					failed.add(t);
				}
			}
		}
		return failed.getAll();
	}
	/**
	 * Forcefully puts t in loc. Overrides and returns whatever was there
	 * @param t
	 * @param loc
	 */
	public Thing put(Thing t, int loc) {
		Thing old = inv[loc];
		inv[loc] = t;
		return old;
	}
	/**
	 * Gets the item at that location
	 * @param loc the int location for the item
	 * @return the Thing there
	 */
	public Thing get(int loc) {
		return inv[loc];
	}
	/**
	 * Gets a snapshot of the contents of the inventory
	 * @return a snapshot List<Thing> of the inventory
	 */
	public Thing[] getAll() {
		return clone().inv;
	}
	public List<Thing> getAllAsList() {
		List<Thing> out = new ArrayList<Thing>();
		for(Thing t : inv) {
			out.add(t);
		}
		return out;
	}
	/**
	 * Removes the item from the inventory at the specified location
	 * @param loc The location of the inventory to remove from
	 * @return the Thing removed
	 */
	public Thing remove(int loc) {
		Thing out = inv[loc];
		inv[loc] = null;
		return out;
	}
	/**
	 * Removes the Thing t from the inventory. Removes the first instance of the item
	 * @param t the thing T to remove
	 * @return a boolean of if an item was removed
	 */
	public boolean remove(Thing t) {
		for(int i = 0; i < inv.length; i++) {
			if(inv[i].isSame(t)) {
				remove(i);
				return true;
			}
		}
		return false;
	}
	/**
	 * Clears the invantory.
	 */
	public Thing[] clear() {
		Thing[] tmp = getAll();
		for(int i = 0; i < inv.length; i++) {
			inv[i] = null;
		}
		return tmp;
	}
	/**
	 * Returns the max size of the inventory
	 * @return the int max size
	 */
	public int size() {
		return inv.length - 1;
	}
	/**
	 * Counts the number of non-null Things in the Inventory
	 * @return an int of the number of Things in the Inventory
	 */
	public int thingCount() {
		int count = 0;
		for(Thing t : inv) {
			if(t != null) {
				count++;
			}
		}
		return count;
	}
	/**
	 * Tests if the inventory is empty. Returns true if thingCount() == 0
	 * @return a boolean of if the inventory is empty
	 */
	public boolean isEmpty() {
		return thingCount() == 0;
	}
	/**
	 * Changes the maximim size of the inventory
	 * @param max int the new max size
	 * @return an Inventory with the leftover items if the new size is smaller
	 */
	public Inventory setMaxSize(int max) {
		Thing[] newInv = new Thing[max];
		Thing[] out = null;
		if(max < inv.length) {
			out = new Thing[inv.length - max];
			for(int i = 0; i < out.length; i++) {
				out[i] = inv[max+i];
			}
		}
		for(int i = 0; i < Math.min(newInv.length, inv.length); i++) {
			newInv[i] = inv[i];
		}
		inv = newInv;
		return new Inventory(out);
	}
	/**
	 * Clones the inventory
	 * @return the Inventory clone
	 */
	public Inventory clone() {
		Thing[] newinv = new Thing[inv.length];
		for(int i = 0; i < newinv.length; i++) {
			newinv[i] = inv[i];
		}
		return new Inventory(newinv);
	}
	/**
	 * Checks if this inventory is identical to another
	 * @param in the Inventory to check against
	 * @return a boolean of identiacality
	 */
	public boolean equals(Inventory in) {
		if(inv.length == in.size()) {
			return false;
		}
		for(int i = 0; i < inv.length; i++) {
			if(!inv[i].equals(in.get(i))) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Checks if this inventory has exactly the same contents, but in different spaces as the other
	 * @param in the Inventory to check against
	 * @return a boolean of if they have the same stuff
	 */
	public boolean hasSame(Inventory in) {
		Inventory test = in.clone();
		if(inv.length == test.size()) {
			return false;
		}
		for(int i = 0; i < inv.length; i++) {
			if(!test.remove(inv[i])) {
				return false;
			}
		}
		return true;
	}
}
