package dev.benjaminc.chef_simulator.things.building;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dev.benjaminc.chef_simulator.control.Direction;
import dev.benjaminc.chef_simulator.data.DataMap;
import dev.benjaminc.chef_simulator.data.DataMap.DataMapKey;
import dev.benjaminc.chef_simulator.things.BasicThing;
import dev.benjaminc.chef_simulator.things.Thing;
import dev.benjaminc.chef_simulator.things.types.ContainerThing;
import dev.benjaminc.chef_simulator.things.types.DirectionalThing;
import dev.benjaminc.chef_simulator.things.types.SolidThing;
import dev.benjaminc.chef_simulator.things.types.ToolThing;
import dev.benjaminc.chef_simulator.things.types.WashableThing;

public class Dishwasher extends BasicThing implements ToolThing, SolidThing, DirectionalThing {

	protected final static int VARIANT_COUNT = 1;
	public Dishwasher() {
		this(null, (UUID) null);
	}
	public Dishwasher(DataMap dataMap, UUID uuid) {
		super(dataMap, Dishwasher.class, uuid);
	}
	public Dishwasher(Direction dir) {
		this(null, dir);
	}
	public Dishwasher(DataMap dm, Direction dir) {
		super(dm, VARIANT_COUNT, Dishwasher.class);
		if(dir != null) {
			dataMap.put(DataMapKey.DIRECTION, dir);
		} else {
			dataMap.put(DataMapKey.DIRECTION, Direction.UP);
		}
	}
	
	@Override
	public List<Thing> useTool(Thing t) {
		List<Thing> temp = new ArrayList<Thing>();
		if(t instanceof ContainerThing) {
			temp.addAll(extractThings(t));
		}
		
		for(Thing th : temp) {
			if(th instanceof WashableThing) {
				((WashableThing) th).wash();
			}
		}
		
		List<Thing> out = new ArrayList<Thing>();
		out.add(t);
		
		return out;
	}
	/**
	 * Gets all things from a container thing
	 * @param t
	 * @return
	 */
	private List<Thing> extractThings(Thing t) {
		List<Thing> temp = new ArrayList<Thing>();
		if(t instanceof ContainerThing) {
			for(Thing th : ((ContainerThing) t).getItems().getAll()) {
				if(th instanceof ContainerThing) {
					temp.addAll(extractThings(th));
				}
				temp.add(th);
			}
		}
		temp.add(t);
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
	
	@Override
	public void setDirection(Direction d) {
		dataMap.put(DataMapKey.DIRECTION, d);
	}
	@Override
	public Direction getDirection() {
		return (Direction) dataMap.get(DataMapKey.DIRECTION);
	}
}
