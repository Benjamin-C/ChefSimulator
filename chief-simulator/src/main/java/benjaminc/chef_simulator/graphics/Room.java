package benjaminc.chef_simulator.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.Objective;
import benjaminc.chef_simulator.Score;
import benjaminc.chef_simulator.control.Cook;
import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.things.Thing;

public class Room implements Drawable {
	
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
	/** the {@link Score} of the game */
	protected Score score;
	/** the {@link Game} this is using */
	protected Game game;
	
	/**
	 * @param w the int width in cells
	 * @param h the int height in cells
	 * @param game the {@link Game} to work with
	 * @param whenDone the {@link Object} to run {@link Object#notify()} when done
	 * @param score the {@link Score} for the room to use
	 */
	public Room(int w, int h, Game game, Object whenDone, Score score) {
		this(w, h, game, whenDone, score, new ArrayList<Cook>());
	}
	
	/**
	 * @param w the int width in cells
	 * @param h the int height in cells
	 * @param game the {@link Game} to work with
	 * @param whenDone the {@link Object} to run {@link Object}#{@link #notify()} when done
	 * @param score the {@link Score} for the room to use
	 * @param cooks the {@link List} of {@link Cook} to put in the room
	 */
	public Room(int w, int h, Game game, Object whenDone, Score score, List<Cook> cooks) {
		width = w;
		height = h;
		this.game = game;
		room = new GameSpace[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				room[i][j] = new GameSpace(new Location(i, j));
			}
		}
		this.cooks = cooks;
		whenDoneSync = whenDone;
		if(whenDoneSync == null) {
			whenDoneSync = new Object();
		}
		objective = new ArrayList<Objective>();
		if(score == null) {
			this.score = new Score();
		} else {
			this.score = score;
		}
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
	 * Sets the observed fps of the room
	 * @param fps the double fps
	 */
	public void setTps(double tps) {
		game.setObservedTps(tps);
	}
	/**
	 * Gets the score from the room
	 * @return the {@link Score} score
	 */
	public Score getScore() {
		return score;
	}
	
	/**
	 * Tick the room
	 * @param frame the long number of the frame in the game
	 */
	public void tick(long frame) {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				room[i][j].tick(this, new Location(i, j), frame, game);
			}
		}
		for(Cook c : cooks) {
			c.tick(this, c.getLocation(), frame, game);
		}
		game.updateGraphics();
	}
	
	@Override
    public void draw(Graphics g, int x, int y, int boxWidth, int boxHeight, double fps) {
		GraphicalDrawer gd = new GraphicalDrawer(g);
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				room[i][j].draw(gd, (i * boxWidth) + x,  (j * boxHeight) + y,  boxWidth,  boxHeight);
			}
		}
		if(cooks != null) {
			for(Cook c : cooks) {
				c.draw(g, x, y, boxWidth,  boxHeight);
			}
		} else {
			System.err.println("[ERROR] Cooks = NULL!");
		}
		for(int i = 0; i < getWidth(); i++) {
			g.setColor(new Color(16, 64, 16));
			g.fillRect(x+(i*boxWidth), 0, boxWidth, boxHeight);
			if(game.getObjectives().size() > i) {
				game.getObjectives().get(i).draw(g, (i * boxWidth) + x, 0, boxWidth, boxHeight);
			}
			if(i == width - 1) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("arial", 0, boxHeight));
				String score = game.getScore().getScore() + "";
				int textWidth = g.getFontMetrics().stringWidth(score+ "");
				g.drawString(score + "", x+((i+1)*boxWidth)-textWidth-(boxWidth/8), boxHeight - (boxHeight / 8));
			}
		}
	}
}
