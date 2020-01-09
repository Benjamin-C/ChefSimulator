package benjaminc.chef_simulator.control;

import java.util.Map;

import benjaminc.chef_simulator.data.Savable;
import benjaminc.util.JSONTools;

public class Location implements Savable {
	
	protected int x;
	protected int y;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Location(String json) {
		if(json.charAt(0) == '{' && json.charAt(json.length()-1) == '}') {
			json = json.substring(1, json.length() - 1);
			Map<String, String> js = JSONTools.splitJSON(json);
			for(String s : js.keySet()) {
				switch(s) {
				case "X": x = Integer.parseInt(js.get(s)); break;
				case "Y": y = Integer.parseInt(js.get(s)); break;
				default: break;
				}
			}
		}
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

	public String toSimpleString() {
		return x + "," + y;
	}
	public String asJSON() {
		return "{\"D\":\"2\",\"X\":\"" + x + "\",\"Y\":\"" + y + "\"}";
	}
	public boolean equals(Location l) {
		return l.x == x && l.y == y;
	}
	public Location3d toLocation3d(int z) {
		return new Location3d(z, y, z);
	}
}
