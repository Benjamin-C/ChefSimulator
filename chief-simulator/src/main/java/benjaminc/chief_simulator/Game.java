package benjaminc.chief_simulator;

import java.awt.event.KeyEvent;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import javax.script.SimpleBindings;

import benjaminc.chief_simulator.control.KeyListenAction;
import benjaminc.chief_simulator.graphics.ActionType;
import benjaminc.chief_simulator.graphics.GamePanel;
import benjaminc.chief_simulator.graphics.Room;
import benjaminc.chief_simulator.things.Apple;
import benjaminc.chief_simulator.things.Cook;
import benjaminc.chief_simulator.things.Counter;
import benjaminc.chief_simulator.things.Lettuce;

public class Game {

	private GamePanel gamePanel;
	private int roomWidth;
	private int roomHeight;
	private Room map;
	
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
		map.addCook(new Cook(this, "Ben", benKeys));
		
		Map<ActionType, Integer> mattKeys = new HashMap<ActionType, Integer>();
		mattKeys.put(ActionType.MOVE_UP, KeyEvent.VK_W);
		mattKeys.put(ActionType.MOVE_DOWN, KeyEvent.VK_S);
		mattKeys.put(ActionType.MOVE_LEFT, KeyEvent.VK_A);
		mattKeys.put(ActionType.MOVE_RIGHT, KeyEvent.VK_D);
		mattKeys.put(ActionType.PICKUP_ITEM, KeyEvent.VK_SHIFT);
		map.addCook(new Cook(this, "Matt", mattKeys));
		map.addThing(new Apple(), 2, 1);
		map.addThing(new Lettuce(), 5, 7);
		for(int i = 0; i < map.getSize().width; i++) {
			map.addThing(new Counter(), i, 0);
		}
		updateGraphics();
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
