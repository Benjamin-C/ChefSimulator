package benjaminc.chef_simulator.graphics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import benjaminc.chef_simulator.data.FoodState;
import benjaminc.chef_utils.graphics.Shape;

public class Texture {
	
	private Map<FoodState, List<Shape>> txtr;
	
	public Texture() {
		txtr = new HashMap<FoodState, List<Shape>>();
	}
	
	public void put(FoodState state, List<Shape> s) {
		txtr.put(state, s);
	}
	
	public List<Shape> get(FoodState state) {
		if(txtr.containsKey(state)) {
			return txtr.get(state);
		}
		return null;
	}
	
	public int size() {
		return txtr.size();
	}
	
	public Map<FoodState, List<Shape>> getList() {
		return txtr;
	}
}
