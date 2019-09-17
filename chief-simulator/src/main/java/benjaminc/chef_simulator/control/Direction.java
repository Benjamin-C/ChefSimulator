package benjaminc.chef_simulator.control;

public enum Direction {
	UP(0, -1),
	DOWN(0, 1),
	LEFT(-1, 0),
	RIGHT(1, 0);
	
	private int xdir;
	private int ydir;
	
	private Direction(int x, int y) {
		xdir = x;
		ydir = y;
	}
	
	public int getX() {
		return xdir;
	}
	
	public int getY() {
		return ydir;
	}
}
