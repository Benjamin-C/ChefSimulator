package benjaminc.chief_simulator.things.building;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import benjaminc.chief_simulator.graphics.building.GraphicalFloor;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.data.DataMapKey;
import benjaminc.chief_simulator.things.types.AttachedThing;

public class Floor implements AttachedThing {

	protected GraphicalFloor graphics;
	Map<DataMapKey, Object> dataMap;
	
	public Floor() {
		this(0);
	}
	public Floor(int var) {
		graphics = new GraphicalFloor(var);
		dataMap = new HashMap<DataMapKey, Object>();
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
	public Map<DataMapKey, Object> getDataMap() {
		return dataMap;
	}
}
