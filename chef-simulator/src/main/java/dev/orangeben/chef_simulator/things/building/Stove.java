package dev.orangeben.chef_simulator.things.building;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dev.orangeben.chef_simulator.data.DataMap;
import dev.orangeben.chef_simulator.things.BasicThing;
import dev.orangeben.chef_simulator.things.Thing;
import dev.orangeben.chef_simulator.things.types.CookwareThing;
import dev.orangeben.chef_simulator.things.types.SolidThing;
import dev.orangeben.chef_simulator.things.types.ToolThing;

public class Stove extends BasicThing implements ToolThing, SolidThing {

	protected final static int VARIANT_COUNT = 1;
	public Stove() {
		this(null, null);
	}
	public Stove(DataMap dataMap, UUID uuid) {
		super(dataMap, Stove.class, uuid);
	}
	
//	@Override
//	public void draw(Graphics g, int x, int y, int w, int h) {
//		graphics.draw(g, x, y, w, h);
//	}
	
	@Override
	public List<Thing> useTool(Thing t) {
		List<Thing> temp = new ArrayList<Thing>();
		if(t instanceof CookwareThing) {
			temp.add(((CookwareThing) t).getCookedThing());
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
