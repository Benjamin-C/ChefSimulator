package benjaminc.chef_simulator.things.types;

import java.awt.Graphics;

import benjaminc.chef_simulator.data.Inventory;
import benjaminc.chef_simulator.graphics.GraphicalDrawer;
import benjaminc.chef_simulator.things.Thing;

public interface ContainerThing extends ToolThing {

	public abstract void addItem(Thing t);
	public abstract void removeItem(Thing t);
	public abstract Inventory getItems();
	public abstract Inventory giveAllItems();
	public abstract void draw(GraphicalDrawer g, int x, int y, int w, int h);
}
