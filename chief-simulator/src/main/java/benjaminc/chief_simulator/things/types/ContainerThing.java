package benjaminc.chief_simulator.things.types;

import java.util.List;

import benjaminc.chief_simulator.things.Thing;

public interface ContainerThing extends ToolThing {

	public abstract void addItem(Thing t);
	public abstract void removeItem(Thing t);
	public abstract List<Thing> getItems();
	
}
