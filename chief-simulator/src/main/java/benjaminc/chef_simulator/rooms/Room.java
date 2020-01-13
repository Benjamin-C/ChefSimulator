package benjaminc.chef_simulator.rooms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.Objective;
import benjaminc.chef_simulator.control.Cook;
import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.data.Savable;
import benjaminc.chef_simulator.graphics.Drawable;
import benjaminc.chef_simulator.graphics.GameSpace;
import benjaminc.chef_simulator.graphics.GraphicalDrawer;
import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.NeedsInitThing;
import benjaminc.util.JSONTools;

public class Room implements Drawable, Savable, Cloneable {
	
	/** the {@link GameSpace} 2d array of the room */
	protected GameSpace[][] room;
	
	/** the {@link List} of {@link Cook} in the room */
	protected List<Cook> cooks;
	/** the int width of the room in cells */
	protected int width;
	/** the int height of the room in cells */
	protected int height;
	/** the {@link Object} to run {@link Object#notify()} on when room is complete */
	protected Object whenDoneSync;
	/** the {@link List} of {@link Objective} to complete */
	protected List<Objective> objective;
	
	/**
	 * @param w the int width in cells
	 * @param h the int height in cells
	 * @param whenDone the {@link Object} to run {@link Object#notify()} when done
	 */
	public Room(int w, int h, Object whenDone) {
		this(w, h, whenDone, new ArrayList<Cook>());
	}
	
	/**
	 * @param w the int width in cells
	 * @param h the int height in cells
	 * @param whenDone the {@link Object} to run {@link Object}#{@link #notify()} when done
	 * @param cooks the {@link List} of {@link Cook} to put in the room
	 */
	public Room(int w, int h, Object whenDone, List<Cook> cooks) {
		initForGame(whenDone, cooks);
		
		width = w;
		height = h;
		room = new GameSpace[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				room[i][j] = new GameSpace(new Location(i, j));
			}
		}
		objective = new ArrayList<Objective>();
	}
	
	/**
	 * @param json the JSON {@link String} representation of the {@link Room}
	 * @param whenDone the {@link Object} to run {@link Object}#{@link #notify()} when done
	 * @param cooks the {@link List} of {@link Cook} to put in the room
	 */
	public Room(String json, Object whenDone, List<Cook> cooks) {
		initForGame(whenDone, cooks);
		
		changeLayout(json);
	}
	
	/**
	 * Changes the layout of the room to the new JSON
	 * @param json the JSON {@link String}
	 */
	public void changeLayout(String json) {
		Map<String, String> j = JSONTools.splitJSON(json);
		
		System.out.println("JSON size is " + JSONTools.unformatJSON(json));
		System.out.println(JSONTools.printMap(j));
		System.out.println(j.keySet());
		List<String> rows = JSONTools.splitJSONArray(j.get(RoomDataKey.ROOM.toString()));
		System.out.println(rows);
		try {
			width = Integer.parseInt(j.get(RoomDataKey.WIDTH.toString()));
			height = Integer.parseInt(j.get(RoomDataKey.HEIGHT.toString()));
		} catch (NumberFormatException e) {
			System.out.println("Room size could not be read. Trying to guess");
			width = rows.size();
			height = JSONTools.splitJSONArray(rows.get(0)).size();
		}
		room = new GameSpace[width][height];
		
		for(int y = 0; y < height; y++) {
			List<String> row = JSONTools.splitJSONArray(rows.get(y));
			for(int x = 0; x < width; x++) {
				GameSpace here = new GameSpace(new Location(x, y), false);
				String spot = row.get(x);
				switch(JSONTools.guessType(spot)) {
				case ARRAY: {
					List<String> spot_list = JSONTools.splitJSONArray(spot);
					for(int i = 0; i < spot_list.size(); i++) {
						here.addThing(BasicThing.makeThingFromJSON(spot_list.get(i)));
					}
				} break;
				case OBJECT: here.addThing(BasicThing.makeThingFromJSON(spot)); break;
				default: break;
				}
				room[x][y] = here;
			}
		}
		
		List<String> objs = JSONTools.splitJSONArray(j.get(RoomDataKey.OBJECTIVES.toString()));
		objective = new ArrayList<Objective>();
		if(objs.size() > 0) {
			for(int i = 0; i < objs.size(); i++) {
				if(objs.get(i) != null && objs.get(i).length() > 0) {
					objective.add(new Objective(objs.get(i)));
				}
			}
		}
		System.out.println("ObjectiveSize = " + objective.size());
	}
	
	/**
	 * Basic {@link Room} init
	 * @param whenDone the {@link Object} to run {@link Object#notify()} when done
	 * @param cooks the {@link List} of {@link Cook} to put in the room
	 */
	public void initForGame(Object whenDone, List<Cook> cooks) {
		this.cooks = cooks;
		whenDoneSync = whenDone;
		if(whenDoneSync == null) {
			whenDoneSync = new Object();
		}
		if(room != null) {
			for(GameSpace[] ga : room) {
				for(GameSpace g : ga) {
					for(Thing t : g.getThings()) {
						if(t instanceof NeedsInitThing) {
							((NeedsInitThing) t).init();
						}
					}
				}
			}
		}
	}
	
	@Override
	public String asJSON() {
		int tabs = 1;
		String s = "{\n";
		
		// Size
		s = s + tabs(tabs) + "\"" + RoomDataKey.WIDTH + "\":\"" + width + "\",\n";
		s = s + tabs(tabs) + "\"" + RoomDataKey.HEIGHT + "\":\"" + height + "\",\n";
		
		// Room
		s = s + tabs(tabs) + "\"" + RoomDataKey.ROOM + "\":[\n" + tabs(++tabs);
		for(int y = 0; y < height; y++) {
			if(y != 0) {
				s = s + ", ";
			}
			s = s + "[";
			tabs++;
			for(int x = 0; x < width; x++) {
				if(x != 0) {
					s = s + ", ";
				}
				s = s + "\n" + tabs(tabs);
				GameSpace g = room[x][y];
				if(g.size() > 1) {
					s = s + "[";
					tabs++;
					for(int e = 0; e < g.size(); e++) {
						if(e != 0) {
							s = s + ",";
						}
						s = s + "\n" + tabs(tabs) +g.getThing(e).asJSON();
					}
					s = s + "\n" + tabs(--tabs) + "]";
				} else {
					s = s + g.getThing().asJSON();
				}
			}
			s = s + "\n" + tabs(--tabs) + "]";
		}
		s = s + "\n" + tabs(--tabs) + "],\n";
		
		// Objectives
		s = s + tabs(tabs) + "\"" + RoomDataKey.OBJECTIVES + "\":[\n" + tabs(tabs++);
		for(int i = 0; i < objective.size(); i++) {
			if(i != 0) {
				s = s + ",\n" + tabs(tabs);
			}
			s = s + objective.get(i).asJSON();
		}
		tabs--;
		s = s + "\n" + tabs(tabs)  + "]";
				
		s = s + "\n}";
		return s;
	}
	
	@Override
	public Room clone() {
		Room n = new Room(width, height, whenDoneSync);
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				Location here = new Location(x, y);
				for(int i = 0; i < getSpace(here).size(); i++) {
					n.getSpace(here).addThing(getSpace(here).getThing(i).clone());
				}
			}
		}
		
		for(int i = 0; i < objective.size(); i++) {
			n.addObjectives(objective.get(i).clone());
		}
		
		return n;
	}
	
	private String tabs(int num) {
		String s = "";
		for(int i = 0; i < num; i++) {
			s = s + "\t";
		}
		return s;
	}
	/**
	 * Gets the sync {@link Object}
	 * @return the Sync {@link Object} for the room
	 */
	public Object getSyncObj() {
		return whenDoneSync;
	}
	/**
	 * Get all the objectives from the room
	 * @return the {@link List} of {@link Objective} for the room
	 */
	public List<Objective> getObjectives() {
		return objective;
	}
	/**
	 * Sets the list of {@link Objective}
	 * Sets internal list to reference to provided list
	 * @param o the {@link List} of {@link Objective} to reference
	 */
	public void setObjectives(List<Objective> o) {
		objective = o;
	}
	/**
	 * Adds multiple {@link Objective} to the room
	 * @param o the {@link List} of {@link Objective} to add
	 */
	public void addObjectives(List<Objective> o) {
		objective.addAll(o);
	}
	/**
	 * Adds an {@link Objective} to the room
	 * @param o the {@link Objective} to add
	 */
	public void addObjectives(Objective o) {
		objective.add(o);
	}

	@Override
	public int getWidth() {
		return width;
	}
	@Override
	public int getHeight() {
		return height;
	}
	/**
	 * Adds a {@link Thing} to the room
	 * @param t the {@link Thing} to add
	 * @param loc the {@link Location} to add it
	 */
	public void addThing(Thing t, Location loc) {
		room[loc.getX()][loc.getY()].addThing(t);
	}
	
	/**
	 * Adds a {@link Thing} to the room
	 * @param t the {@link Thing} to add
	 * @param loc the {@link Location} to add it
	 * @param elev the int elevation to add the {@link Thing} at
	 */
	public void addThing(Thing t, Location loc, int elev) {
		room[loc.getX()][loc.getY()].addThing(elev, t);
	}
	
	/**
	 * Gets the size of the room
	 * @return the {@link Dimension} size
	 */
	public Dimension getSize() {
		return new Dimension(width, height);
	}
	
	/**
	 * gets the {@link GameSpace} at a {@link Location}
	 * @param loc the {@link Location} to look
	 * @return the {@link GameSpace} there
	 */
	public GameSpace getSpace(Location loc) {
		return room[loc.getX()][loc.getY()];
	}
	
	/**
	 * Gets the things from the room
	 * @param loc the {@link Location} to get from
	 * @return the {@link List} of {@link Thing} at that spot
	 */
	public List<Thing> getThings(Location loc) {
		return room[loc.getX()][loc.getY()].getThings();
	}
	/**
	 * Adds a cook to the room
	 * @param c the {@link Cook} to add
	 */
	public void addCook(Cook c) {
		cooks.add(c);
	}
	
	/**
	 * Tick the room
	 * @param frame the long number of the frame in the game
	 */
	public void tick(long frame) {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				room[i][j].tick(this, new Location(i, j), frame);
			}
		}
		for(Cook c : cooks) {
			c.tick(this, c.getLocation(), frame);
		}
	}
	
	public void drawRoom(Graphics g, int x, int y, int boxWidth, int boxHeight) {
		GraphicalDrawer gd = new GraphicalDrawer(g);
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				room[i][j].draw(gd, (i * boxWidth) + x,  (j * boxHeight) + y,  boxWidth,  boxHeight);
			}
		}
	}
	public void drawCooks(Graphics g, int x, int y, int boxWidth, int boxHeight) {
		if(cooks != null) {
			for(Cook c : cooks) {
				c.draw(g, x, y, boxWidth,  boxHeight);
			}
		} else {
			System.err.println("WARNING No cooks to draw.");
		}
	}
	public void drawObjectives(Graphics g, int x, int y, int boxWidth, int boxHeight) {
		for(int i = 0; i < getWidth(); i++) {
			g.setColor(new Color(16, 64, 16));
			g.fillRect(x+(i*boxWidth), 0, boxWidth, boxHeight);
			if(Game.getObjectives() != null && Game.getObjectives().size() > i) {
				Game.getObjectives().get(i).draw(g, (i * boxWidth) + x, 0, boxWidth, boxHeight);
			}
			if(i == width - 1) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("arial", 0, boxHeight));
				String score = Game.getScore().getScore() + "";
				int textWidth = g.getFontMetrics().stringWidth(score+ "");
				g.drawString(score + "", x+((i+1)*boxWidth)-textWidth-(boxWidth/8), boxHeight - (boxHeight / 8));
			}
		}
	}
	
	@Override
    public void draw(Graphics g, int x, int y, int boxWidth, int boxHeight, double fps) {
		drawRoom(g, x, y, boxWidth, boxHeight);
		drawCooks(g, x, y, boxWidth, boxHeight);
		drawObjectives(g, x, y, boxWidth, boxHeight);
	}
	
	/**
	 * Data keys for saving Rooms to JSON
	 */
	public enum RoomDataKey {
	/** the JSON 2d array of {@link Thing} in the room */
	ROOM,
	/** the JSON array of {@link Objective} */
	OBJECTIVES,
	/** the int width of the room */
	WIDTH,
	/** the int height of the room */
	HEIGHT;
	}
}
