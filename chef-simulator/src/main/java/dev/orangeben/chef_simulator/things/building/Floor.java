package dev.orangeben.chef_simulator.things.building;

import java.util.UUID;

import dev.orangeben.chef_simulator.data.DataMap;
import dev.orangeben.chef_simulator.things.BasicThing;
import dev.orangeben.chef_simulator.things.Thing;
import dev.orangeben.chef_simulator.things.types.AttachedThing;

public class Floor extends BasicThing implements AttachedThing {

	protected final static int VARIANT_COUNT = (int) System.currentTimeMillis();
	public Floor() {
		this(null, null);
	}
	public Floor(DataMap dataMap, UUID uuid) {
		super(dataMap, Floor.class, uuid);
	}

//	@Override
//	public void draw(Graphics g, int x, int y, int w, int h) {
//		graphics.draw(g, x, y, w, h);
//	}
	
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
