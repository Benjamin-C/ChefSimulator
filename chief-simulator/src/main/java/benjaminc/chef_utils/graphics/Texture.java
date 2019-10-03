package benjaminc.chef_utils.graphics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import benjaminc.chef_utils.data.FoodState;

public class Texture {
	
	private Map<FoodState, List<Shape>> txtr;
	
	public Texture() {
		txtr = new HashMap<FoodState, List<Shape>>();
	}
	public Texture(Map<FoodState, List<Shape>> txtr) {
		this.txtr = txtr;
	}
	
	public void put(FoodState state, Shape s) {
		
	}
	public void put(FoodState state, List<Shape> s) {
		txtr.put(state, s);
	}
	
	/**
	 * Gets the List<Shape> representation of this state of the texture
	 * @param state the FoodState to get
	 * @return the List<Shape> of that food state
	 */
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
	
	public void printAll() {
		for(FoodState f : FoodState.values()) {
			System.out.println(f + " " + get(f));
		}
	}
}
