package benjaminc.chief_simulator;

import benjaminc.chief_simulator.control.KeyListenAction;
import benjaminc.chief_simulator.graphics.GamePanel;
import benjaminc.chief_simulator.graphics.Room;
import benjaminc.chief_simulator.things.Apple;
import benjaminc.chief_simulator.things.Cook;

public class Game {

	private GamePanel gamePanel;
	private int roomWidth;
	private int roomHeight;
	private Room map;
	
	@SuppressWarnings("unused")
	public Game() {
		roomWidth = 4;
		roomHeight = 4;
		map = new Room(roomWidth, roomHeight);
		gamePanel = new GamePanel(map);
		map.addCook(new Cook(this, "Ben"));
		map.addThing(new Apple(), 2, 1);
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
	
	public int getRoomWidth() {
		return roomWidth;
	}
	
	public int getRoomHeight() {
		return roomHeight;
	}
}
