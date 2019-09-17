package benjaminc.chef_simulator.things.types;

import java.util.List;

import benjaminc.chef_simulator.things.Thing;

public interface ToolThing extends Thing {

	public abstract List<Thing> useTool(Thing t);
	
}
