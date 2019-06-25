package benjaminc.chief_simulator.things.tools;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import benjaminc.chief_simulator.data.DataMap;
import benjaminc.chief_simulator.data.DataMapKey;
import benjaminc.chief_simulator.data.FoodState;
import benjaminc.chief_simulator.graphics.GraphicalLoader;
import benjaminc.chief_simulator.graphics.tools.GraphicalPan;
import benjaminc.chief_simulator.things.BasicThing;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.ContainerThing;
import benjaminc.chief_simulator.things.types.Cookable;
import benjaminc.chief_simulator.things.types.CookwareThing;

public class Pan extends BasicThing implements CookwareThing, ContainerThing{

	protected final static int VARIANT_COUNT = 1;
	public Pan() {
		this(null);
	}
	public Pan(DataMap dataMap) {
		super(dataMap, VARIANT_COUNT, Pan.class);
		graphics = GraphicalLoader.load(this.getClass().getSimpleName(), this.dataMap);
	}
	public Pan(int variant, Thing item) {
		super(variant, FoodState.RAW, VARIANT_COUNT, Pan.class);
		
	}
	
	@Override
	public List<Thing> useTool(Thing t) {
		checkItemKey();
		List<Thing> out = new ArrayList<Thing>();
		Thing thing = dataMap.get(DataMapKey.ITEM).getThing();
		if(thing == null) {
			dataMap.get(DataMapKey.ITEM).update(t);
		} else {
			out.add(thing);
			dataMap.get(DataMapKey.ITEM).update(null);
		}
		return out;
	}

	@Override
	public Thing getCookedThing() {
		checkItemKey();
		if(dataMap.get(DataMapKey.ITEM).getThing() instanceof Cookable) {
			dataMap.get(DataMapKey.ITEM).update(((Cookable) dataMap.get(DataMapKey.ITEM).getThing()).getCookedThing());
		}
		return this;
	}
	
	@Override
	public void addItem(Thing t) {
		checkItemKey();
		dataMap.get(DataMapKey.ITEM).update(t);
	}

	@Override
	public void removeItem(Thing t) {
		checkItemKey();
		if(t.isSame(dataMap.get(DataMapKey.ITEM).getThing())) {
			dataMap.get(DataMapKey.ITEM).update(null);
		}
	}

	@Override
	public List<Thing> getItems() {
		checkItemKey();
		List<Thing> out = new ArrayList<Thing>();
		out.add(dataMap.get(DataMapKey.ITEM).getThing());
		return out;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		checkItemKey();
		graphics.draw(g, x, y, w, h);
		if(dataMap.get(DataMapKey.ITEM).getThing() != null) {
			dataMap.get(DataMapKey.ITEM).getThing().draw(g, x+(w/4), y+(h/4), w/2, h/2);
		}
	}
	
	@Override
	public Thing duplicate() {
		return new Pan(dataMap);
	}
	@Override
	public boolean isSame(Thing t) {
		checkItemKey();
		if(t != null) {
			if(t instanceof Pan) {
				if(((Pan) t).getItems().get(0).isSame(dataMap.get(DataMapKey.ITEM).getThing())) {
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public Map<DataMapKey, DataMapValue> getDataMap() {
		return dataMap;
	}
	@Override
	public List<Thing> giveAllItems() {
		checkItemKey();
		List<Thing> out = new ArrayList<Thing>();
		out.add(dataMap.get(DataMapKey.ITEM).getThing());
		dataMap.get(DataMapKey.ITEM).update(null);
		return out;
	}
	private void checkItemKey() {
		if(!dataMap.containsKey(DataMapKey.ITEM)) {
			dataMap.put(DataMapKey.ITEM, new DataMapValue(Thing.class));
		}
	}
}
