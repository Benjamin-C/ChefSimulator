package benjaminc.chief_simulator;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import benjaminc.chief_simulator.control.Cook;
import benjaminc.chief_simulator.control.KeyListenAction;
import benjaminc.chief_simulator.control.Location;
import benjaminc.chief_simulator.control.TickTimer;
import benjaminc.chief_simulator.graphics.ActionType;
import benjaminc.chief_simulator.graphics.GamePanel;
import benjaminc.chief_simulator.graphics.Room;
import benjaminc.chief_simulator.rooms.Level1;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.building.Counter;
import benjaminc.chief_simulator.things.building.CuttingBoard;
import benjaminc.chief_simulator.things.building.Disposal;
import benjaminc.chief_simulator.things.building.Spawner;
import benjaminc.chief_simulator.things.food.Apple;
import benjaminc.chief_simulator.things.food.Bun;
import benjaminc.chief_simulator.things.food.Lettuce;
import benjaminc.chief_simulator.things.food.Beef;
import benjaminc.chief_simulator.things.food.FoodState;
import benjaminc.chief_simulator.things.food.Tomato;
import benjaminc.util.Util;

public class Game {

	private GamePanel gamePanel;
	private int roomWidth;
	private int roomHeight;
	private Room map;
	private Score score;
	private Object roomSync;
	
	private double observedFps;
	
	private int tps = 6;
	
	// This is used, it just doesn't appear to be to Eclipse
	@SuppressWarnings("unused")
	private TickTimer tickTimer;
	List<Cook> cooks;
	
	public Game() {
		this(40, 30, false);
	}
	public Game(int scale, int fps, boolean lago) {
		tps = fps;
		
		final Game thisGame = this;
		cooks = new ArrayList<Cook>();
		score = new Score();
		map = new Room(1, 1, this, new Object(), score, cooks);
		gamePanel = new GamePanel(thisGame, map, scale, lago, fps);
		Map<ActionType, Integer> benKeys = new HashMap<ActionType, Integer>();
		benKeys.put(ActionType.MOVE_UP, KeyEvent.VK_UP);
		benKeys.put(ActionType.MOVE_DOWN, KeyEvent.VK_DOWN);
		benKeys.put(ActionType.MOVE_LEFT, KeyEvent.VK_LEFT);
		benKeys.put(ActionType.MOVE_RIGHT, KeyEvent.VK_RIGHT);
		benKeys.put(ActionType.PICKUP_ITEM, KeyEvent.VK_CONTROL);
		benKeys.put(ActionType.USE_ITEM, KeyEvent.VK_NUMPAD0);
		cooks.add(new Cook(thisGame, "Ben", new Color(255, 128, 0), benKeys, new Location(14, 1)));
		
		Map<ActionType, Integer> mattKeys = new HashMap<ActionType, Integer>();
		mattKeys.put(ActionType.MOVE_UP, KeyEvent.VK_W);
		mattKeys.put(ActionType.MOVE_DOWN, KeyEvent.VK_S);
		mattKeys.put(ActionType.MOVE_LEFT, KeyEvent.VK_A);
		mattKeys.put(ActionType.MOVE_RIGHT, KeyEvent.VK_D);
		mattKeys.put(ActionType.PICKUP_ITEM, KeyEvent.VK_Q);
		mattKeys.put(ActionType.USE_ITEM, KeyEvent.VK_E);
		cooks.add(new Cook(thisGame, "Matt", Color.GREEN, mattKeys, new Location(1, 1)));
		
		thisGame.updateGraphics();
		
		Thread control = new Thread("Control") {
			@Override
			public void run() {
				startMap(new Level1(cooks, score, thisGame));
			}
		};
		control.start();
	}
	
	public void setObservedFps(double fps) {
		observedFps = fps;
	}
	private void startMap(Room lvl) {
		map = lvl;
		gamePanel.setLevel(lvl);
		tickTimer = new TickTimer(tps, lvl);
		System.out.println("Started");
		Util.showThreads();
		updateGraphics();
		Util.pause(lvl.getSyncObj());
		System.out.println("Done");
	}
	
	public void mapDraw2() {
		
	}
	
	protected void mapDraw() {
		roomWidth = 16;
		roomHeight = 16;
		roomSync = new Object();
		map = new Room(roomWidth, roomHeight, this, roomSync, score);
		gamePanel = new GamePanel(this, map);
		score = new Score();
		
		Map<ActionType, Integer> benKeys = new HashMap<ActionType, Integer>();
		benKeys.put(ActionType.MOVE_UP, KeyEvent.VK_UP);
		benKeys.put(ActionType.MOVE_DOWN, KeyEvent.VK_DOWN);
		benKeys.put(ActionType.MOVE_LEFT, KeyEvent.VK_LEFT);
		benKeys.put(ActionType.MOVE_RIGHT, KeyEvent.VK_RIGHT);
		benKeys.put(ActionType.PICKUP_ITEM, KeyEvent.VK_CONTROL);
		benKeys.put(ActionType.USE_ITEM, KeyEvent.VK_NUMPAD0);
		map.addCook(new Cook(this, "Ben", Color.ORANGE, benKeys));
		
		Map<ActionType, Integer> mattKeys = new HashMap<ActionType, Integer>();
		mattKeys.put(ActionType.MOVE_UP, KeyEvent.VK_W);
		mattKeys.put(ActionType.MOVE_DOWN, KeyEvent.VK_S);
		mattKeys.put(ActionType.MOVE_LEFT, KeyEvent.VK_A);
		mattKeys.put(ActionType.MOVE_RIGHT, KeyEvent.VK_D);
		mattKeys.put(ActionType.PICKUP_ITEM, KeyEvent.VK_Q);
		mattKeys.put(ActionType.USE_ITEM, KeyEvent.VK_E);
		map.addCook(new Cook(this, "Peter", Color.ORANGE, mattKeys));
		
		for(int i = 0; i < map.getSize().width; i++) {
			map.addThing(new Counter(), new Location(i, 0));
		}
		map.addThing(new Apple(), new Location(2, 0));
		map.addThing(new Apple(-1, FoodState.CHOPPED), new Location(3, 0));
		map.addThing(new Lettuce(), new Location(11, 0));
		map.addThing(new Lettuce(-1, FoodState.CHOPPED), new Location(12, 0));
		map.getSpace(new Location(4, 0)).removeAll(new Counter());
		map.addThing(new CuttingBoard(), new Location(4, 0));
		map.addThing(new CuttingBoard(), new Location(4, 3));	
		map.addThing(new Counter(), new Location(4, 4));
		map.addThing(new CuttingBoard(), new Location(5, 3));
		map.addThing(new Counter(), new Location(5, 4));
		map.addThing(new CuttingBoard(), new Location(6, 3));
		map.addThing(new Counter(), new Location(6, 4));
		map.addThing(new Spawner(new Apple()), new Location(5, 0));
		map.addThing(new Spawner(new Lettuce()), new Location(6, 0));
		map.addThing(new Spawner(new Tomato()), new Location(7, 0));
		map.addThing(new Spawner(new Bun()), new Location(8, 0));
		map.addThing(new Spawner(new Beef()), new Location(9, 0));
		map.addThing(new Disposal(), new Location(15, 0));
		//map.addThing(new Window(this), 14, 0);
		
		List<Thing> toppings = new ArrayList<Thing>();
		toppings.add(new Lettuce());
		toppings.add(new Beef(-1, FoodState.COOKED));
		map.addObjectives(new Objective(new Bun(toppings), 5));
		//toppings.add(new Patty());
		Bun bun1 = new Bun(toppings);
		Bun bun2 = new Bun(toppings);
		map.addObjectives(new Objective(bun1.duplicate(), 5));
		map.addObjectives(new Objective(bun2.duplicate(), 5));
		map.addThing(new Spawner(bun1), new Location(10, 0));
		//map.addThing(new Spawner(bun1), 11, 0);
		updateGraphics();
	}
	
	public List<Objective> getObjectives() {
		return map.getObjectives();
	}

	public void setObjects(List<Objective> objectives) {
		map.setObjectives(objectives);
	}

	public Room getRoom() {
		return map;
	}
	public void updateGraphics() {
		gamePanel.update(observedFps);
	}
	
	public void registerKeylistener(KeyListenAction a) {
		gamePanel.addKeyListener(a);
	}
	
	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}
	
	public void addScore(int score) {
		this.score.addScore(score);
	}
	
	public int getTps() {
		return tps;
	}
	public void setTps(int tps) {
		this.tps = tps;
	}
}
