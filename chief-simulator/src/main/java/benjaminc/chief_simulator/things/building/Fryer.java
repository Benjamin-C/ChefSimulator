package benjaminc.chief_simulator.things.building;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import benjaminc.chief_simulator.graphics.building.GraphicalFryer;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.data.DataMapKey;
import benjaminc.chief_simulator.things.data.DataMapValue;
import benjaminc.chief_simulator.things.types.Cookable;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.ToolThing;

public class Fryer implements ToolThing, SolidThing {

	protected GraphicalFryer graphics;
	protected Map<DataMapKey, DataMapValue> dataMap;
	
	public Fryer() {
		this(0);
	}
	public Fryer(int var) {
		super();
		graphics = new GraphicalFryer(dataMap);
		
		dataMap = new HashMap<DataMapKey, DataMapValue>();
		dataMap.put(DataMapKey.VARIANT, new DataMapValue(var));
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
	}
	
	@Override
	public List<Thing> useTool(Thing t) {
		List<Thing> temp = new ArrayList<Thing>();
		if(t instanceof Cookable) {
			temp.add(((Cookable) t).getCookedThing());
		} else {
			temp.add(t);
		}
		return temp;
	}

	@Override
	public Thing duplicate() {
		return new Fryer();
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
