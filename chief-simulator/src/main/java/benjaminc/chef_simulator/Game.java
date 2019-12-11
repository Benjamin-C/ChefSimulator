package benjaminc.chef_simulator;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import benjaminc.chef_simulator.control.Cook;
import benjaminc.chef_simulator.control.KeyListenAction;
import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.control.TickTimer;
import benjaminc.chef_simulator.graphics.ActionType;
import benjaminc.chef_simulator.graphics.GamePanel;
import benjaminc.chef_simulator.graphics.GraphicalLoader;
import benjaminc.chef_simulator.rooms.Level1;
import benjaminc.chef_simulator.rooms.Room;
import benjaminc.chef_textures.dialog.MessageDialog;
import benjaminc.util.Util;

public class Game {

	private static GamePanel gamePanel;
	private static Room room;
	private static Score score;
	private static double observedTps;
	private static TickTimer tickTimer;
	
	public static int droppedFrameCount = 0;
	private static int setTps = 6;
	private static List<Cook> cooks;
	
	public static void setupGame() {
		setupGame(40, 30, false, true);
	}
	public static void setupGame(int scale, int fps, boolean lago, boolean exitOnClose) {
		setTps = fps;
		
		cooks = new ArrayList<Cook>();
		score = new Score();
		GraphicalLoader.loadCache("assets/textures/");
		room = new Room(1, 1, new Object(), cooks);
		gamePanel = new GamePanel(room, scale, room.getWidth()*scale, room.getHeight()*scale, lago, fps, exitOnClose);
		
		System.out.println("New SysOut setd");
		
		cooks.add(newCook("Ben", new Color(255, 128, 0), new Location(14, 1),
				KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT,
				KeyEvent.VK_RIGHT, KeyEvent.VK_CONTROL, KeyEvent.VK_NUMPAD0));
		cooks.add(newCook("Matt", Color.GREEN, new Location(1, 1),
				KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A,
				KeyEvent.VK_D, KeyEvent.VK_Q, KeyEvent.VK_E));
	}
	
	public static void playDefaultGame() {
		Thread control = new Thread("Control") {
			@Override
			public void run() {
				playMap(new Level1(cooks, score));
				playMap(new Level1(cooks, score));
			}
		};
		control.start();
	}
	
	public static void playGame(Room r) {
		System.out.println(r.getObjectives());
		Thread control = new Thread("Control") {
			@Override
			public void run() {
				playMap(r);
			}
		};
		control.start();
	}
	public static void playJSONMap(String json) {
		Room lvl = new Room(json, new Object(), cooks);
		Thread control = new Thread("Control") {
			@Override
			public void run() {
				playMap(lvl);
			}
		};
		control.start();
	}
	public static Cook newCook(String name, Color color, Location location, int up, int down, int left, int right, int pickup, int use) {
		Map<ActionType, Integer> keys = new HashMap<ActionType, Integer>();
		keys.put(ActionType.MOVE_UP, up);
		keys.put(ActionType.MOVE_DOWN, down);
		keys.put(ActionType.MOVE_LEFT, left);
		keys.put(ActionType.MOVE_RIGHT, right);
		keys.put(ActionType.PICKUP_ITEM, pickup);
		keys.put(ActionType.USE_ITEM, use);
		return new Cook(name, color, keys, location);
	}

	public static void setObservedTps(double tps) {
		observedTps = tps;
	}
	private static void playMap(Room lvl) {
		room = lvl;
		room.initForGame(new Object(), cooks);
		gamePanel.setLevel(lvl);
		tickTimer = new TickTimer(setTps, lvl);
		tickTimer.start();
		System.out.println("Started");
		Util.showThreads();
		System.out.println("UDG start");
//		updateGraphics();
		Util.pause(lvl.getSyncObj());
		tickTimer.end();
		System.out.println("Done");
		new MessageDialog("Game Over, You Win", "You won the game! Your score was " + score.getScore());
	}
	
	public static List<Objective> getObjectives() {
		return room.getObjectives();
	}

	public static void setObjects(List<Objective> objectives) {
		room.setObjectives(objectives);
	}

	public static void roomUDG(long frame) {
		gamePanel.update(observedTps, droppedFrameCount, frame);
	}
	
	public static void registerKeylistener(KeyListenAction a) {
		gamePanel.addKeyListener(a);
	}
	
	public static void chat(String msg) {
		gamePanel.getChatBox().out.println(msg);
	}
	/**
	 * @return the {@link int} droppedFrameCount
	 */
	public static int getDroppedFrameCount() {
		return droppedFrameCount;
	}
	/**
	 * @param droppedFrameCount the {@link int} droppedFrameCount to set
	 */
	public static void setDroppedFrameCount(int droppedFrameCount) {
		Game.droppedFrameCount = droppedFrameCount;
	}
	/**
	 * @return the {@link GamePanel} the {@link Room} draws on
	 */
	public static GamePanel getGamePanel() {
		return gamePanel;
	}
	/**
	 * @return the {@link Room} the {@link Game} plays in
	 */
	public static Room getRoom() {
		return room;
	}
	/**
	 * @return the {@link Score} the {@link Game} uses
	 */
	public static Score getScore() {
		return score;
	}
	/**
	 * @return the {@link double} observedTps
	 */
	public static double getObservedTps() {
		return observedTps;
	}
	/**
	 * @return the {@link TickTimer} the {@link Game} uses
	 */
	public static TickTimer getTickTimer() {
		return tickTimer;
	}
	
	
}
