package benjaminc.chef_simulator.control;

import java.util.Map;

import benjaminc.util.JSONTools;

public class Location3d extends Location {

	protected int z;
	
	public Location3d(int x, int y, int z) {
		super(x, y);
		this.z = z;
	}
	public Location3d(String json) {
		super(-1, -1);
		if(json.charAt(0) == '{' && json.charAt(json.length()-1) == '}') {
			json = json.substring(1, json.length() - 1);
			Map<String, String> js = JSONTools.splitJSON(json);
			for(String s : js.keySet()) {
				switch(s) {
				case "X": x = Integer.parseInt(js.get(s)); break;
				case "Y": y = Integer.parseInt(js.get(s)); break;
				case "Z": z = Integer.parseInt(js.get(s)); break;
				default: break;
				}
			}
		}
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public Location add(Location3d l) {
		x = x + l.getX();
		y = y + l.getY();
		z = z + l.getZ();
		return this;
	}
	public boolean inside(int xmin, int xmax, int ymin, int ymax, int zmin, int zmax) {
		boolean temp = x < xmax && x >= xmin && y < ymax && y >= ymin && z < zmax && z >= zmin;
		//System.out.println(xmin + " <= x < " + xmax + " | " + ymin + " <= y < " + ymax + " | " + temp);
		return temp;
	}
	@Override
	public String toString() {
		return "Location[x=" + x + ",y=" + y + ",z=" + z +"]";
	}
	public String asJSON() {
		return "{\"D\":\"3\",\"X\":\"" + x + "\",\"Y\":\"" + y + "\",\"Z\":\"" + z + "\"}";
	}
	@Override
	public Location3d clone() {
		return new Location3d(x, y, z);
	}

	public String toSimpleString() {
		return x + "," + y + "," + z;
	}
	public boolean equals(Location3d l) {
		return l.x == x && l.y == y && l.z == z;
	}
	public Location as2d() {
		return new Location(x, y);
	}
}
