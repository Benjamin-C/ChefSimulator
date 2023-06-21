package dev.orangeben.chef_simulator.rooms;

import java.util.ArrayList;
import java.util.List;

import dev.orangeben.chef_simulator.Objective;
import dev.orangeben.chef_simulator.Score;
import dev.orangeben.chef_simulator.control.Chef;
import dev.orangeben.chef_simulator.control.Direction;
import dev.orangeben.chef_simulator.data.FoodState;
import dev.orangeben.chef_simulator.data.location.Location2d;
import dev.orangeben.chef_simulator.things.Thing;
import dev.orangeben.chef_simulator.things.building.Belt;
import dev.orangeben.chef_simulator.things.building.Button;
import dev.orangeben.chef_simulator.things.building.Counter;
import dev.orangeben.chef_simulator.things.building.CuttingBoard;
import dev.orangeben.chef_simulator.things.building.DishCounter;
import dev.orangeben.chef_simulator.things.building.DishReturn;
import dev.orangeben.chef_simulator.things.building.Dishwasher;
import dev.orangeben.chef_simulator.things.building.Disposal;
import dev.orangeben.chef_simulator.things.building.Fryer;
import dev.orangeben.chef_simulator.things.building.Remover;
import dev.orangeben.chef_simulator.things.building.Spawner;
import dev.orangeben.chef_simulator.things.building.Stove;
import dev.orangeben.chef_simulator.things.building.Window;
import dev.orangeben.chef_simulator.things.food.Apple;
import dev.orangeben.chef_simulator.things.food.Beef;
import dev.orangeben.chef_simulator.things.food.Bun;
import dev.orangeben.chef_simulator.things.food.Cheese;
import dev.orangeben.chef_simulator.things.food.Lettuce;
import dev.orangeben.chef_simulator.things.food.Potato;
import dev.orangeben.chef_simulator.things.food.Tomato;
import dev.orangeben.chef_simulator.things.tools.Dishbin;
import dev.orangeben.chef_simulator.things.tools.Pan;
import dev.orangeben.chef_simulator.things.tools.Plate;

public class Level1 extends Room {
	
	private int cookStart[][] = {{1, 1}, {14, 1}};
	
	public Level1(List<Chef> cooks, Score score) {
		super(16, 16, new Object(), cooks); // Change these to width and height!

		for(int i = 0; i < cooks.size(); i++) {
			cooks.get(i).setLocation(new Location2d(cookStart[i%cookStart.length][0], cookStart[i%cookStart.length][1]));
		}
		
		for(int i = 0; i < getSize().width; i++) {
			addThing(new Counter(), new Location2d(i, 0));
			addThing(new Counter(), new Location2d(i, 15));
		}
		for(int i = 0; i < getSize().height; i++) {
			addThing(new Counter(), new Location2d(0, i));
			addThing(new Counter(), new Location2d(15, i));
		}
		
		getSpace(new Location2d(4, 0)).removeAll(new Counter());
		addThing(new Remover(), new Location2d(15, 8));
		addThing(new Beef(0, FoodState.COOKED), new Location2d(1, 2));
		addThing(new Beef(), new Location2d(0, 0));
		addThing(new CuttingBoard(), new Location2d(4, 0));
		addThing(new Counter(), new Location2d(4, 4));
		addThing(new Counter(), new Location2d(4, 3));
		addThing(new CuttingBoard(), new Location2d(5, 3));
		addThing(new CuttingBoard(), new Location2d(6, 3));
		addThing(new Counter(), new Location2d(7, 3));
		addThing(new Stove(), new Location2d(7, 4));
		addThing(new Counter(), new Location2d(8, 3));
		addThing(new Pan(), new Location2d(8, 3));
		addThing(new Fryer(), new Location2d(8, 4));
		addThing(new CuttingBoard(), new Location2d(9, 3));
		addThing(new CuttingBoard(), new Location2d(10, 3));
		addThing(new Counter(), new Location2d(11, 4));
		addThing(new Counter(), new Location2d(11, 3));
		addThing(new Counter(), new Location2d(4, 12));
		addThing(new Counter(), new Location2d(4, 11));
		addThing(new CuttingBoard(), new Location2d(5, 12));
		addThing(new CuttingBoard(), new Location2d(6, 12));
		addThing(new Fryer(), new Location2d(7, 11));
		addThing(new Stove(), new Location2d(8, 11));
		
		addThing(new CuttingBoard(), new Location2d(9, 12));
		addThing(new CuttingBoard(), new Location2d(10, 12));
		addThing(new Counter(), new Location2d(11, 12));
		addThing(new Counter(), new Location2d(11, 11));
		addThing(new Spawner(new Apple()), new Location2d(7, 0));
		addThing(new Spawner(new Lettuce()), new Location2d(5, 4));
		addThing(new Spawner(new Tomato()), new Location2d(6, 4));
		addThing(new Spawner(new Bun()), new Location2d(9, 4));
		addThing(new Spawner(new Beef()), new Location2d(10, 4));
		addThing(new Spawner(new Cheese()), new Location2d(8, 0));
		addThing(new Spawner(new Apple()), new Location2d(8, 15));
		addThing(new Spawner(new Potato()), new Location2d(3, 3));
		addThing(new Spawner(new Lettuce()), new Location2d(5, 11));
		addThing(new Spawner(new Tomato()), new Location2d(6, 11));
		addThing(new Spawner(new Bun()), new Location2d(9, 11));
		addThing(new Spawner(new Beef()), new Location2d(10, 11));
		addThing(new Spawner(new Cheese()), new Location2d(7, 15));
		addThing(new Disposal(), new Location2d(15, 1));
		addThing(new Disposal(), new Location2d(0, 14));
		addThing(new Disposal(), new Location2d(1, 0));
		addThing(new Disposal(), new Location2d(14, 15));
		addThing(new Belt(Direction.DOWN), new Location2d(14, 14));
		addThing(new Belt(Direction.DOWN), new Location2d(14, 13));
		addThing(new Belt(Direction.DOWN), new Location2d(14, 12));
		addThing(new Belt(Direction.DOWN), new Location2d(14, 11));
		addThing(new Window(), new Location2d(14, 0));
		addThing(new Belt(Direction.RIGHT), new Location2d(3, 1));
		addThing(new Belt(Direction.RIGHT), new Location2d(4, 1));
		addThing(new Belt(Direction.RIGHT), new Location2d(5, 1));
		addThing(new Belt(Direction.RIGHT), new Location2d(6, 1));
		addThing(new Belt(Direction.DOWN), new Location2d(7, 1));
		addThing(new Belt(Direction.UP), new Location2d(3, 2));
		addThing(new Belt(Direction.LEFT), new Location2d(4, 2));
		addThing(new Belt(Direction.LEFT), new Location2d(5, 2));
		addThing(new Belt(Direction.LEFT), new Location2d(6, 2));
		addThing(new Belt(Direction.LEFT), new Location2d(7, 2));
		
		addThing(new DishCounter(Direction.DOWN, FoodState.CHOPPED), new Location2d(0, 4));
		addThing(new DishCounter(), new Location2d(0, 5));
		addThing(new Dishwasher(Direction.DOWN), new Location2d(0, 5));
		addThing(new DishCounter(), new Location2d(0, 6));
		addThing(new DishCounter(), new Location2d(0, 7));
		addThing(new DishReturn(), new Location2d(0, 8));
		addThing(new Dishbin(), new Location2d(0, 9));
		
		addThing(new Button(), new Location2d(15, 5));
		addThing(new Button() {
			boolean toFreeze = false;
			@Override public void click() {
				toFreeze = true;
			}
			@Override
			public void tick(Room r, Location2d l, double f) {
				// TODO Auto-generated method stub
				if(toFreeze) {
					toFreeze = false;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}, new Location2d(15, 6));
		
		List<Thing> toppings = new ArrayList<Thing>();
		toppings.add(new Lettuce(-1, FoodState.CHOPPED));
		toppings.add(new Beef(-1, FoodState.CHOPPED_COOKED));
		toppings.add(new Tomato(-1, FoodState.CHOPPED));
		addObjectives(new Objective(new Plate(new Bun(toppings)), 10));
		
		List<Thing> toppings2 = new ArrayList<Thing>();
		toppings2.add(new Beef(-1, FoodState.CHOPPED_COOKED));
		toppings2.add(new Cheese());
		Bun bun = new Bun(toppings2);
		System.out.println("ObjectiveObject " + bun);
		addObjectives(new Objective(new Plate(bun), 10));
		
		Bun bun1 = new Bun(toppings);
		Bun bun2 = new Bun(toppings2);
		System.out.println("Bun1 " + bun1);
		System.out.println("Bun1.clone " + bun1.clone());
		addObjectives(new Objective(new Plate(bun1.clone()), 10));
		addObjectives(new Objective(new Plate(bun2.clone()), 10));
		addObjectives(new Objective(new Plate(new Beef(-1, FoodState.COOKED)), 5));
		
		addThing(new Spawner(bun1), new Location2d(10, 0));
		addThing(new Spawner(bun2), new Location2d(11, 0));
		
		Plate pl = new Plate(bun1.clone());
		addThing(new Spawner(pl), new Location2d(7, 8));
		addThing(new Spawner(new Plate()), new Location2d(7, 12));
		addThing(new Spawner(new Plate()), new Location2d(8, 12));
		addThing(new Spawner(new Plate()), new Location2d(8, 8));
		
		
	}

}
