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
import benjaminc.chief_simulator.things.building.Window;
import benjaminc.chief_simulator.things.food.Apple;
import benjaminc.chief_simulator.things.food.Bun;
import benjaminc.chief_simulator.things.food.ChoppedApple;
import benjaminc.chief_simulator.things.food.ChoppedLettuce;
import benjaminc.chief_simulator.things.food.Lettuce;
import benjaminc.chief_simulator.things.food.Patty;
import benjaminc.chief_simulator.things.food.Tomato;

public class Game {

	private GamePanel gamePanel;
	private int roomWidth;
	private int roomHeight;
	private Room map;
	
	private List<Objective> objects;
	
	@SuppressWarnings("unused")
	public Game() {
		roomWidth = 16;
		roomHeight = 16;
		map = new Room(roomWidth, roomHeight);
		gamePanel = new GamePanel(map);
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
		map.addThing(new Apple(), 2, 1);
		map.addThing(new ChoppedApple(), 3, 1);
		map.addThing(new Lettuce(), 5, 7);
		map.addThing(new ChoppedLettuce(), 6, 7);
		for(int i = 0; i < map.getSize().width; i++) {
			map.addThing(new Counter(), i, 0);
		}
		map.getSpace(4, 0).removeAll(new Counter());
		map.addThing(new CuttingBoard(), 4, 0);
		map.addThing(new Spawner(new Apple()), 5, 0);
		map.addThing(new Spawner(new Lettuce()), 6, 0);
		map.addThing(new Spawner(new Tomato()), 7, 0);
		map.addThing(new Spawner(new Bun()), 8, 0);
		map.addThing(new Spawner(new Patty()), 9, 0);
		map.addThing(new Disposal(), 15, 0);
		map.addThing(new Window(), 14, 0);
		
		objects = new ArrayList<Objective>();
		List<Thing> toppings = new ArrayList<Thing>();
		toppings.add(new Lettuce());
		toppings.add(new Patty());
		objects.add(new Objective(new Bun(toppings), 5));
		updateGraphics();
	}
	
	public List<Objective> getObjects() {
		return objects;
	}

	public void setObjects(List<Objective> objects) {
		this.objects = objects;
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
}
