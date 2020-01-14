package benjaminc.chef_simulator.rooms;

import java.util.ArrayList;
import java.util.List;

import benjaminc.chef_simulator.Objective;
import benjaminc.chef_simulator.Score;
import benjaminc.chef_simulator.control.Chef;
import benjaminc.chef_simulator.control.Direction;
import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.data.FoodState;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.building.Belt;
import benjaminc.chef_simulator.things.building.Button;
import benjaminc.chef_simulator.things.building.Counter;
import benjaminc.chef_simulator.things.building.CuttingBoard;
import benjaminc.chef_simulator.things.building.DishCounter;
import benjaminc.chef_simulator.things.building.Disposal;
import benjaminc.chef_simulator.things.building.Fryer;
import benjaminc.chef_simulator.things.building.Remover;
import benjaminc.chef_simulator.things.building.DishReturn;
import benjaminc.chef_simulator.things.building.Dishwasher;
import benjaminc.chef_simulator.things.building.Spawner;
import benjaminc.chef_simulator.things.building.Stove;
import benjaminc.chef_simulator.things.building.Window;
import benjaminc.chef_simulator.things.food.Apple;
import benjaminc.chef_simulator.things.food.Beef;
import benjaminc.chef_simulator.things.food.Bun;
import benjaminc.chef_simulator.things.food.Cheese;
import benjaminc.chef_simulator.things.food.Lettuce;
import benjaminc.chef_simulator.things.food.Potato;
import benjaminc.chef_simulator.things.food.Tomato;
import benjaminc.chef_simulator.things.tools.Dishbin;
import benjaminc.chef_simulator.things.tools.Pan;
import benjaminc.chef_simulator.things.tools.Plate;

public class Level1 extends Room {
	
	private int cookStart[][] = {{1, 1}, {14, 1}};
	
	public Level1(List<Chef> cooks, Score score) {
		super(16, 16, new Object(), cooks); // Change these to width and height!

		for(int i = 0; i < cooks.size(); i++) {
			cooks.get(i).setLocation(new Location(cookStart[i%cookStart.length][0], cookStart[i%cookStart.length][1]));
		}
		
		for(int i = 0; i < getSize().width; i++) {
			addThing(new Counter(), new Location(i, 0));
			addThing(new Counter(), new Location(i, 15));
		}
		for(int i = 0; i < getSize().height; i++) {
			addThing(new Counter(), new Location(0, i));
			addThing(new Counter(), new Location(15, i));
		}
		
		getSpace(new Location(4, 0)).removeAll(new Counter());
		addThing(new Remover(), new Location(15, 8));
		addThing(new Beef(0, FoodState.COOKED), new Location(1, 2));
		addThing(new Beef(), new Location(0, 0));
		addThing(new CuttingBoard(), new Location(4, 0));
		addThing(new Counter(), new Location(4, 4));
		addThing(new Counter(), new Location(4, 3));
		addThing(new CuttingBoard(), new Location(5, 3));
		addThing(new CuttingBoard(), new Location(6, 3));
		addThing(new Counter(), new Location(7, 3));
		addThing(new Stove(), new Location(7, 4));
		addThing(new Counter(), new Location(8, 3));
		addThing(new Pan(), new Location(8, 3));
		addThing(new Fryer(), new Location(8, 4));
		addThing(new CuttingBoard(), new Location(9, 3));
		addThing(new CuttingBoard(), new Location(10, 3));
		addThing(new Counter(), new Location(11, 4));
		addThing(new Counter(), new Location(11, 3));
		addThing(new Counter(), new Location(4, 12));
		addThing(new Counter(), new Location(4, 11));
		addThing(new CuttingBoard(), new Location(5, 12));
		addThing(new CuttingBoard(), new Location(6, 12));
		addThing(new Fryer(), new Location(7, 11));
		addThing(new Stove(), new Location(8, 11));
		
		addThing(new CuttingBoard(), new Location(9, 12));
		addThing(new CuttingBoard(), new Location(10, 12));
		addThing(new Counter(), new Location(11, 12));
		addThing(new Counter(), new Location(11, 11));
		addThing(new Spawner(new Apple()), new Location(7, 0));
		addThing(new Spawner(new Lettuce()), new Location(5, 4));
		addThing(new Spawner(new Tomato()), new Location(6, 4));
		addThing(new Spawner(new Bun()), new Location(9, 4));
		addThing(new Spawner(new Beef()), new Location(10, 4));
		addThing(new Spawner(new Cheese()), new Location(8, 0));
		addThing(new Spawner(new Apple()), new Location(8, 15));
		addThing(new Spawner(new Potato()), new Location(3, 3));
		addThing(new Spawner(new Lettuce()), new Location(5, 11));
		addThing(new Spawner(new Tomato()), new Location(6, 11));
		addThing(new Spawner(new Bun()), new Location(9, 11));
		addThing(new Spawner(new Beef()), new Location(10, 11));
		addThing(new Spawner(new Cheese()), new Location(7, 15));
		addThing(new Disposal(), new Location(15, 1));
		addThing(new Disposal(), new Location(0, 14));
		addThing(new Disposal(), new Location(1, 0));
		addThing(new Disposal(), new Location(14, 15));
		addThing(new Belt(Direction.DOWN), new Location(14, 14));
		addThing(new Belt(Direction.DOWN), new Location(14, 13));
		addThing(new Belt(Direction.DOWN), new Location(14, 12));
		addThing(new Belt(Direction.DOWN), new Location(14, 11));
		addThing(new Window(), new Location(14, 0));
		addThing(new Belt(Direction.RIGHT), new Location(3, 1));
		addThing(new Belt(Direction.RIGHT), new Location(4, 1));
		addThing(new Belt(Direction.RIGHT), new Location(5, 1));
		addThing(new Belt(Direction.RIGHT), new Location(6, 1));
		addThing(new Belt(Direction.DOWN), new Location(7, 1));
		addThing(new Belt(Direction.UP), new Location(3, 2));
		addThing(new Belt(Direction.LEFT), new Location(4, 2));
		addThing(new Belt(Direction.LEFT), new Location(5, 2));
		addThing(new Belt(Direction.LEFT), new Location(6, 2));
		addThing(new Belt(Direction.LEFT), new Location(7, 2));
		
		addThing(new DishCounter(Direction.DOWN, FoodState.CHOPPED), new Location(0, 4));
		addThing(new DishCounter(), new Location(0, 5));
		addThing(new Dishwasher(Direction.DOWN), new Location(0, 5));
		addThing(new DishCounter(), new Location(0, 6));
		addThing(new DishCounter(), new Location(0, 7));
		addThing(new DishReturn(), new Location(0, 8));
		addThing(new Dishbin(), new Location(0, 9));
		
		addThing(new Button(), new Location(15, 5));
		addThing(new Button() {
			boolean toFreeze = false;
			@Override public void click() {
				toFreeze = true;
			}
			@Override
			public void tick(Room r, Location l, double f) {
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
		}, new Location(15, 6));
		
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
		
		addThing(new Spawner(bun1), new Location(10, 0));
		addThing(new Spawner(bun2), new Location(11, 0));
		
		Plate pl = new Plate(bun1.clone());
		addThing(new Spawner(pl), new Location(7, 8));
		addThing(new Spawner(new Plate()), new Location(7, 12));
		addThing(new Spawner(new Plate()), new Location(8, 12));
		addThing(new Spawner(new Plate()), new Location(8, 8));
		
		
	}

}
