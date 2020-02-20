package dev.benjaminc.chef_simulator.data.location;

import java.util.Map;

import dev.benjaminc.util.JSONTools;

public class ChefHandLocation extends Location {

	private String name;
	
	public ChefHandLocation(String name, boolean itdoesntmatteritcanbeanythingyourheartdesires) {
		this.name = name;
	}
	
	public ChefHandLocation(String json) {
		if(json.charAt(0) == '{' && json.charAt(json.length()-1) == '}') {
			json = json.substring(1, json.length() - 1);
			Map<String, String> js = JSONTools.splitJSON(json);
			for(String s : js.keySet()) {
				switch(s) {
				case "NAME": name = js.get(s); break;
				default: break;
				}
			}
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "ChefHandLocation[name=" + name + "]";
	}
	@Override
	public Location clone() {
		return new ChefHandLocation(name, false);
	}

	public String toSimpleString() {
		return name;
	}
	public String asJSON() {
		return "{\"D\":\"1\",\"NAME\":\"" + name + "\"}";
	}
	public boolean equals(Location l) {
		if(l instanceof ChefHandLocation) {
			if(name.equals(((ChefHandLocation) l).getName())) {
				return true;
			}
		}
		return false;
	}

}
