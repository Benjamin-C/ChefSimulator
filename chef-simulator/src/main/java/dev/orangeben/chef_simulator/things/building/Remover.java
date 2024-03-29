package dev.orangeben.chef_simulator.things.building;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dev.orangeben.chef_simulator.data.DataMap;
import dev.orangeben.chef_simulator.things.BasicThing;
import dev.orangeben.chef_simulator.things.Thing;
import dev.orangeben.chef_simulator.things.types.ContainerThing;
import dev.orangeben.chef_simulator.things.types.SolidThing;
import dev.orangeben.chef_simulator.things.types.ToolThing;

public class Remover extends BasicThing implements ToolThing, SolidThing {

	protected final static int VARIANT_COUNT = 1;
	public Remover() {
		this(null, null);
	}
	public Remover(DataMap dataMap, UUID uuid) {
		super(dataMap, Remover.class, uuid);
	}
	
//	@Override
//	public void draw(Graphics g, int x, int y, int w, int h) {
//		graphics.draw(g, x, y, w, h);
//	}
	
	@Override
	public List<Thing> useTool(Thing t) {
		List<Thing> temp = new ArrayList<Thing>();
		if(t instanceof ContainerThing) {
			for(Thing tm : ((ContainerThing) t).getItems().clear()) {
				temp.add(tm);
			}
		}
		temp.add(t);
		System.out.println(t);
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
