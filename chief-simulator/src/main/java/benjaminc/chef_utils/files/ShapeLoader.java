package benjaminc.chef_utils.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import benjaminc.chef_utils.graphics.Texture;
import benjaminc.chef_simulator.data.FoodState;
import benjaminc.chef_utils.graphics.Shape;
import benjaminc.chef_utils.graphics.ShapeType;

public class ShapeLoader {
	
	public ShapeLoader() {
		
	}
	//  Methods
	/**
	 * Turns a JSON string into a Shape
	 * Must recive JSON in format {"key":"value","key":"value"}
	 * EX: {"shapetype":"solid_rectangle","x":"0.0","y":"0.0","w":"0.0","h":"0.0","r":"0","g":"0","b":"0"}
	 * 
	 * @param json the String JSON to interpert
	 * @return the Shape the JSON represents
	 */
	
	public Texture loadShapeList(File file) {
		Texture list = new Texture();
/*>*/	System.out.println("Trying " + file.getName());
		if(file.exists()) {
/*>*/		System.out.println("It exists");
			list = loadShapeListFromFile(file);
		} else {
/*>*/		System.out.println("Trying as directory");
			File dir = new File(file.getPath().substring(0, file.getPath().lastIndexOf(".")));
			System.out.println("Test path = " + dir.getPath());
			if(dir.isDirectory()) {
				System.out.println("WARNING: Loading direcotyr is depricated. Please update to single texture files");
				System.out.println("Loading directory: " + dir.getName() + " ...");
				
				File[] listOfFiles = dir.listFiles();
/*>*/			System.out.println("Trying files in directory");
				for (File f : listOfFiles) {
					if (f.isFile()) {
						FoodState key = FoodState.valueOf(f.getName().substring(0, f.getName().lastIndexOf(".")).toUpperCase());
						list.put(key, loadShapeList(f).get(FoodState.RAW));
					} else if (f.isDirectory()) {
						System.out.println("Directory " + f.getName());
					}
				}
			} else {
				System.out.println("Not directory :(");
			}
		}
		return list;
	}
	
	public Texture loadShapeListFromFile(File file) {
		Texture list = new Texture();
		System.out.println("Loading: " + file.getName() + " ... ");
    	try {
			Scanner s = new Scanner(file);
			ShapeLoader sload = new ShapeLoader();
			List<Shape> txtr = new ArrayList<Shape>();
			FoodState state = FoodState.RAW;
			int count = 0;
			while(s.hasNextLine()) {
				String line = s.nextLine();
				count++;
				if(line.length() == 0) {
					// Line is empty, should ignore it
				} else if(line.charAt(line.length()-1) == ':') {
					// Line is a FoodState
					List<Shape> tmpshplst = new ArrayList<Shape>();
					for(Shape tmpss : txtr) {
						tmpshplst.add(tmpss.clone());
					}
					list.put(state, tmpshplst);
					txtr.clear();
					state = FoodState.valueOf(line.substring(0, line.lastIndexOf(":")));
				} else if(line.charAt(0) == '{') {
					// Probibly json?
					txtr.add(sload.getShapeFromJSON(line));
				} else {
					// Line is jibberish
					System.out.println("Could not read line #" + count + ": " + line);
				}
				
			}
			list.put(state, txtr);
			s.close();
			System.out.println("Done loading " + file.getName());
		} catch (FileNotFoundException e) {
			System.out.println("File " + file.getPath() + " not found.");
		}
		return list;
	}
	
	public Shape getShapeFromJSON(String json) {
		// {"shapetype":"solid_rectangle","x":"0.0","y":"0.0","w":"0.0","h":"0.0","r":"0","g":"0","b":"0"}
		
		if(json.charAt(0) == '{' && json.charAt(json.length()-1) == '}' && json.length() > 2) {
			json = json.substring(1,json.length()-1);
			String bitsStr[] = json.split(",");
			Map<String, String> bits = new HashMap<String, String>();
			for(String s : bitsStr) {
				String parts[] = s.split(":");
				if(parts.length == 2
						&& parts[0].charAt(0) == '\"' && parts[0].charAt(parts[0].length()-1) == '\"'
						&& parts[1].charAt(0) == '\"' && parts[1].charAt(parts[1].length()-1) == '\"')
				{
					bits.put(parts[0].substring(1, parts[0].length()-1), parts[1].substring(1, parts[1].length()-1));
				} else {
					System.out.println("Error loading " + s + ". Skipping.");
				}
			}
			
			float nx = getFloatValue(ShapeDataKey.X_KEY, bits);
			float ny = getFloatValue(ShapeDataKey.Y_KEY, bits);
			float nw = getFloatValue(ShapeDataKey.W_KEY, bits);
			float nh = getFloatValue(ShapeDataKey.H_KEY, bits);
			float nc = getFloatValue(ShapeDataKey.C_KEY, bits);
			int nr = getColorValue(ShapeDataKey.R_KEY, bits);
			int ng = getColorValue(ShapeDataKey.G_KEY, bits);
			int nb = getColorValue(ShapeDataKey.B_KEY, bits);
			ShapeType nt = ShapeType.SOLID_RECTANTLE;
			if(bits.containsKey(ShapeDataKey.SHPAETYPE_KEY)) {
				nt = ShapeType.valueOf(bits.get(ShapeDataKey.SHPAETYPE_KEY).toUpperCase());
				if(nt == null) { nt = ShapeType.SOLID_RECTANTLE; System.out.println("Could not load " + ShapeDataKey.SHPAETYPE_KEY + " (ShapeType " + bits.get(ShapeDataKey.SHPAETYPE_KEY) + " is not valid). Returning default value SOLID_RECTANGLE"); }
			} else { System.out.println("Could not load " + ShapeDataKey.SHPAETYPE_KEY + " (Key not found). Returning default value SOLID_RECTANGLE"); }
			Shape back = new Shape(nt, nx, ny, nw, nh, nc, nr, ng, nb);
			return back;
		} else {
			System.out.println("Error loading JSON.\nIt may be missing curley brackets\nor data. Skipping. Error JSON:\n" + json);
		}
		return null;
	}
	
	private int getColorValue(String key, Map<String, String> map) {
		return Math.min(Math.max(getIntValue(key, map), 0), 255);
	}
	private int getIntValue(String key, Map<String, String> map) {
		if(map.containsKey(key)) {
			try {
				return Integer.parseInt(map.get(key));
			} catch(NumberFormatException e) {
				System.out.println("Could not load " + key + " (NumberFormatException). Returning default value 0");
			}
		} else {
			System.out.println("Could not load " + key + " (Key not found). Returning default value 0");
		}
		return 0;
	}
	private float getFloatValue(String key, Map<String, String> map) {
		if(map.containsKey(key)) {
			try {
				return Float.parseFloat(map.get(key));
			} catch(NumberFormatException e) {
				System.out.println("Could not load " + key + " (NumberFormatException). Returning default value 0");
			}
		} else {
			System.out.println("Could not load " + key + " (Key not found). Returning default value 0");
		}
		return 0;
	}

}
