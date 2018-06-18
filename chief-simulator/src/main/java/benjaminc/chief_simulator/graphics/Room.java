package benjaminc.chief_simulator.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import benjaminc.chief_simulator.Game;
import benjaminc.chief_simulator.Objective;
import benjaminc.chief_simulator.control.Cook;
import benjaminc.chief_simulator.things.Thing;

public class Room {
	
	protected GameSpace[][] room;
	
	protected List<Cook> cooks;
	protected int width;
	protected int height;
	
	public Room(int w, int h) {
		width = w;
		height = h;
		room = new GameSpace[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				room[i][j] = new GameSpace();
			}
		}
		cooks = new ArrayList<Cook>();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	public void addThing(Thing t, int x, int y) {
		room[x][y].addThing(t);
	}
	
	public Dimension getSize() {
		return new Dimension(width, height);
	}
	
	public GameSpace getSpace(int x, int y) {
		return room[x][y];
	}
	
	public List<Thing> getThings(int x, int y) {
		return room[x][y].getThings();
	}
	public void addCook(Cook c) {
		cooks.add(c);
	}
	public void draw(Graphics g, int x, int y, int w, int h) {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				room[i][j].draw(g, (i * w) + x,  (j * h) + y,  w,  h);
			}
		}
		for(Cook c : cooks) {
			c.draw(g, x, y, w,  h);
		}
		
	}
}
