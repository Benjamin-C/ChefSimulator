package benjaminc.chief_simulator.things.types;

import java.util.List;

import benjaminc.chief_simulator.things.Thing;

public interface ToolThing extends Thing {

	public abstract List<Thing> useTool(Thing t);
	
}
