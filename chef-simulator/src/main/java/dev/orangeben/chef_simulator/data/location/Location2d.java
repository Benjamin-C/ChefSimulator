package dev.orangeben.chef_simulator.data.location;

import java.util.Map;

import dev.orangeben.chef_simulator.control.Direction;
import dev.orangeben.util.JSONTools;

public class Location2d extends Location {
	
	protected int x;
	protected int y;
	
	public Location2d(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Location2d(String json) {
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
	
	public Location2d add(Location2d l) {
		if(l instanceof Location2d) {
			x = x + ((Location2d) l).getX();
			y = y + ((Location2d) l).getY();
		}
		return this;
	}
	public Location2d add(Direction d) {
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
	public Location2d clone() {
		return new Location2d(x, y);
	}

	public String toSimpleString() {
		return x + "," + y;
	}
	public String asJSON() {
		return "{\"D\":\"2\",\"X\":\"" + x + "\",\"Y\":\"" + y + "\"}";
	}
	public boolean equals(Location2d l) {
		if(l instanceof Location2d) {
			return ((Location2d) l).x == x && ((Location2d) l).y == y;
		}
		return false;
	}
	public Location3d as3d() {
		return as3d(0);
	}
	public Location3d as3d(int z) {
		return new Location3d(x, y, z);
	}
}
