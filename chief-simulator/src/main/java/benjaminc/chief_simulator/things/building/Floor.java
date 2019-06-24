package benjaminc.chief_simulator.things.building;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import benjaminc.chief_simulator.data.DataMapKey;
import benjaminc.chief_simulator.data.DataMapValue;
import benjaminc.chief_simulator.graphics.building.GraphicalFloor;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.AttachedThing;

public class Floor implements AttachedThing {

	protected GraphicalFloor graphics;
	protected Map<DataMapKey, DataMapValue> dataMap;
	
	public Floor() {
		this(0);
	}
	public Floor(int var) {
		super();
		graphics = new GraphicalFloor(dataMap);
		dataMap = new HashMap<DataMapKey, DataMapValue>();
		dataMap.put(DataMapKey.VARIANT, new DataMapValue(var));
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
	}

	@Override
	public Thing duplicate() {
		return new Floor();
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
