package benjaminc.chief_simulator.things.building;

import java.awt.Graphics;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import benjaminc.chief_simulator.Game;
import benjaminc.chief_simulator.control.Direction;
import benjaminc.chief_simulator.control.Location;
import benjaminc.chief_simulator.graphics.GameSpace;
import benjaminc.chief_simulator.graphics.Room;
import benjaminc.chief_simulator.graphics.building.GraphicalBelt;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.data.DataMapKey;
import benjaminc.chief_simulator.things.data.DataMapValue;
import benjaminc.chief_simulator.things.types.AttachedThing;
import benjaminc.chief_simulator.things.types.DirectionalThing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.Tickable;

public class Belt implements SolidThing, DirectionalThing, Tickable {
	
	protected GraphicalBelt graphics;
	protected Map<DataMapKey, DataMapValue> dataMap;
	
	// Not to be saved, only to be used
	protected static int movedel = 100; 
	protected List<Thing> toMove;
	
	public Belt() {
		this(0, Direction.UP);
	}
	public Belt(int v) {
		this(v, Direction.UP);
	}
	public Belt(Direction d) {
		this(0, d);
	}
	public Belt(int variant, Direction d) {
		super();
		dataMap = new HashMap<DataMapKey, DataMapValue>();
		dataMap.put(DataMapKey.DIRECTION, new DataMapValue(d));
		dataMap.put(DataMapKey.VARIANT, new DataMapValue(variant));
		graphics = new GraphicalBelt(dataMap);
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
	}

	@Override
	public Thing duplicate() {
		return new Belt();
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
		dataMap.get(DataMapKey.DIRECTION).update(d);
	}
	@Override
	public Direction getDirection() {
		return dataMap.get(DataMapKey.DIRECTION).getDirection();
	}
	@Override
	public void tick(Room r, Location l, double frame, Game g) {
		if(dataMap.containsKey(DataMapKey.LAST_MOVE_FRAME) &&
				dataMap.get(DataMapKey.LAST_MOVE_FRAME).getDouble() != frame) {
			dataMap.get(DataMapKey.LAST_MOVE_FRAME).update(frame);;
			GameSpace gs = r.getSpace(l);
			GameSpace ngs = r.getSpace(l.add(dataMap.get(DataMapKey.DIRECTION).getDirection()));
			toMove = new ArrayList<Thing>();
			for(Thing t : gs.getThings()) {
				if(!(t instanceof AttachedThing)) {
					if(!t.getDataMap().containsKey(DataMapKey.LAST_MOVE_FRAME)) {
						t.getDataMap().put(DataMapKey.LAST_MOVE_FRAME, new DataMapValue(0d));
						System.err.println("LAST_MOVE_FRAME on " + t + " did not exist, so I created it");
					}
					if(t.getDataMap().get(DataMapKey.LAST_MOVE_FRAME).getDouble() + movedel < System.currentTimeMillis()) {
						toMove.add(t);
						t.getDataMap().get(DataMapKey.LAST_MOVE_FRAME).update((double) System.currentTimeMillis());
					}
				} else {
					String oldtype = t.getDataMap().get(DataMapKey.LAST_MOVE_FRAME).getClass().toString();
					t.getDataMap().put(DataMapKey.LAST_MOVE_FRAME, new DataMapValue(0d));
					System.err.println("LAST_MOVE_FRAME on " + t + " was the wrong type (" + oldtype + "), so I changed it");
				}
			}
			for(Thing t : toMove) {
				ngs.addThing(gs.removeThing(t));
			}
		}
	}
	@Override
	public Map<DataMapKey, DataMapValue> getDataMap() {
		return dataMap;
	}
}
