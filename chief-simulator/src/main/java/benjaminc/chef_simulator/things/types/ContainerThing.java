package benjaminc.chef_simulator.things.types;

import benjaminc.chef_simulator.data.Inventory;
import benjaminc.chef_simulator.things.Thing;

public interface ContainerThing extends ToolThing {

	public abstract void addItem(Thing t);
	public abstract void removeItem(Thing t);
	public abstract Inventory getItems();
	public abstract Inventory giveAllItems();
}
