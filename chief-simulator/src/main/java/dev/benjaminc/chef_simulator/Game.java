package dev.benjaminc.chef_simulator;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import org.lwjglb.engine.OpenGLEngine;
import org.lwjglb.engine.Window;

import benjamin.BenTCP.TCPClient;
import benjamin.BenTCP.TCPOnDataArrival;
import benjamin.BenTCP.TCPServer;
import benjamin.BenTCP.TCPSetupStream;
import dev.benjaminc.chef_simulator.chef_graphics.ActionType;
import dev.benjaminc.chef_simulator.chef_graphics.GamePanel;
import dev.benjaminc.chef_simulator.chef_graphics.GameSpace;
import dev.benjaminc.chef_simulator.chef_graphics.GraphicalLoader;
import dev.benjaminc.chef_simulator.control.Chef;
import dev.benjaminc.chef_simulator.control.CommandProcessor;
import dev.benjaminc.chef_simulator.control.EventHandler;
import dev.benjaminc.chef_simulator.control.KeyListenAction;
import dev.benjaminc.chef_simulator.control.TickEvent;
import dev.benjaminc.chef_simulator.control.TickTimer;
import dev.benjaminc.chef_simulator.control.command.ConnectCommand;
import dev.benjaminc.chef_simulator.control.command.EventCommand;
import dev.benjaminc.chef_simulator.control.command.FindCommand;
import dev.benjaminc.chef_simulator.control.command.ListCommand;
import dev.benjaminc.chef_simulator.control.command.MoveCommand;
import dev.benjaminc.chef_simulator.control.command.SetCommand;
import dev.benjaminc.chef_simulator.data.DataLoader;
import dev.benjaminc.chef_simulator.data.DataMap.DataMapKey;
import dev.benjaminc.chef_simulator.data.location.Location2d;
import dev.benjaminc.chef_simulator.events.ChatEvent;
import dev.benjaminc.chef_simulator.events.Event;
import dev.benjaminc.chef_simulator.rooms.Level1;
import dev.benjaminc.chef_simulator.rooms.Room;
import dev.benjaminc.chef_textures.dialog.MessageDialog;
import dev.benjaminc.util.JSONTools;
import dev.benjaminc.util.Util;

public class Game {

	private static GamePanel gamePanel;
	private static Room room;
	private static Score score;
	private static double observedTps;
	private static TickTimer tickTimer;
	
	public static int droppedFrameCount = 0;
	private static int setTps = 6;
	private static List<Chef> cooks;
	
	private static boolean paused;
	private static boolean multiplayer = false;
	
	private static TCPServer server;
	private static boolean isServer = false;
	private static PrintStream mpPrintStream;
	private static boolean mpConnected;
	
	private static TCPClient client;
	
	private static DataLoader dataLoader = null;
	
	public static OpenGLEngine openglEngine;
	
	public static boolean usingOpenGL = true;
	
	private static TCPOnDataArrival tcpodr = new TCPOnDataArrival() {
		@Override public void onDataArrived(byte[] data) {
			String dataString = "";
			for(int i = 0; i < data.length; i++) {
				dataString = dataString + (char) data[i];
			}
//			consoleInput(dataString);
			switch(dataString.charAt(0)) {
			case '{': 
				List<String> evts = JSONTools.seperateJSON(dataString);
				for(String s : evts) {
					if(s.length() > 1) {
						Event e = Event.loadEventFromJSON(s);
						if(e != null) {
							EventHandler.reciveEvent(e);
						}
					}
				}
				break;
			default: Game.getGamePanel().getChatBox().out.println(dataString);
			}
		}
	};
	
	private static Scanner sysin;
	
	public static CommandProcessor cp = new CommandProcessor();
	
	public static void setupGame() {
		setupGame(40, 30, false, true);
	}
	public static void setupGame(int scale, int fps, boolean lago, boolean exitOnClose) {
		sysin = new Scanner(System.in);
		setTps = fps;
		
		cooks = new ArrayList<Chef>();
		score = new Score();
		GraphicalLoader.loadCache("assets/textures/");
		room = new Room(1, 1, new Object(), cooks);
		gamePanel = new GamePanel(room, scale, room.getWidth()*scale, room.getHeight()*scale, lago, fps, exitOnClose);
		
		if(usingOpenGL) {
			initOpenGLGraphics();
			while(!openglEngine.isReady()) { System.out.print("."); try {Thread.sleep(1); } catch(InterruptedException e) {} }
		}
		
		System.out.println("New SysOut setd");
		
		cooks.add(newCook("Ben", new Color(255, 128, 0), new Location2d(14, 14),
				KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT,
				KeyEvent.VK_RIGHT, KeyEvent.VK_CONTROL, KeyEvent.VK_NUMPAD0));
		cooks.add(newCook("Matt", Color.GREEN, new Location2d(1, 1),
				KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A,
				KeyEvent.VK_D, KeyEvent.VK_Q, KeyEvent.VK_E));
		
		// AddCommands
		cp = new CommandProcessor();
		cp.addCommand(new MoveCommand());
		cp.addCommand(new ListCommand());
		cp.addCommand(new SetCommand());
		cp.addCommand(new EventCommand());
		cp.addCommand(new ConnectCommand());
		cp.addCommand(new FindCommand());
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
	
	public static void initOpenGLGraphics() {
		try {
	        boolean vSync = false;
	        Window.WindowOptions opts = new Window.WindowOptions();
	        opts.cullFace = false;
	        opts.showFps = true;
	        opts.compatibleProfile = true;
	        opts.antialiasing = true;
	        opts.frustumCulling = false;
	        openglEngine = new OpenGLEngine("GAME", vSync, opts);
	        Thread opengl = new Thread(openglEngine, "OpenGL");
	        opengl.start();
	    } catch (Exception excp) {
	        excp.printStackTrace();
	        System.exit(-1);
	    }
	}
	
	public static Chef newCook(String name, Color color, Location2d location, int up, int down, int left, int right, int pickup, int use) {
		Map<ActionType, Integer> keys = new HashMap<ActionType, Integer>();
		keys.put(ActionType.MOVE_UP, up);
		keys.put(ActionType.MOVE_DOWN, down);
		keys.put(ActionType.MOVE_LEFT, left);
		keys.put(ActionType.MOVE_RIGHT, right);
		keys.put(ActionType.PICKUP_ITEM, pickup);
		keys.put(ActionType.USE_ITEM, use);
		return new Chef(name, color, keys, location);
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
			if(room != null) { 
				if(!(multiplayer && !isServer)) {
					room.tick(frame); 
				} else {
					room.tickCooks(frame);
				}
			} else { System.out.println("[ERROR] Room is NULL!"); }
			}}, UUID.randomUUID());
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
		System.out.println("Spinich " + usingOpenGL);
		if(usingOpenGL) {
			for(int x = 0; x < room.getWidth(); x++) {
				for(int y = 0; y < room.getHeight(); y++) {
					GameSpace s = room.getSpace(new Location2d(x, y));
					for(int z = 0; z < s.size(); z++) {
						final int nx = x;
						final int ny = y;
						final int nz = z;
						openglEngine.mod(new Runnable() {
							@Override public void run() {
								s.getThing(nz).getDataMap().put(DataMapKey.TEXTURE_OPENGL, openglEngine.newBunny(nx, nz, ny, 0.0f, 0.0f, 0.0f));
							}
						}); 
					}
				}
			}	
		}
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
	 * Checks if the game is trying to load data
	 * @return the boolean loadingData
	 */
	public static DataLoader getDataLoader() {
		return dataLoader;
	}
	/**
	 * Sets wether or not the game is loading data
	 * @param newLoadingData the boolean newLoadingData
	 */
	public static void setDataLoader(DataLoader newDataLoader) {
		dataLoader = newDataLoader;
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
					
					server = new TCPServer(25242, tcpodr, TCPSetupStream.defaultSetupStream(new Scanner(System.in)), 1);
					mpPrintStream = new PrintStream(server.getOutputStream());
					mpConnected = true;
					isServer = true;
					chat("Client connected");
			} };
			serverStarter.start();
		} else {
			chat("Game is already multiplayer! (port 25242)");
		}	
	}
	
	public static void connectToServer(String ip, int port) {
		if(!multiplayer) {
			multiplayer = true;
			if(paused) {
				tickTimer.unpause();
			}
		}
		client = new TCPClient(ip, port, tcpodr, TCPSetupStream.defaultSetupStream(new Scanner(System.in)), "GameClient");
		mpPrintStream = new PrintStream(client.getOutputStream());
		mpConnected = true;
	}
	
	public static boolean isMPConnected() {
		return mpConnected;
	}
	public static boolean isMultiplayer() {
		return multiplayer;
	}
	public static boolean isServer() {
		return isServer;
	}
	
	public static List<Chef> getCooks() {
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
		EventHandler.fireEvent(new ChatEvent(msg, 480));
	}
	/**
	 * Handles input from a console sending to chat or {@link CommandProcessor} as needed
	 * @param in the {@link String} to handel 
	 */
	public static void consoleInput(String in) {
		if(in.charAt(0) == '/') {
			cp.process(in.substring(Math.min(in.length()-1, 1)));
		} else {
			gamePanel.getChatBox().out.println(in);
		}
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
	
	public static TCPServer getServer() {
		return server;
	}
	
	public static PrintStream getServerPrintStream() {
		return mpPrintStream;
	}
	/**
	 * Gets a {@link Chef} by name
	 * @param name	the {@link String} name
	 * @return		the {@link Chef}. Null if no cook of that name exists
	 */
	public static Chef getChefByName(String name) {
		for(Chef c : cooks) {
			if(c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	public static void doNothing() {
		
	}
}
