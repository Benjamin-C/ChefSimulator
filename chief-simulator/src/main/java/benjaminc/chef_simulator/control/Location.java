package benjaminc.chef_simulator.control;

public class Location {
	
	protected int x;
	protected int y;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Location add(Location l) {
		x = x + l.getX();
		y = y + l.getY();
		return this;
	}
	public Location add(Direction d) {
		x = x + d.getX();
		y = y + d.getY();
		return this;
	}
	public boolean inside(int xmin, int xmax, int ymin, int ymax) {
		boolean temp = x < xmax && x >= xmin && y < ymax && y >= ymin;
		//System.out.println(xmin + " <= x < " + xmax + " | " + ymin + " <= y < " + ymax + " | " + temp);
		return temp;
	}
	@Override
	public String toString() {
		return "Location[x=" + x + ",y=" + y +"]";
	}
	@Override
	public Location clone() {
		return new Location(x, y);
	}
}
