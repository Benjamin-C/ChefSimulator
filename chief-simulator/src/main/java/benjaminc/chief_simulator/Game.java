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
import benjaminc.util.Util;

public class Game {

	private GamePanel gamePanel;
	private Room map;
	private Score score;
	
	private double observedFps;
	
	private int tps = 6;
	
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
		
		cooks.add(newCook("Ben", new Color(255, 128, 0), new Location(14, 1),
				KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT,
				KeyEvent.VK_RIGHT, KeyEvent.VK_CONTROL, KeyEvent.VK_NUMPAD0));
		cooks.add(newCook("Matt", Color.GREEN, new Location(1, 1),
				KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A,
				KeyEvent.VK_D, KeyEvent.VK_Q, KeyEvent.VK_E));
		thisGame.updateGraphics();
		
		Thread control = new Thread("Control") {
			@Override
			public void run() {
				playMap(new Level1(cooks, score, thisGame));
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

	public void setObservedFps(double fps) {
		observedFps = fps;
	}
	private void playMap(Room lvl) {
		map = lvl;
		gamePanel.setLevel(lvl);
		tickTimer = new TickTimer(tps, lvl);
		tickTimer.start();
		System.out.println("Started");
		Util.showThreads();
		updateGraphics();
		Util.pause(lvl.getSyncObj());
		System.out.println("Done");
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
