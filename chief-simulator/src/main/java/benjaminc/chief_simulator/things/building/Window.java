package benjaminc.chief_simulator.things.building;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import benjaminc.chief_simulator.Objective;
import benjaminc.chief_simulator.data.DataMap;
import benjaminc.chief_simulator.graphics.Room;
import benjaminc.chief_simulator.things.BasicThing;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.ToolThing;
import benjaminc.util.Util;

public class Window extends BasicThing implements ToolThing, Thing, SolidThing{
	
	protected Room room;
	protected final static int VARIANT_COUNT = 1;
	public Window(Room r) {
		this(r, null);
	}
	public Window(Room r, DataMap dataMap) {
		super(dataMap, VARIANT_COUNT, Window.class);
		room = r;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
	}

	@Override
	public Thing duplicate() {
		return new Window(room);
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
	public List<Thing> useTool(Thing t) {
		for(int i = 0; i < room.getObjectives().size(); i++) {
			Objective o = room.getObjectives().get(i);
			if(o.isMet(t)) {
				//System.out.println("isMet");
				room.getScore().addScore(o.getScore());
				room.getObjectives().remove(i);
				if(room.getObjectives().size() == 0) {
					Util.resume(room.getSyncObj());
				}
				//System.out.println(game.getObjectives().size());
				return null;
			}
		}
		List<Thing> li = new ArrayList<Thing>();
		li.add(t);
		return li;
	}
	@Override
	public DataMap getDataMap() {
		return dataMap;
	}
}
