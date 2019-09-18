package benjaminc.chef_simulator.graphics;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_utils.files.ShapeLoader;

public class GraphicalLoader {

	private static Map<String, Texture> cache;
	
	public static GraphicalThing load(String filename, DataMap data) {
		if(cache == null) {
			cache = new HashMap<String, Texture>();
		}
		String fullname = "assets/textures/" + filename.toLowerCase() + ".cst";
		if(cache.containsKey(fullname)) {
			System.out.println("Loading " + fullname.substring((fullname.lastIndexOf("/"))+1) + " from cache");
			return new GraphicalThing(data, cache.get(fullname));
		} else {
			ShapeLoader sl = new ShapeLoader();
			Texture tx = sl.loadShapeListFromFile(new File(fullname));
			cache.put(fullname, tx);
			return new GraphicalThing(data, tx);
		}
	}
}
