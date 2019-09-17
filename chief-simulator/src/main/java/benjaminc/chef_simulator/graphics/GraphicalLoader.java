package benjaminc.chef_simulator.graphics;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.graphics.building.GraphicalBelt;
import benjaminc.chef_simulator.graphics.building.GraphicalCounter;
import benjaminc.chef_simulator.graphics.building.GraphicalCuttingBoard;
import benjaminc.chef_simulator.graphics.building.GraphicalDisposal;
import benjaminc.chef_simulator.graphics.building.GraphicalFloor;
import benjaminc.chef_simulator.graphics.building.GraphicalFryer;
import benjaminc.chef_simulator.graphics.building.GraphicalRemover;
import benjaminc.chef_simulator.graphics.building.GraphicalSpawner;
import benjaminc.chef_simulator.graphics.building.GraphicalStove;
import benjaminc.chef_simulator.graphics.building.GraphicalWindow;
import benjaminc.chef_simulator.graphics.food.GraphicalApple;
import benjaminc.chef_simulator.graphics.food.GraphicalBeef;
import benjaminc.chef_simulator.graphics.food.GraphicalBun;
import benjaminc.chef_simulator.graphics.food.GraphicalCheese;
import benjaminc.chef_simulator.graphics.food.GraphicalLettuce;
import benjaminc.chef_simulator.graphics.food.GraphicalPotato;
import benjaminc.chef_simulator.graphics.food.GraphicalTomato;
import benjaminc.chef_simulator.graphics.tools.GraphicalPan;
import benjaminc.chef_simulator.graphics.tools.GraphicalPlate;

public class GraphicalLoader {

	public static GraphicalThing load(String name, DataMap data) {
		switch(name.toLowerCase()) {
		// building
		case "belt": return new GraphicalBelt(data);
		case "counter": return new GraphicalCounter(data);
		case "cuttingboard": return new GraphicalCuttingBoard(data);
		case "disposal": return new GraphicalDisposal(data);
		case "floor": return new GraphicalFloor(data);
		case "fryer": return new GraphicalFryer(data);
		case "remover": return new GraphicalRemover(data);
		case "spawner": return new GraphicalSpawner(data);
		case "stove": return new GraphicalStove(data);
		case "window": return new GraphicalWindow(data);
		
		// food
		case "apple": return new GraphicalApple(data);
		case "beef": return new GraphicalBeef(data);
		case "bun": return new GraphicalBun(data);
		case "cheese": return new GraphicalCheese(data);
		case "lettuce": return new GraphicalLettuce(data);
		case "potato": return new GraphicalPotato(data);
		case "tomato": return new GraphicalTomato(data);
		// tools
		case "pan": return new GraphicalPan(data);
		case "plate": return new GraphicalPlate(data);
		}
		System.err.println("The Graphical type was not found. Please fix this before things will work");
		return null;
	}
}
