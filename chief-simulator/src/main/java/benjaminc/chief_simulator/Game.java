package benjaminc.chief_simulator;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.SimpleBindings;

import benjaminc.chief_simulator.control.Cook;
import benjaminc.chief_simulator.control.KeyListenAction;
import benjaminc.chief_simulator.graphics.ActionType;
import benjaminc.chief_simulator.graphics.GamePanel;
import benjaminc.chief_simulator.graphics.Room;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.building.Counter;
import benjaminc.chief_simulator.things.building.CuttingBoard;
import benjaminc.chief_simulator.things.building.Disposal;
import benjaminc.chief_simulator.things.building.Spawner;
import benjaminc.chief_simulator.things.building.Stove;
import benjaminc.chief_simulator.things.building.Window;
import benjaminc.chief_simulator.things.food.Apple;
import benjaminc.chief_simulator.things.food.Bun;
import benjaminc.chief_simulator.things.food.Cheese;
import benjaminc.chief_simulator.things.food.Lettuce;
import benjaminc.chief_simulator.things.food.Beef;
import benjaminc.chief_simulator.things.food.FoodState;
import benjaminc.chief_simulator.things.food.Tomato;

public class Game {

	private GamePanel gamePanel;
	private int roomWidth;
	private int roomHeight;
	private Room map;
	private int score;

	private List<Objective> objectives;
	
	@SuppressWarnings("unused")
	public Game() {
		mapDraw2();
	}
	
	public void mapDraw2() {
		roomWidth = 16;
		roomHeight = 16;
		map = new Room(roomWidth, roomHeight);
		gamePanel = new GamePanel(this, map);
		objectives = new ArrayList<Objective>();
		score = 0;
		
		Map<ActionType, Integer> benKeys = new HashMap<ActionType, Integer>();
		benKeys.put(ActionType.MOVE_UP, KeyEvent.VK_UP);
		benKeys.put(ActionType.MOVE_DOWN, KeyEvent.VK_DOWN);
		benKeys.put(ActionType.MOVE_LEFT, KeyEvent.VK_LEFT);
		benKeys.put(ActionType.MOVE_RIGHT, KeyEvent.VK_RIGHT);
		benKeys.put(ActionType.PICKUP_ITEM, KeyEvent.VK_CONTROL);
		benKeys.put(ActionType.USE_ITEM, KeyEvent.VK_NUMPAD0);
		map.addCook(new Cook(this, "Ben", benKeys, 14, 1));
		
		Map<ActionType, Integer> mattKeys = new HashMap<ActionType, Integer>();
		mattKeys.put(ActionType.MOVE_UP, KeyEvent.VK_W);
		mattKeys.put(ActionType.MOVE_DOWN, KeyEvent.VK_S);
		mattKeys.put(ActionType.MOVE_LEFT, KeyEvent.VK_A);
		mattKeys.put(ActionType.MOVE_RIGHT, KeyEvent.VK_D);
		mattKeys.put(ActionType.PICKUP_ITEM, KeyEvent.VK_Q);
		mattKeys.put(ActionType.USE_ITEM, KeyEvent.VK_E);
		map.addCook(new Cook(this, "Peter", mattKeys, 1, 1));
		
		for(int i = 0; i < map.getSize().width; i++) {
			map.addThing(new Counter(), i, 0);
			map.addThing(new Counter(), i, 15);
		}
		for(int i = 0; i < map.getSize().height; i++) {
			map.addThing(new Counter(), 0, i);
			map.addThing(new Counter(), 15, i);
		}
		map.getSpace(4, 0).removeAll(new Counter());
		map.addThing(new Lettuce(-1, FoodState.CHOPPED), 3, 0);
		map.addThing(new CuttingBoard(), 4, 0);
		map.addThing(new Counter(), 4, 4);
		map.addThing(new Counter(), 4, 3);
		map.addThing(new CuttingBoard(), 5, 3);
		map.addThing(new CuttingBoard(), 6, 3);
		map.addThing(new Stove(), 7, 4);
		map.addThing(new Counter(), 7, 3);
		map.addThing(new Counter(), 8, 4);
		map.addThing(new Counter(), 8, 3);
		map.addThing(new CuttingBoard(), 9, 3);
		map.addThing(new CuttingBoard(), 10, 3);
		map.addThing(new Counter(), 11, 4);
		map.addThing(new Counter(), 11, 3);
		map.addThing(new Counter(), 4, 12);
		map.addThing(new Counter(), 4, 11);
		map.addThing(new CuttingBoard(), 5, 12);
		map.addThing(new CuttingBoard(), 6, 12);
		map.addThing(new Stove(), 7, 12);
		map.addThing(new Counter(), 7, 11);
		map.addThing(new Counter(), 8, 12);
		map.addThing(new Counter(), 8, 11);
		map.addThing(new CuttingBoard(), 9, 12);
		map.addThing(new CuttingBoard(), 10, 12);
		map.addThing(new Counter(), 11, 12);
		map.addThing(new Counter(), 11, 11);
		map.addThing(new Spawner(new Apple()), 7, 0);
		map.addThing(new Spawner(new Lettuce()), 5, 4);
		map.addThing(new Spawner(new Tomato()), 6, 4);
		map.addThing(new Spawner(new Bun()), 9, 4);
		map.addThing(new Spawner(new Beef()), 10, 4);
		map.addThing(new Spawner(new Cheese()), 11, 4);
		map.addThing(new Spawner(new Apple()), 8, 15);
		map.addThing(new Spawner(new Lettuce()), 5, 11);
		map.addThing(new Spawner(new Tomato()), 6, 11);
		map.addThing(new Spawner(new Bun()), 9, 11);
		map.addThing(new Spawner(new Beef()), 10, 11);
		map.addThing(new Spawner(new Cheese()), 11, 11);
		map.addThing(new Disposal(), 15, 1);
		map.addThing(new Disposal(), 0, 14);
		map.addThing(new Disposal(), 1, 0);
		map.addThing(new Disposal(), 14, 15);
		map.addThing(new Window(this), 14, 0);
		
		List<Thing> toppings = new ArrayList<Thing>();
		toppings.add(new Lettuce(-1, FoodState.CHOPPED));
		toppings.add(new Beef(-1, FoodState.CHOPPED_COOKED));
		toppings.add(new Tomato(-1, FoodState.CHOPPED));
		objectives.add(new Objective(new Bun(toppings), 5));
		List<Thing> toppings2 = new ArrayList<Thing>();
		toppings2.add(new Beef(-1, FoodState.CHOPPED_COOKED));
		objectives.add(new Objective(new Bun(toppings2), 5));
		Bun bun1 = new Bun(toppings);
		Bun bun2 = new Bun(toppings2);
		objectives.add(new Objective(bun1.duplicate(), 5));
		objectives.add(new Objective(bun2.duplicate(), 5));
		map.addThing(new Spawner(bun1), 10, 0);
		map.addThing(new Spawner(bun2), 11, 0);
		updateGraphics();
	}
	
	public void mapDraw() {
		roomWidth = 16;
		roomHeight = 16;
		map = new Room(roomWidth, roomHeight);
		gamePanel = new GamePanel(this, map);
		objectives = new ArrayList<Objective>();
		score = 0;
		
		Map<ActionType, Integer> benKeys = new HashMap<ActionType, Integer>();
		benKeys.put(ActionType.MOVE_UP, KeyEvent.VK_UP);
		benKeys.put(ActionType.MOVE_DOWN, KeyEvent.VK_DOWN);
		benKeys.put(ActionType.MOVE_LEFT, KeyEvent.VK_LEFT);
		benKeys.put(ActionType.MOVE_RIGHT, KeyEvent.VK_RIGHT);
		benKeys.put(ActionType.PICKUP_ITEM, KeyEvent.VK_CONTROL);
		benKeys.put(ActionType.USE_ITEM, KeyEvent.VK_NUMPAD0);
		map.addCook(new Cook(this, "Ben", benKeys));
		
		Map<ActionType, Integer> mattKeys = new HashMap<ActionType, Integer>();
		mattKeys.put(ActionType.MOVE_UP, KeyEvent.VK_W);
		mattKeys.put(ActionType.MOVE_DOWN, KeyEvent.VK_S);
		mattKeys.put(ActionType.MOVE_LEFT, KeyEvent.VK_A);
		mattKeys.put(ActionType.MOVE_RIGHT, KeyEvent.VK_D);
		mattKeys.put(ActionType.PICKUP_ITEM, KeyEvent.VK_Q);
		mattKeys.put(ActionType.USE_ITEM, KeyEvent.VK_E);
		map.addCook(new Cook(this, "Peter", mattKeys));
		
		for(int i = 0; i < map.getSize().width; i++) {
			map.addThing(new Counter(), i, 0);
		}
		map.addThing(new Apple(), 2, 0);
		map.addThing(new Apple(-1, FoodState.CHOPPED), 3, 0);
		map.addThing(new Lettuce(), 11, 0);
		map.addThing(new Lettuce(-1, FoodState.CHOPPED), 12, 0);
		map.getSpace(4, 0).removeAll(new Counter());
		map.addThing(new CuttingBoard(), 4, 0);
		map.addThing(new CuttingBoard(), 4, 3);	
		map.addThing(new Counter(), 4, 4);
		map.addThing(new CuttingBoard(), 5, 3);
		map.addThing(new Counter(), 5, 4);
		map.addThing(new CuttingBoard(), 6, 3);
		map.addThing(new Counter(), 6, 4);
		map.addThing(new Spawner(new Apple()), 5, 0);
		map.addThing(new Spawner(new Lettuce()), 6, 0);
		map.addThing(new Spawner(new Tomato()), 7, 0);
		map.addThing(new Spawner(new Bun()), 8, 0);
		map.addThing(new Spawner(new Beef()), 9, 0);
		map.addThing(new Disposal(), 15, 0);
		map.addThing(new Window(this), 14, 0);
		
		List<Thing> toppings = new ArrayList<Thing>();
		toppings.add(new Lettuce());
		toppings.add(new Beef(-1, FoodState.COOKED));
		objectives.add(new Objective(new Bun(toppings), 5));
		//toppings.add(new Patty());
		Bun bun1 = new Bun(toppings);
		Bun bun2 = new Bun(toppings);
		objectives.add(new Objective(bun1.duplicate(), 5));
		objectives.add(new Objective(bun2.duplicate(), 5));
		map.addThing(new Spawner(bun1), 10, 0);
		//map.addThing(new Spawner(bun1), 11, 0);
		updateGraphics();
	}
	
	public List<Objective> getObjectives() {
		return objectives;
	}

	public void setObjects(List<Objective> objectives) {
		this.objectives = objectives;
	}

	public Room getRoom() {
		return map;
	}
	public void updateGraphics() {
		gamePanel.update();
	}
	
	public void registerKeylistener(KeyListenAction a) {
		gamePanel.addKeyListener(a);
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void addScore(int score) {
		this.score = this.score + score;
	}
}
