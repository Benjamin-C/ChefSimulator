package benjaminc.chef_simulator.graphics;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_utils.files.ShapeLoader;
import benjaminc.chef_utils.graphics.Texture;

public class GraphicalLoader {

	private static Map<String, Texture> cache;
	
	public static Texture load(String filename) {
		if(cache == null) {
			cache = new HashMap<String, Texture>();
		}
		String fullname = "assets/textures/" + filename.toLowerCase() + ".cst";
		if(cache.containsKey(fullname)) {
			System.out.println("Loading " + fullname.substring((fullname.lastIndexOf("/"))+1) + " from cache");
//			cache.get(fullname).printAll();
			return cache.get(fullname);
		} else {
			ShapeLoader sl = new ShapeLoader();
			Texture tx = sl.loadShapeList(new File(fullname));
			cache.put(fullname, tx);
			if(tx == null) {
				System.out.println("The_Texture_Was_Null_GL");
			}
			return tx;
		}
	}
}
