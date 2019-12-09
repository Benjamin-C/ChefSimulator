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
import benjaminc.util.PrintStreamDuplicator;
import benjaminc.util.Util;

public class Game {

	private static GamePanel gamePanel;
	private static Room map;
	private static Score score;
	
	private static double observedTps;
	
	private static int tps = 6;
	
	private static int droppedFrameCount = 0;
	
	private static TickTimer tickTimer;
	private static List<Cook> cooks;
	
	public static void setupGame() {
		setupGame(40, 30, false, true);
	}
	public static void setupGame(int scale, int fps, boolean lago, boolean exitOnClose) {
		tps = fps;
		
		cooks = new ArrayList<Cook>();
		score = new Score();
		GraphicalLoader.loadCache("assets/textures/");
		map = new Room(1, 1, this, new Object(), score, cooks);
		gamePanel = new GamePanel(thisGame, map, scale, map.getWidth()*scale, map.getHeight()*scale, lago, fps, exitOnClose);
		
		// TODO here is the print router
		PrintStreamDuplicator psd = new PrintStreamDuplicator(System.out, gamePanel.getChatBox().out);
		System.setOut(psd);
		
		System.out.println("New SysOut setd");
		
		cooks.add(newCook("Ben", new Color(255, 128, 0), new Location(14, 1),
				KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT,
				KeyEvent.VK_RIGHT, KeyEvent.VK_CONTROL, KeyEvent.VK_NUMPAD0));
		cooks.add(newCook("Matt", Color.GREEN, new Location(1, 1),
				KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A,
				KeyEvent.VK_D, KeyEvent.VK_Q, KeyEvent.VK_E));
		
		try {
			
			Thread.sleep(500);
			System.out.println("OutManThingyToFind");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void playDefaultGame() {
		Thread control = new Thread("Control") {
			@Override
			public void run() {
				playMap(new Level1(cooks, score, Game.this));
				playMap(new Level1(cooks, score, Game.this));
			}
		};
		control.start();
	}
	
	public void playGame(Room r) {
		System.out.println(r.getObjectives());
		Thread control = new Thread("Control") {
			@Override
			public void run() {
				playMap(r);
			}
		};
		control.start();
	}
	public void playJSONMap(String json) {
		Room lvl = new Room(json, this, new Object(), score, cooks);
		Thread control = new Thread("Control") {
			@Override
			public void run() {
				playMap(lvl);
			}
		};
		control.start();
	}
	public Cook newCook(String name, Color color, Location location, int up, int down, int left, int right, int pickup, int use) {
		Map<ActionType, Integer> keys = new HashMap<ActionType, Integer>();
		keys.put(ActionType.MOVE_UP, up);
		keys.put(ActionType.MOVE_DOWN, down);
		keys.put(ActionType.MOVE_LEFT, left);
		keys.put(ActionType.MOVE_RIGHT, right);
		keys.put(ActionType.PICKUP_ITEM, pickup);
		keys.put(ActionType.USE_ITEM, use);
		return new Cook(this, name, color, keys, location);
	}

	public void setObservedTps(double tps) {
		observedTps = tps;
	}
	private void playMap(Room lvl) {
		map = lvl;
		map.initForGame(this, new Object(), score, cooks);
		gamePanel.setLevel(lvl);
		tickTimer = new TickTimer(tps, lvl);
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
	
	public List<Objective> getObjectives() {
		return map.getObjectives();
	}

	public void setObjects(List<Objective> objectives) {
		map.setObjectives(objectives);
	}

	public Room getRoom() {
		return map;
	}
	public void roomUDG(long frame) {
		gamePanel.update(observedTps, droppedFrameCount, frame);
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
	/**
	 * @param dropCount 
	 * 
	 */
	public void droppedFrame(int dropCount) {
		droppedFrameCount += dropCount;
	}
}
