package dev.benjaminc.chef_simulator.things.building;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dev.benjaminc.chef_simulator.chef_graphics.GraphicalDrawer;
import dev.benjaminc.chef_simulator.data.DataMap;
import dev.benjaminc.chef_simulator.data.FoodState;
import dev.benjaminc.chef_simulator.data.DataMap.DataMapKey;
import dev.benjaminc.chef_simulator.things.BasicThing;
import dev.benjaminc.chef_simulator.things.Thing;
import dev.benjaminc.chef_simulator.things.types.CustomDrawingThing;
import dev.benjaminc.chef_simulator.things.types.SolidThing;
import dev.benjaminc.chef_simulator.things.types.ToolThing;
import dev.benjaminc.chef_utils.graphics.Texture;

public class Spawner extends BasicThing implements ToolThing, SolidThing, CustomDrawingThing{

	protected final static int VARIANT_COUNT = 1;
	public Spawner() {
		this(null, null);
	}
	public Spawner(DataMap dataMap, UUID uuid) {
		super(dataMap, Spawner.class, uuid);
	}
	public Spawner(Thing t) {
		this(null, null);
		dataMap.put(DataMapKey.MAKES, t);
	}
	
	
	@Override
	public void draw(GraphicalDrawer g, int x, int y, int w, int h) {
		int indmkw = w / 4;
		int indmkh = h / 4;
		g.drawTexture(((Texture) dataMap.get(DataMapKey.TEXTURE)).getList().get(FoodState.RAW), x, y, w, h, getName());
		g.draw(((Thing) dataMap.get(DataMapKey.MAKES)), x+indmkw,  y+indmkh,  w-(2*indmkw),  h-(2*indmkh));
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
