package benjaminc.chef_simulator.graphics;

import java.util.ArrayList;
import java.util.List;

import benjaminc.chef_utils.graphics.Shape;

public class Texture {
	
	private List<Shape> txtr;
	
	public Texture() {
		txtr = new ArrayList<Shape>();
	}
	
	public void add(Shape s) {
		txtr.add(s);
	}
	
	public Shape get(int n) {
		return txtr.get(n);
	}
	
	public int size() {
		return txtr.size();
	}
	
	public List<Shape> getList() {
		return txtr;
	}
}
