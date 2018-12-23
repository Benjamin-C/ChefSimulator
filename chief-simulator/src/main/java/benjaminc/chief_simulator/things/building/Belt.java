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
import benjaminc.chief_simulator.things.DataMapKey;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.AttachedThing;
import benjaminc.chief_simulator.things.types.DirectionalThing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.Tickable;

public class Belt implements SolidThing, DirectionalThing, Tickable {
	
	protected GraphicalBelt graphics;
	protected Direction dir;
	long lastFrame;
	List<Thing> toMove;
	Map<DataMapKey, Object> dataMap;
	
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
		dataMap = new HashMap<DataMapKey, Object>();
		dir = d;
		graphics = new GraphicalBelt(variant, dir);
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
		dir = d;
		graphics.setDirection(d);
	}
	@Override
	public Direction getDirection() {
		return dir;
	}
	@Override
	public void tick(Room r, Location l, long frame, Game g) {
		if(lastFrame != frame) {
			lastFrame = frame;
			GameSpace gs = r.getSpace(l);
			GameSpace ngs = r.getSpace(l.add(dir));
			toMove = new ArrayList<Thing>();
			for(Thing t : gs.getThings()) {
				if(!(t instanceof AttachedThing)) {
					if(!t.getDataMap().containsKey(DataMapKey.LAST_MOVE_FRAME)) {
						t.getDataMap().put(DataMapKey.LAST_MOVE_FRAME, 0L);
						System.err.println("LAST_MOVE_FRAME on " + t + " did not exist, so I created it");
					}
					Object data = t.getDataMap().get(DataMapKey.LAST_MOVE_FRAME);
					if(data instanceof Long) {
						if(!((Long) data).equals(new Long(frame))) {
							toMove.add(t);
							t.getDataMap().put(DataMapKey.LAST_MOVE_FRAME, frame);
						}
					} else {
						String oldtype = t.getDataMap().get(DataMapKey.LAST_MOVE_FRAME).getClass().toString();
						t.getDataMap().put(DataMapKey.LAST_MOVE_FRAME, 0);
						System.err.println("LAST_MOVE_FRAME on " + t + " was the wrong type (" + oldtype + "), so I changed it");
					}
				}
			}
			for(Thing t : toMove) {
				ngs.addThing(gs.removeThing(t));
			}
		}
	}
	@Override
	public Map<DataMapKey, Object> getDataMap() {
		return dataMap;
	}
}
