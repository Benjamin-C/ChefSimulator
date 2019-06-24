package benjaminc.chief_simulator.things.tools;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import benjaminc.chief_simulator.data.DataMapArray;
import benjaminc.chief_simulator.data.DataMapKey;
import benjaminc.chief_simulator.data.DataMapValue;
import benjaminc.chief_simulator.graphics.food.GraphicalBun;
import benjaminc.chief_simulator.graphics.tools.GraphicalPlate;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.ContainerThing;

public class Plate implements ContainerThing{

	protected GraphicalPlate graphics;
	protected Map<DataMapKey, Object> dataMap;

	public Plate() {
		this(-1, null, null);
	}
	public Plate(Thing item) {
		this(-1, null, item);
	}
	public Plate(int variant, Thing item) {
		this(variant, null, item);
	}
	public Plate(List<Thing> items) {
		this(-1, items, null);
	}
	public Plate(int variant) {
		this(variant, null, null);
	}
	public Plate(int variant, List<Thing> items) {
		this(variant, items, null);
	}
	private Plate(int variant, List<Thing> items, Thing t) {
		super();
		dataMap = new HashMap<DataMapKey, Object>();
		if(variant == -1) {
			Random r = new Random();
			dataMap.put(DataMapKey.VARIANT, r.nextInt(GraphicalBun.VARIANT_COUNT));
		}
		dataMap.put(DataMapKey.ITEM, new DataMapValue(new DataMapArray(items)));
		if(items == null) {
			dataMap.put(DataMapKey.ITEM, new ArrayList<Thing>());
			if(t != null) {
				List<Thing> bob = (List<Thing>) (dataMap.get(DataMapKey.ITEM));
				bob.add(t);
				dataMap.put(DataMapKey.ITEM, bob);
			}
		}
		graphics = new GraphicalPlate(dataMap);
	}
	public Plate(Map<DataMapKey, DataMapValue> data) {
		dataMap = data;
		graphics = new GraphicalPlate(dataMap);
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
		switch(items.size()) {
		case 1: {
			items.get(0).draw(g,  x+(w/4),  y+(h/4),  w/2,  h/2);
		} break;
		case 2: {
			items.get(0).draw(g,  x,  y+(h/4),  w/2,  h/2);
			items.get(1).draw(g,  x+(w/2),  y+(h/4),  w/2,  h/2);
		} break;
		case 3: {
			items.get(0).draw(g,  x,  y,  w/2,  h/2);
			items.get(1).draw(g,  x+(w/2),  y,  w/2,  h/2);
			items.get(2).draw(g,  x+(w/4),  y+(h/2),  w/2,  h/2);
		} break;
		default: {
			for(int i = 0; i < items.size(); i++) {
				switch(i%4) {
				case 0: { items.get(i).draw(g,  x,  y,  w/2,  h/2); } break;
				case 1: { items.get(i).draw(g,  x+(w/2),  y,  w/2,  h/2); } break;
				case 2: { items.get(i).draw(g,  x,  y+(h/2),  w/2,  h/2); } break;
				case 3: { items.get(i).draw(g,  x+(w/2),  y+(h/2),  w/2,  h/2); } break;
				}
			}
		} break;
		}
	}

	@Override
	public List<Thing> useTool(Thing t) {
		if(t != null) {
			addItem(t);
		} else {
			List<Thing> out = new ArrayList<Thing>();
			if(items.size() > 0) {
				out.add(items.remove(items.size() - 1));
			}
			return out;
		}
		return null;
	}

	@Override
	public void addItem(Thing t) {
		if(t != null) {
			items.add(t);
		}
	}

	@Override
	public void removeItem(Thing t) {
		items.remove(t);
	}

	@Override
	public List<Thing> getItems() {
		List<Thing> temp = new ArrayList<Thing>();
		for(int i = 0; i < items.size(); i++) {
			temp.add(items.get(i).duplicate());
		}
		return temp;
	}
	
	@Override
	public Thing duplicate() {
		List<Thing> temp = new ArrayList<Thing>();
		for(int i = 0; i < items.size(); i++) {
			temp.add(items.get(i).duplicate());
		}
		return new Plate(variant, temp);
	}

	@Override
	public boolean isSame(Thing t) {
		if(t != null) {
			if(t.getClass() == this.getClass()) {
				if(t instanceof Plate) {
					List<Thing> theirItems = ((Plate) t).getItems();
					List<Thing> ourItems = getItems();
					if(theirItems.size() == ourItems.size()) {
						for(Thing oth : ourItems) {
							for(Thing tth : theirItems) {
								if(oth.isSame(tth)) {
									theirItems.remove(tth);
									break;
								}
							}
						}
						if(theirItems.isEmpty()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public void setVariant(int var) {
		variant = var;
	}
	public int getVariant() {
		return variant;
	}
	@Override
	public Map<DataMapKey, Object> getDataMap() {
		return dataMap;
	}
	@Override
	public List<Thing> giveAllItems() {
		List<Thing> out = items;
		items = null;
		return out;
	}
}
