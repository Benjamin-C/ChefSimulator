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
	public static Direction fromID(int id) {
		switch(id) {
		case 0: return Direction.UP;
		case 1: return Direction.RIGHT;
		case 2: return Direction.DOWN;
		case 3: return Direction.LEFT;
		}
		return null;
	}
	
	public Direction add(Direction toadd) {
		return fromID((id + toadd.getId()) % 4);
	}
	public Direction sub(Direction toadd) {
		return fromID((id - toadd.getId()) % 4);
	}
}
