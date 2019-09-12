package benjaminc.chief_simulator.things.building;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import benjaminc.chief_simulator.data.DataMap;
import benjaminc.chief_simulator.data.DataMapKey;
import benjaminc.chief_simulator.things.BasicThing;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.ToolThing;

public class Spawner extends BasicThing implements ToolThing, SolidThing {

	protected final static int VARIANT_COUNT = 1;
	public Spawner(DataMap dataMap) {
		super(dataMap, VARIANT_COUNT, Spawner.class);
	}
	public Spawner(Thing t) {
		this((DataMap) null);
		dataMap.put(DataMapKey.MAKES, t);
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		int indmkw = w / 4;
		int indmkh = h / 4;
		graphics.draw(g, x, y, w, h);
		((Thing) dataMap.get(DataMapKey.MAKES)).draw(g, x+indmkw,  y+indmkh,  w-(2*indmkw),  h-(2*indmkh));
	}

	@Override
	public List<Thing> useTool(Thing t) {
		List<Thing> temp = new ArrayList<Thing>();
		if(t == null) {
			temp.add(((Thing) dataMap.get(DataMapKey.MAKES)).clone());
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
}
