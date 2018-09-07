package benjaminc.chief_simulator.rooms;

import java.util.ArrayList;
import java.util.List;

import benjaminc.chief_simulator.Objective;
import benjaminc.chief_simulator.Score;
import benjaminc.chief_simulator.control.Cook;
import benjaminc.chief_simulator.graphics.Room;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.building.Counter;
import benjaminc.chief_simulator.things.building.CuttingBoard;
import benjaminc.chief_simulator.things.building.Disposal;
import benjaminc.chief_simulator.things.building.Fryer;
import benjaminc.chief_simulator.things.building.Spawner;
import benjaminc.chief_simulator.things.building.Stove;
import benjaminc.chief_simulator.things.building.Window;
import benjaminc.chief_simulator.things.food.Apple;
import benjaminc.chief_simulator.things.food.Beef;
import benjaminc.chief_simulator.things.food.Bun;
import benjaminc.chief_simulator.things.food.Cheese;
import benjaminc.chief_simulator.things.food.FoodState;
import benjaminc.chief_simulator.things.food.Lettuce;
import benjaminc.chief_simulator.things.food.Potato;
import benjaminc.chief_simulator.things.food.Tomato;

public class Level1 extends Room {
	
	private int cookStart[][] = {{1, 1}, {14, 1}};
	
	public Level1(List<Cook> cooks, Score score) {
		super(16, 16, new Object(), score, cooks); // Change these to width and height!

		for(int i = 0; i < cooks.size(); i++) {
			cooks.get(i).setLocation(cookStart[i%cookStart.length][0], cookStart[i%cookStart.length][1]);
			System.out.println(cooks.get(i));
		}
		
		for(int i = 0; i < getSize().width; i++) {
			addThing(new Counter(), i, 0);
			addThing(new Counter(), i, 15);
		}
		for(int i = 0; i < getSize().height; i++) {
			addThing(new Counter(), 0, i);
			addThing(new Counter(), 15, i);
		}
		
		getSpace(4, 0).removeAll(new Counter()); 
		addThing(new Beef(), 0, 0);
		addThing(new CuttingBoard(), 4, 0);
		addThing(new Counter(), 4, 4);
		addThing(new Counter(), 4, 3);
		addThing(new CuttingBoard(), 5, 3);
		addThing(new CuttingBoard(), 6, 3);
		addThing(new Fryer(), 7, 4);
		addThing(new Counter(), 7, 3);
		addThing(new Stove(), 8, 4);
		addThing(new Counter(), 8, 3);
		addThing(new CuttingBoard(), 9, 3);
		addThing(new CuttingBoard(), 10, 3);
		addThing(new Counter(), 11, 4);
		addThing(new Counter(), 11, 3);
		addThing(new Counter(), 4, 12);
		addThing(new Counter(), 4, 11);
		addThing(new CuttingBoard(), 5, 12);
		addThing(new CuttingBoard(), 6, 12);
		addThing(new Fryer(), 7, 11);
		addThing(new Stove(), 8, 11);
		addThing(new Counter(), 7, 12);
		addThing(new Counter(), 8, 12);
		addThing(new CuttingBoard(), 9, 12);
		addThing(new CuttingBoard(), 10, 12);
		addThing(new Counter(), 11, 12);
		addThing(new Counter(), 11, 11);
		addThing(new Spawner(new Apple()), 7, 0);
		addThing(new Spawner(new Lettuce()), 5, 4);
		addThing(new Spawner(new Tomato()), 6, 4);
		addThing(new Spawner(new Bun()), 9, 4);
		addThing(new Spawner(new Beef()), 10, 4);
		addThing(new Spawner(new Cheese()), 8, 0);
		addThing(new Spawner(new Apple()), 8, 15);
		addThing(new Spawner(new Potato()), 3, 3);
		addThing(new Spawner(new Lettuce()), 5, 11);
		addThing(new Spawner(new Tomato()), 6, 11);
		addThing(new Spawner(new Bun()), 9, 11);
		addThing(new Spawner(new Beef()), 10, 11);
		addThing(new Spawner(new Cheese()), 7, 15);
		addThing(new Disposal(), 15, 1);
		addThing(new Disposal(), 0, 14);
		addThing(new Disposal(), 1, 0);
		addThing(new Disposal(), 14, 15);
		addThing(new Window(this), 14, 0);
		
		List<Thing> toppings = new ArrayList<Thing>();
		toppings.add(new Lettuce(-1, FoodState.CHOPPED));
		toppings.add(new Beef(-1, FoodState.CHOPPED_COOKED));
		toppings.add(new Tomato(-1, FoodState.CHOPPED));
		addObjectives(new Objective(new Bun(toppings), 10));
		List<Thing> toppings2 = new ArrayList<Thing>();
		toppings2.add(new Beef(-1, FoodState.CHOPPED_COOKED));
		toppings2.add(new Cheese());
		addObjectives(new Objective(new Bun(toppings2), 10));
		Bun bun1 = new Bun(toppings);
		Bun bun2 = new Bun(toppings2);
		addObjectives(new Objective(bun1.duplicate(), 10));
		addObjectives(new Objective(bun2.duplicate(), 10));
		addObjectives(new Objective(new Beef(-1, FoodState.COOKED), 5));
		addThing(new Spawner(bun1), 10, 0);
		addThing(new Spawner(bun2), 11, 0);
	}

}
