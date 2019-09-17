package benjaminc.chef_simulator.graphics;

import java.io.File;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_utils.files.ShapeLoader;

public class GraphicalLoader {

	public static GraphicalThing load(String name, DataMap data) {
		name = name.toLowerCase();
		ShapeLoader sl = new ShapeLoader();
		return new GraphicalThing(data, sl.loadShapeListFromFile(new File("assets/textures/" + name + ".cst")));
	}
}
