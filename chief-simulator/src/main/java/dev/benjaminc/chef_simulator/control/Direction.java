package dev.benjaminc.chef_simulator.control;

public enum Direction {
	UP(0, -1, 0),
	DOWN(0, 1, 2),
	LEFT(-1, 0, 3),
	RIGHT(1, 0, 1);
	
	private int xdir;
	private int ydir;
	private int id;
	
	private Direction(int x, int y, int id) {
		xdir = x;
		ydir = y;
		this.id = id;
	}
	
	public int getX() {
		return xdir;
	}
	
	public int getY() {
		return ydir;
	}
	
	public int getId() {
		return id;
	}
}
