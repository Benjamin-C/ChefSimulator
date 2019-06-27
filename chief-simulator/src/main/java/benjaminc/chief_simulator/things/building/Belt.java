package benjaminc.chief_simulator.things.building;

import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;

import benjaminc.chief_simulator.Game;
import benjaminc.chief_simulator.control.Direction;
import benjaminc.chief_simulator.control.Location;
import benjaminc.chief_simulator.data.DataMap;
import benjaminc.chief_simulator.data.DataMapKey;
import benjaminc.chief_simulator.graphics.GameSpace;
import benjaminc.chief_simulator.graphics.Room;
import benjaminc.chief_simulator.things.BasicThing;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.AttachedThing;
import benjaminc.chief_simulator.things.types.DirectionalThing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.Tickable;

public class Belt extends BasicThing implements SolidThing, DirectionalThing, Tickable {
	
	// Not to be saved, only to be used
	protected static int movedel = 100; 
	protected List<Thing> toMove;
	
	protected final static int VARIANT_COUNT = 1;
	public Belt() {
		this((DataMap) null);
	}
	public Belt(DataMap dataMap) {
		super(dataMap, VARIANT_COUNT, Belt.class);
	}
	public Belt(Direction d) {
		this((DataMap) null);
		dataMap.put(DataMapKey.DIRECTION, d);
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
	}

	@Override
	public Thing duplicate() {
		return new Belt(dataMap.clone());
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
	public void tick(Room r, Location l, double frame, Game g) {
		if(dataMap.containsKey(DataMapKey.LAST_MOVE_FRAME) &&
				(Double) dataMap.get(DataMapKey.LAST_MOVE_FRAME) != frame) {
			dataMap.put(DataMapKey.LAST_MOVE_FRAME, frame);;
			GameSpace gs = r.getSpace(l);
			GameSpace ngs = r.getSpace(l.add((Direction) dataMap.get(DataMapKey.DIRECTION)));
			toMove = new ArrayList<Thing>();
			for(Thing t : gs.getThings()) {
				if(!(t instanceof AttachedThing)) {
					if(!t.getDataMap().containsKey(DataMapKey.LAST_MOVE_FRAME)) {
						t.getDataMap().put(DataMapKey.LAST_MOVE_FRAME, 0d);
						System.err.println("LAST_MOVE_FRAME on " + t + " did not exist, so I created it");
					}
					if((Double) t.getDataMap().get(DataMapKey.LAST_MOVE_FRAME) + movedel < System.currentTimeMillis()) {
						toMove.add(t);
						t.getDataMap().put(DataMapKey.LAST_MOVE_FRAME, (double) System.currentTimeMillis());
					}
				} else {
					String oldtype = t.getDataMap().get(DataMapKey.LAST_MOVE_FRAME).getClass().toString();
					t.getDataMap().put(DataMapKey.LAST_MOVE_FRAME, 0d);
					System.err.println("LAST_MOVE_FRAME on " + t + " was the wrong type (" + oldtype + "), so I changed it");
				}
			}
			for(Thing t : toMove) {
				ngs.addThing(gs.removeThing(t));
			}
		}
	}
	@Override
	public DataMap getDataMap() {
		return dataMap;
	}
}
