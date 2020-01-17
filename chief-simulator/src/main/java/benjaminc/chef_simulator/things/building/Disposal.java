package benjaminc.chef_simulator.things.building;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.DataMap.DataMapKey;
import benjaminc.chef_simulator.events.OnDisposeEvent;
import benjaminc.chef_simulator.rooms.Room;
import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.AttachedThing;
import benjaminc.chef_simulator.things.types.PersistentThing;
import benjaminc.chef_simulator.things.types.SolidThing;
import benjaminc.chef_simulator.things.types.Tickable;

/**
 * A disposal to remove Things
 * extends BasicThing implements SolidThing, Tickable
 * @author Benjamin-C
 *
 */
public class Disposal extends BasicThing implements SolidThing, Tickable {

	protected final static int VARIANT_COUNT = 1;
	public Disposal() {
		this(null, null);
	}
	public Disposal(DataMap dataMap, UUID uuid) {
		super(dataMap, Disposal.class, uuid);
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
	public void tick(Room r, Location l, double frame) {
		List<Thing> stuff = r.getThings(l);
		List<Thing> toRemove = new ArrayList<Thing>();
		for(Thing t : stuff) {
			if(!(t instanceof AttachedThing)) {
				if(!t.getDataMap().containsKey(DataMapKey.LAST_MOVE_FRAME)) {
					t.getDataMap().put(DataMapKey.LAST_MOVE_FRAME, 0d);
					System.err.println("LAST_MOVE_FRAME on " + t + " did not exist, so I created it");
				}
				Object data = t.getDataMap().get(DataMapKey.LAST_MOVE_FRAME);
				if(((Double) data).longValue() != frame) {
					if(!(t instanceof PersistentThing)) {
						toRemove.add(t);
						t.getDataMap().put(DataMapKey.LAST_MOVE_FRAME, frame);
					} else {
						t.getDataMap().put(DataMapKey.LAST_MOVE_FRAME, frame);
						((PersistentThing) t).onDispose(new OnDisposeEvent() { @Override public void setCanceled(boolean cancel) { } });
					}
				}
			}
		}
		for(Thing t : toRemove) {
			r.getSpace(l).removeThing(t);
		}
	}
}
