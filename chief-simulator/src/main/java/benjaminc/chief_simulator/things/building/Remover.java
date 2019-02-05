package benjaminc.chief_simulator.things.building;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import benjaminc.chief_simulator.graphics.building.GraphicalRemover;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.data.DataMapKey;
import benjaminc.chief_simulator.things.data.DataMapValue;
import benjaminc.chief_simulator.things.types.ContainerThing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.ToolThing;

public class Remover implements ToolThing, SolidThing {

	protected GraphicalRemover graphics;
	protected Map<DataMapKey, DataMapValue> dataMap;
	
	public Remover() {
		this(0);
	}
	public Remover(int var) {
		super();
		graphics = new GraphicalRemover(dataMap);
		
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
		if(t instanceof ContainerThing) {
			ContainerThing th = (ContainerThing) t;
			temp.addAll(th.getItems());
			for(Thing tm : temp) {
				th.removeItem(tm);
			}
		}
		temp.add(t);
		return temp;
	}

	@Override
	public Thing duplicate() {
		return new Remover();
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
