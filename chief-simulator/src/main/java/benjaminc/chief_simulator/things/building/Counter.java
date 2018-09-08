package benjaminc.chief_simulator.things.building;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import benjaminc.chief_simulator.graphics.building.GraphicalCounter;
import benjaminc.chief_simulator.things.DataMapKey;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.SolidThing;

public class Counter implements SolidThing {
	
	protected GraphicalCounter graphics;
	Map<DataMapKey, Object> dataMap;
	
	public Counter() {
		this(0);
	}
	public Counter(int variant) {
		super();
		dataMap = new HashMap<DataMapKey, Object>();
		graphics = new GraphicalCounter(variant);
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
	}

	@Override
	public Thing duplicate() {
		return new Counter();
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
}
