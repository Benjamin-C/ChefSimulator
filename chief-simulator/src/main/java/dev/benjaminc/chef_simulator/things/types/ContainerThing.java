package dev.benjaminc.chef_simulator.things.types;

import dev.benjaminc.chef_simulator.chef_graphics.GraphicalDrawer;
import dev.benjaminc.chef_simulator.data.Inventory;
import dev.benjaminc.chef_simulator.things.Thing;

/**
 * A template for Things with inventories
 * @author Benjamin-C
 *
 */
public interface ContainerThing extends ToolThing {

	/**
	 * Adds t to the inventory of the container
	 * @param t the {@link Thing} to add
	 */
	public abstract void addItem(Thing t);
	/**
	 * Removes t from the inventory of the container
	 * @param t the {@link Thing} to remove
	 */
	public abstract void removeItem(Thing t);
	/**
	 * Gets the Inventory from the container
	 * @return the {@link Inventory}
	 */
	public abstract Inventory getItems();
	/**
	 * Gets a copy of the Inventory from the container, then clears the inventory.
	 * Use {@link ContainerThing#getItems()} to get items without clearing the inventory.
	 * @return a copy of the {@link Inventory}
	 */
	public abstract Inventory giveAllItems();
	/**
	 * Draw the ContainerThing on the screen
	 * @param g the {@link GraphicalDrawer}
	 * @param x the int of the X ofset
	 * @param y the int of the Y ofset
	 * @param w the width to draw the thing
	 * @param h the height to draw the thing
	 */
	public abstract void draw(GraphicalDrawer g, int x, int y, int w, int h);
}
