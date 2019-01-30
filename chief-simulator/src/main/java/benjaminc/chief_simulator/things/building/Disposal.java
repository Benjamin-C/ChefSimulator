package benjaminc.chief_simulator.things.building;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import benjaminc.chief_simulator.Game;
import benjaminc.chief_simulator.control.Location;
import benjaminc.chief_simulator.graphics.Room;
import benjaminc.chief_simulator.graphics.building.GraphicalDisposal;
import benjaminc.chief_simulator.things.DataMapKey;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.AttachedThing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.Tickable;
import benjaminc.chief_simulator.things.types.ToolThing;

public class Disposal implements SolidThing, ToolThing, Tickable {

	protected GraphicalDisposal graphics;
	Map<DataMapKey, Object> dataMap;
	
	public Disposal() {
		this(0);
	}
	public Disposal(int var) {
		graphics = new GraphicalDisposal(var);
		dataMap = new HashMap<DataMapKey, Object>();
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
	}

	@Override
	public Thing duplicate() {
		return new Disposal();
	}

	@Override
	public List<Thing> useTool(Thing t) {
		return null;
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
	public Map<DataMapKey, Object> getDataMap() {
		return dataMap;
	}
	@Override
	public void tick(Room r, Location l, long frame, Game g) {
		List<Thing> stuff = r.getThings(l);
		List<Thing> toRemove = new ArrayList<Thing>();
		for(Thing t : stuff) {
				if(!(t instanceof AttachedThing)) {
				if(!t.getDataMap().containsKey(DataMapKey.LAST_MOVE_FRAME)) {
					t.getDataMap().put(DataMapKey.LAST_MOVE_FRAME, 0L);
					System.err.println("LAST_MOVE_FRAME on " + t + " did not exist, so I created it");
				}
				Object data = t.getDataMap().get(DataMapKey.LAST_MOVE_FRAME);
				if(!(data instanceof Long)) {
					String oldtype = t.getDataMap().get(DataMapKey.LAST_MOVE_FRAME).getClass().toString();
					t.getDataMap().put(DataMapKey.LAST_MOVE_FRAME, 0);
					System.err.println("LAST_MOVE_FRAME on " + t + " was the wrong type (" + oldtype + "), so I changed it");
				}
				if(((Long) data).longValue() != frame) {
					toRemove.add(t);
					t.getDataMap().put(DataMapKey.LAST_MOVE_FRAME, frame);
				}
			}
		}
		for(Thing t : toRemove) {
			r.getSpace(l).removeThing(t);
		}
	}
}
