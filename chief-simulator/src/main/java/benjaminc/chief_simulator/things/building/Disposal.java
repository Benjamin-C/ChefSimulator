package benjaminc.chief_simulator.things.building;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import benjaminc.chief_simulator.graphics.building.GraphicalDisposal;
import benjaminc.chief_simulator.things.DataMapKey;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.ToolThing;

public class Disposal implements SolidThing, ToolThing{

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
}
