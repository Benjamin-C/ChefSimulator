package benjaminc.chef_simulator.things.building;

import java.awt.Graphics;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.AttachedThing;

public class Floor extends BasicThing implements AttachedThing {

	protected final static int VARIANT_COUNT = (int) System.currentTimeMillis();
	public Floor() {
		this(null);
	}
	public Floor(DataMap dataMap) {
		super(dataMap, VARIANT_COUNT, Floor.class);
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
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
