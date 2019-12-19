package benjaminc.chef_simulator;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import benjamin.BenTCP.TCPOnDataArrival;
import benjamin.BenTCP.TCPServer;
import benjamin.BenTCP.TCPSetupStream;
import benjaminc.chef_simulator.control.CommandProcessor;
import benjaminc.chef_simulator.control.Cook;
import benjaminc.chef_simulator.control.KeyListenAction;
import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.control.TickEvent;
import benjaminc.chef_simulator.control.TickTimer;
import benjaminc.chef_simulator.control.command.ListCommand;
import benjaminc.chef_simulator.control.command.MoveCommand;
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
	
	private static boolean paused;
	private static boolean multiplayer = false;
	
	private static TCPServer server;
	
	private static Scanner sysin;
	
	public static CommandProcessor cp = new CommandProcessor();
	
	public static void setupGame() {
		setupGame(40, 30, false, true);
	}
	public static void setupGame(int scale, int fps, boolean lago, boolean exitOnClose) {
		sysin = new Scanner(System.in);
		
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
		cp = new CommandProcessor();
		cp.addCommand(new MoveCommand());
		cp.addCommand(new ListCommand());
		
		openMultiplayer();
		
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
		tickTimer = new TickTimer(setTps);
		tickTimer.addToDo(new TickEvent() { @Override public void tick(long frame) {
			if(room != null) { room.tick(frame); } else { System.out.println("[ERROR] Room is NULL!"); } }
			}, UUID.randomUUID());
		tickTimer.addToDo(new TickEvent() { @Override public void tick(long frame) {
			Game.roomUDG(frame);} }, UUID.randomUUID());
		tickTimer.start();
		registerKeylistener(new KeyListenAction() {
			
			@Override
			public void keyReleaseEvent(int key) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressEvent(int key) {
				if(key == KeyEvent.VK_ESCAPE) {
					togglePaused();
				}
			}
		});
		System.out.println("Started");
		Util.showThreads();
		System.out.println("UDG start");
//		updateGraphics();
		Util.pause(lvl.getSyncObj());
		tickTimer.end();
		System.out.println("Done");
		new MessageDialog("Game Over, You Win", "You won the game! Your score was " + score.getScore());
	}
	
	/**
	 * Gets weather the game is paused. If the game is multiplayer, the {@link TickTimer} continues even when paused.
	 * @return the boolean if paused
	 */
	public static boolean getPaused() {
		return paused;
	}
	
	/**
	 * Sets weather the game is paused. If the game is multiplayer, the {@link TickTimer} continues even when paused.
	 * @param newPaused the boolean if paused
	 */
	public static void setPaused(boolean newPaused) {
		if(!multiplayer) {
			if(newPaused) {
				tickTimer.pause();
			} else {
				tickTimer.unpause();
			}
		}
		if(newPaused) {
			gamePanel.getPauseScreen().enableKeys();
			Game.getGamePanel().getPanel().repaint();
		} else {
			gamePanel.getPauseScreen().disableKeys();
		}
		paused = newPaused;
	}
	
	/**
	 * Toggles weather the game is paused. If the game is multiplayer, the {@link TickTimer} continues even when paused.
	 */
	public static void togglePaused() {
		setPaused(!paused);
	}
	
	public static void openMultiplayer() {
		if(!multiplayer) {
			multiplayer = true;
			if(paused) {
				tickTimer.unpause();
			}
			Thread serverStarter = new Thread("ServerStart") {
				@Override public void run() {
					chat("Waiting for client on port 25242");
					TCPOnDataArrival odr = new TCPOnDataArrival() {
						
						@Override
						public void onDataArrived(byte[] data) {
							String dataString = "";
							for(int i = 0; i < data.length; i++) {
								dataString = dataString + (char) data[i];
							}
							System.out.println("Recived: " + dataString);
							if(dataString.charAt(0) == '/') {
								cp.process(dataString.substring(Math.min(dataString.length()-1, 1)));
							} else {
								chat(dataString);
							}
						}
					};
					server = new TCPServer(25242, odr, TCPSetupStream.defaultSetupStream(new Scanner(System.in)), 1);
					chat("Client connected");
			} };
			serverStarter.start();
		} else {
			chat("Game is already multiplayer! (port 25242)");
		}
		
	}
	
	public static List<Cook> getCooks() {
		return cooks;
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
	
	public static UUID registerKeylistener(KeyListenAction a) {
		return gamePanel.addKeyListener(a);
	}
	public static void removeKeyListener(UUID a) {
		gamePanel.removeKeyListener(a);
	}
	/**
	 * Redraws the current frame. Does not cause a tick or increment the frame counter
	 */
	public static void redrawFrame() {
		gamePanel.getPanel().repaint();
	}
	/**
	 * Sends a message to the in game chat
	 * @param msg the {@link String} message
	 */
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
	
	/**
	 * Exits the game
	 */
	public static void end() {
		sysin.close();
		
		System.exit(0);
	}
}
