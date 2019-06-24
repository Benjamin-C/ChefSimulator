package benjaminc.chief_simulator.things.building;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import benjaminc.chief_simulator.data.DataMapKey;
import benjaminc.chief_simulator.data.DataMapValue;
import benjaminc.chief_simulator.graphics.building.GraphicalSpawner;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.ToolThing;

public class Spawner implements ToolThing, SolidThing {

	// TODO Figure out how to save toMake
	//protected Thing toMake;
	protected GraphicalSpawner graphics;
	Map<DataMapKey, DataMapValue> dataMap;
	
	public Spawner(Thing t) {
		this(t, 0);
	}
	public Spawner(Thing t, int var) {
		graphics = new GraphicalSpawner(dataMap);
		dataMap = new HashMap<DataMapKey, DataMapValue>();
		dataMap.put(DataMapKey.VARIANT, new DataMapValue(var));
		dataMap.put(DataMapKey.MAKES, new DataMapValue(t));
	}
	public Spawner(Map<DataMapKey, DataMapValue> data) {
		dataMap = data;
		graphics = new GraphicalSpawner(dataMap);
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		int indmkw = w / 4;
		int indmkh = h / 4;
		graphics.draw(g, x, y, w, h);
		dataMap.get(DataMapKey.MAKES).getThing().draw(g, x+indmkw,  y+indmkh,  w-(2*indmkw),  h-(2*indmkh));
	}

	@Override
	public List<Thing> useTool(Thing t) {
		List<Thing> temp = new ArrayList<Thing>();
		if(t == null) {
			temp.add(dataMap.get(DataMapKey.MAKES).getThing().duplicate());
		}
		temp.add(t);
		return temp;
	}

	@Override
	public Thing duplicate() {
		return new Spawner(dataMap);
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
