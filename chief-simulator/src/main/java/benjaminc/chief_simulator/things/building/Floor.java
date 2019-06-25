package benjaminc.chief_simulator.things.building;

import java.awt.Graphics;

import benjaminc.chief_simulator.data.DataMap;
import benjaminc.chief_simulator.things.BasicThing;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.AttachedThing;

public class Floor extends BasicThing implements AttachedThing {

	protected final static int VARIANT_COUNT = 1;
	public Floor() {
		this(null);
	}
	public Floor(DataMap dataMap) {
		super(dataMap, VARIANT_COUNT, Floor.class);
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
	}

	@Override
	public Thing duplicate() {
		return new Floor(dataMap.clone());
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
