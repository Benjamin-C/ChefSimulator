package benjaminc.chef_utils.graphics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import benjaminc.chef_simulator.data.FoodState;

public class Texture {
	
	private Map<FoodState, List<Shape>> txtr;
	private String fileName = null;
	
	public Texture() {
		this((String) null);
	}
	public Texture(String name) {
		txtr = new HashMap<FoodState, List<Shape>>();
	}
	public Texture(Map<FoodState, List<Shape>> txtr) {
		this(txtr, null);
	}
	public Texture(Map<FoodState, List<Shape>> txtr, String name) {
		this.txtr = txtr;
		this.fileName = name;
	}
	
	public void put(FoodState state, Shape s) {
		System.out.println("This texture adding method is not supported");
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
	
	@Override
	public String toString() {
		if(fileName != null) {
			return "Texture[" + fileName + "]";
		} else {
			return "Texture[" + super.toString() + "]";
		}
	}
}
