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
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.data.DataMapKey;
import benjaminc.chief_simulator.things.data.DataMapValue;
import benjaminc.chief_simulator.things.types.AttachedThing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.Tickable;
import benjaminc.chief_simulator.things.types.ToolThing;

public class Disposal implements SolidThing, ToolThing, Tickable {

	protected GraphicalDisposal graphics;
	protected Map<DataMapKey, DataMapValue> dataMap;
	
	public Disposal() {
		this(0);
	}
	public Disposal(int var) {
		super();
		dataMap = new HashMap<DataMapKey, DataMapValue>();
		dataMap.put(DataMapKey.VARIANT, new DataMapValue(var));
		graphics = new GraphicalDisposal(dataMap);
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
	public Map<DataMapKey, DataMapValue> getDataMap() {
		return dataMap;
	}
	@Override
	public void tick(Room r, Location l, double frame, Game g) {
		List<Thing> stuff = r.getThings(l);
		List<Thing> toRemove = new ArrayList<Thing>();
		for(Thing t : stuff) {
				if(!(t instanceof AttachedThing)) {
				if(!t.getDataMap().containsKey(DataMapKey.LAST_MOVE_FRAME)) {
					t.getDataMap().put(DataMapKey.LAST_MOVE_FRAME, new DataMapValue(0d));
					System.err.println("LAST_MOVE_FRAME on " + t + " did not exist, so I created it");
				}
				Object data = t.getDataMap().get(DataMapKey.LAST_MOVE_FRAME);
				if(((Long) data).longValue() != frame) {
					toRemove.add(t);
					t.getDataMap().get(DataMapKey.LAST_MOVE_FRAME).update(frame);
				}
			}
		}
		for(Thing t : toRemove) {
			r.getSpace(l).removeThing(t);
		}
	}
}
