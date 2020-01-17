package benjaminc.chef_simulator.things.building;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import benjaminc.chef_simulator.control.Direction;
import benjaminc.chef_simulator.control.EventHandler;
import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.DataMap.DataMapKey;
import benjaminc.chef_simulator.events.ThingMoveEvent;
import benjaminc.chef_simulator.graphics.GameSpace;
import benjaminc.chef_simulator.rooms.Room;
import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.AttachedThing;
import benjaminc.chef_simulator.things.types.DirectionalThing;
import benjaminc.chef_simulator.things.types.SolidThing;
import benjaminc.chef_simulator.things.types.Tickable;

public class Belt extends BasicThing implements SolidThing, DirectionalThing, Tickable {
	
	// Not to be saved, only to be used
	protected static int movedel = 100; 
	protected List<Thing> toMove;
	
	protected final static int VARIANT_COUNT = 1;
	public Belt() {
		this(null, null);
	}
	public Belt(DataMap dataMap, UUID uuid) {
		super(dataMap, Belt.class, uuid);
	}
	public Belt(DataMap dataMap) {
		super(dataMap, VARIANT_COUNT, Belt.class);
		System.out.println(this.dataMap);
		this.dataMap.put(DataMapKey.LAST_MOVE_FRAME, 0d);
	}
	public Belt(Direction d) {
		this((DataMap) null);
		dataMap.put(DataMapKey.DIRECTION, d);
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
	public void setDirection(Direction d) {
		dataMap.put(DataMapKey.DIRECTION, d);
	}
	@Override
	public Direction getDirection() {
		return (Direction) dataMap.get(DataMapKey.DIRECTION);
	}
	@Override
	public void tick(Room r, Location l, double frame) {
		if(dataMap.containsKey(DataMapKey.LAST_MOVE_FRAME)) {
			if ((Double) dataMap.get(DataMapKey.LAST_MOVE_FRAME) != frame) {
				dataMap.put(DataMapKey.LAST_MOVE_FRAME, frame);
				GameSpace gs = r.getSpace(l);
				GameSpace ngs = r.getSpace(l.add((Direction) dataMap.get(DataMapKey.DIRECTION)));
				toMove = new ArrayList<Thing>();
				for(Thing t : gs.getThings()) {
					if(t != null) {
						if(!(t instanceof AttachedThing)) {
							if(!t.getDataMap().containsKey(DataMapKey.LAST_MOVE_FRAME)) {
								t.getDataMap().put(DataMapKey.LAST_MOVE_FRAME, 0d);
								System.err.println("LAST_MOVE_FRAME on " + t + " did not exist, so I created it");
							}
							if((Double) t.getDataMap().get(DataMapKey.LAST_MOVE_FRAME) + movedel < System.currentTimeMillis()) {
								toMove.add(t);
								t.getDataMap().put(DataMapKey.LAST_MOVE_FRAME, (double) System.currentTimeMillis());
							}
						}
					}
				}
				for(Thing t : toMove) {
					EventHandler.fireEvent(new ThingMoveEvent(t, gs.getLoc().as3d(), ngs.getLoc().as3d()));
				}
			}
		} else {
			System.out.println("No LastMoveFrame, making it for " + dataMap);
			dataMap.put(DataMapKey.LAST_MOVE_FRAME, frame);
		}
	}
	@Override
	public DataMap getDataMap() {
		return dataMap;
	}
}
