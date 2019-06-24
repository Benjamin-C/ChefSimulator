package benjaminc.chief_simulator.things.building;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import benjaminc.chief_simulator.data.DataMapKey;
import benjaminc.chief_simulator.data.DataMapValue;
import benjaminc.chief_simulator.graphics.building.GraphicalCounter;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.SolidThing;

public class Counter implements SolidThing {
	
	protected GraphicalCounter graphics;
	protected Map<DataMapKey, DataMapValue> dataMap;
	
	public Counter() {
		this(0);
	}
	public Counter(int var) {
		super();
		graphics = new GraphicalCounter(dataMap);
		
		dataMap = new HashMap<DataMapKey, DataMapValue>();
		dataMap.put(DataMapKey.VARIANT, new DataMapValue(var));
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
	public Map<DataMapKey, DataMapValue> getDataMap() {
		return dataMap;
	}
}
