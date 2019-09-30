package benjaminc.chef_simulator.things.building;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.Cookable;
import benjaminc.chef_simulator.things.types.SolidThing;
import benjaminc.chef_simulator.things.types.ToolThing;

public class Fryer extends BasicThing implements ToolThing, SolidThing {

	protected final static int VARIANT_COUNT = 1;
	public Fryer() {
		this(null);
	}
	public Fryer(DataMap dataMap) {
		super(dataMap, VARIANT_COUNT, Fryer.class);
	}
	
//	@Override
//	public void draw(Graphics g, int x, int y, int w, int h) {
//		graphics.draw(g, x, y, w, h);
//	}
	
	@Override
	public List<Thing> useTool(Thing t) {
		List<Thing> temp = new ArrayList<Thing>();
		if(t instanceof Cookable) {
			temp.add(((Cookable) t).getCookedThing());
		} else {
			temp.add(t);
		}
		return temp;
	}
	
	@Override
	public boolean isSame(Thing t) {
		if(t.getClass() == this.getClass()) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public DataMap getDataMap() {
		return dataMap;
	}
}
