package benjaminc.chef_simulator.graphics;

import java.io.File;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_utils.files.ShapeLoader;

public class GraphicalLoader {

	public static GraphicalThing load(String name, DataMap data) {
		name = name.toLowerCase();
		ShapeLoader sl = new ShapeLoader();
		return new GraphicalThing(data, sl.loadShapeListFromFile(new File(name)));
//		switch(name.toLowerCase()) {
//		// building
//		case "belt": return new GraphicalBelt(data);
//		case "counter": return new GraphicalCounter(data);
//		case "cuttingboard": return new GraphicalCuttingBoard(data);
//		case "disposal": return new GraphicalDisposal(data);
//		case "floor": return new GraphicalFloor(data);
//		case "fryer": return new GraphicalFryer(data);
//		case "remover": return new GraphicalRemover(data);
//		case "spawner": return new GraphicalSpawner(data);
//		case "stove": return new GraphicalStove(data);
//		case "window": return new GraphicalWindow(data);
//		
//		// food
//		case "apple": return new GraphicalApple(data);
//		case "beef": return new GraphicalBeef(data);
//		case "bun": return new GraphicalBun(data);
//		case "cheese": return new GraphicalCheese(data);
//		case "lettuce": return new GraphicalLettuce(data);
//		case "potato": return new GraphicalPotato(data);
//		case "tomato": return new GraphicalTomato(data);
//		// tools
//		case "pan": return new GraphicalPan(data);
//		case "plate": return new GraphicalPlate(data);
//		}
//		System.err.println("The Graphical type was not found. Please fix this before things will work");
	}
}
