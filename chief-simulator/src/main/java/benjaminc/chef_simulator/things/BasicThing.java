package benjaminc.chef_simulator.things;

import java.util.Random;

import benjaminc.chef_simulator.control.Direction;
import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.DataMapKey;
import benjaminc.chef_simulator.graphics.GraphicalLoader;
//import benjaminc.chef_simulator.graphics.GraphicalThing;
import benjaminc.chef_utils.data.FoodState;

public class BasicThing implements Thing, Cloneable {

	//protected GraphicalThing graphics;
	protected DataMap dataMap;
	protected Class<?> subclass;
	
	public BasicThing(DataMap dataMap, Class<?> subclass) {
		this.subclass = subclass;
		this.dataMap = dataMap;
		if(this.dataMap == null) {
			this.dataMap = new DataMap();
		}
		if(!this.dataMap.containsKey(DataMapKey.VARIANT_COUNT)) {
			this.dataMap.put(DataMapKey.VARIANT_COUNT, 1);
		}
		if(!this.dataMap.containsKey(DataMapKey.VARIANT)) {
			Random r = new Random();
			this.dataMap.put(DataMapKey.VARIANT_COUNT, r.nextInt((int) this.dataMap.get(DataMapKey.VARIANT_COUNT)));
		}
		if(!this.dataMap.containsKey(DataMapKey.DIRECTION)) {
			this.dataMap.put(DataMapKey.DIRECTION, Direction.UP);
		}
		if(!this.dataMap.containsKey(DataMapKey.FOOD_STATE)) {
			this.dataMap.put(DataMapKey.FOOD_STATE, FoodState.RAW);
		}
		if(subclass != null) {
			String pkg = this.subclass.getPackage().getName();
			pkg = pkg.substring(pkg.lastIndexOf(".")+1);
			this.dataMap.put(DataMapKey.GRAPHICS, GraphicalLoader.load(pkg + "/" + this.subclass.getSimpleName()));
		}
		
	}
	public BasicThing(DataMap dataMap, int variantCount, Class<?> subclass) {
		this(dataMap, subclass);
		this.dataMap.put(DataMapKey.VARIANT_COUNT, variantCount);
	}
	public BasicThing(int variant, FoodState state, int variantCount, Class<?> subclass) {
		this(null, variantCount, subclass);
		dataMap.put(DataMapKey.VARIANT, variant);
		if(state != null) {
			dataMap.put(DataMapKey.FOOD_STATE, state);
		} else {
			dataMap.put(DataMapKey.FOOD_STATE, FoodState.RAW);
		}
	}
	
	@Override
	public boolean equals(Thing t) {
		if(t instanceof BasicThing) {
			BasicThing bt = (BasicThing) t;
			if(bt.getSubclass().equals(subclass)) {
				if(dataMap.equals(t.getDataMap())) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public Thing clone() {
		try {
			Thing t = (Thing) super.clone();
			t.setDataMap(dataMap.clone());
			return t;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		//return new BasicThing(dataMap.clone(), subclass);
	}
//	@Override
//	public void draw(Graphics g, int x, int y, int w, int h) {
//		//graphics.draw(g, x, y, w, h);
//	}
	@Override
	public boolean isSame(Thing t) {
		System.out.println("BasicThingSame?");
		if(!t.getClass().equals(getClass())) {
			System.out.println("Class does not match");
			return false;
		} else {
			if(! t.getDataMap().equals(dataMap)) {
				System.out.println("Data map does not match");
				return false;
			}
		}
		System.out.println("BasicThing is same");
		return true;
	}
	@Override
	public DataMap getDataMap() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setDataMap(DataMap m) {
		this.dataMap = m;
	}
	@Override
	public String getName() {
		return subclass.getSimpleName();
	}
	private Class<?> getSubclass() {
		return subclass;
	}
}
