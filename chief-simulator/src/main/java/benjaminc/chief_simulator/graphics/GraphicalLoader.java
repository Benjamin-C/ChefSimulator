package benjaminc.chief_simulator.graphics;

import benjaminc.chief_simulator.data.DataMap;
import benjaminc.chief_simulator.graphics.building.GraphicalBelt;

public class GraphicalLoader {

	public static GraphicalThing load(String name, DataMap data) {
		switch(name) {
		// building
		case "belt": return new GraphicalBelt(data);
		
		}
		return null;
	}
}
