package dev.benjaminc.chef_simulator.things.building;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dev.benjaminc.chef_simulator.data.DataMap;
import dev.benjaminc.chef_simulator.things.BasicThing;
import dev.benjaminc.chef_simulator.things.Thing;
import dev.benjaminc.chef_simulator.things.types.Choppable;
import dev.benjaminc.chef_simulator.things.types.SolidThing;
import dev.benjaminc.chef_simulator.things.types.ToolThing;

public class CuttingBoard extends BasicThing implements ToolThing, SolidThing {

	protected final static int VARIANT_COUNT = 1;
	public CuttingBoard() {
		this(null, null);
	}
	public CuttingBoard(DataMap dataMap, UUID uuid) {
		super(dataMap, CuttingBoard.class, uuid);
	}
	
//	@Override
//	public void draw(Graphics g, int x, int y, int w, int h) {
//		graphics.draw(g, x, y, w, h);
//	}
	
	@Override
	public List<Thing> useTool(Thing t) {
		List<Thing> temp = new ArrayList<Thing>();
		if(t instanceof Choppable) {
			temp.add(((Choppable) t).getChoppedThing());
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
