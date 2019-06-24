package benjaminc.chief_simulator.data;

import benjaminc.chief_simulator.things.Thing;

public class Inventory {
	
	protected Thing[] inv;
	
	public Inventory(Thing[] inv) {
		this.inv = inv;
	}
	public Inventory(int maxSize) {
		inv = new Thing[maxSize];
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
	}
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
	 * Gets a snapshot of the contents of the inventory
	 * @return a snapshot List<Thing> of the inventory
	 */
	public Thing[] getAll() {
		return clone().inv;
	}
	/**
	 * Returns the max size of the inventory
	 * @return the int max size
	 */
	public int size() {
		return inv.length - 1;
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
}
