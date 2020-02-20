package dev.benjaminc.chef_simulator.graphics;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import dev.benjaminc.chef_utils.files.ShapeLoader;
import dev.benjaminc.chef_utils.graphics.Texture;

public class GraphicalLoader {

	private static Map<String, Texture> cache;
	
	public static void loadCache(String root) {
		if(cache == null) {
			cache = new HashMap<String, Texture>();
		}
		
		System.out.println("Loading cache");
		System.out.println(root);
		
		ShapeLoader sl = new ShapeLoader();
		
		cacheFile(new File(root), sl);
		
		System.out.println("Start cache dump:");
		for(String s : cache.keySet()) {
			System.out.println(s);
		}
		System.out.println("End cache dump");
	}
	private static void cacheFile(File f, ShapeLoader sl) {
		System.out.println(f.getName());
		if(f.isDirectory()) {
			File[] listOfFiles = f.listFiles();
			for(File fi : listOfFiles) {
				cacheFile(fi, sl);
			}
		} else {
			Texture tx = sl.loadShapeList(f);
			System.out.println(f.getName());
			cache.put(f.getName(), tx);
		}
	}
	
	public static Texture load(String filename) {
		if(cache == null) {
			cache = new HashMap<String, Texture>();
		}
		String fullname = "assets/textures/" + filename.toLowerCase() + ".cst";
		if(cache.containsKey(filename)) {
			//System.out.println("Loading " + filename/*.substring((fullname.lastIndexOf("/"))+1)*/ + " from cache");
			return cache.get(filename);
		} else {
			ShapeLoader sl = new ShapeLoader();
			Texture tx = sl.loadShapeList(new File(fullname));
			cache.put(filename, tx);
			if(tx == null) {
				System.out.println("The_Texture_Was_Null_GL");
			}
			return tx;
		}
	}
}
