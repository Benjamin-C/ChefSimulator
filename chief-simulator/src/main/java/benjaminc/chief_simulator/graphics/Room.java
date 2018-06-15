package benjaminc.chief_simulator.graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import benjaminc.chief_simulator.things.Cook;
import benjaminc.chief_simulator.things.Thing;

public class Room {
	
	protected GameSpace[][] room;
	
	protected List<Cook> cooks;
	int width;
	int height;
	
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
	public void draw(Graphics g, int w, int h) {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				room[i][j].draw(g, i * w,  j * h,  w,  h);
			}
		}
		for(Cook c : cooks) {
			c.draw(g,  w,  h);
		}
	}
}
