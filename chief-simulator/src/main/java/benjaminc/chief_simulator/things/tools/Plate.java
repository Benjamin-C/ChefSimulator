package benjaminc.chief_simulator.things.tools;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import benjaminc.chief_simulator.data.DataMap;
import benjaminc.chief_simulator.data.DataMapKey;
import benjaminc.chief_simulator.data.FoodState;
import benjaminc.chief_simulator.data.Inventory;
import benjaminc.chief_simulator.things.BasicThing;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.ContainerThing;

public class Plate extends BasicThing implements ContainerThing{

	private static final int VARIANT_COUNT = 1;
	private static final int MAX_INV_SIZE = 64;
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
		super(variant, FoodState.RAW, VARIANT_COUNT, Plate.class);
		Inventory myinv = new Inventory(MAX_INV_SIZE);
		myinv.add(t);
		if(items != null) {
			for(Thing th : items) {
				myinv.add(th);
			}
		}
		dataMap.put(DataMapKey.INVENTORY, myinv);
	}
	public Plate(DataMap data) {
		super(data, Plate.class);
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
		List<Thing> items = ((Inventory) dataMap.get(DataMapKey.INVENTORY)).getThingsAsList();
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
			Inventory in = (Inventory) dataMap.get(DataMapKey.INVENTORY);
			if(in.thingCount() > 0) {
				Thing th = null;
				for(int i = in.size() - 1; i >= 0; i--) {
					th = in.get(i);
					if(th != null) {
						out.add(in.remove(i));
						break;
					}
				}
				
			}
			return out;
		}
		return null;
	}

	@Override
	public void addItem(Thing t) {
		if(t != null) {
			((Inventory) dataMap.get(DataMapKey.INVENTORY)).add(t);
		}
	}

	@Override
	public void removeItem(Thing t) {
		((Inventory) dataMap.get(DataMapKey.INVENTORY)).remove(t);
	}

	@Override
	public Inventory getItems() {
		return (Inventory) dataMap.get(DataMapKey.INVENTORY);
	}

	@Override
	public boolean isSame(Thing t) {
		if(t != null) {
			if(t.getClass() == this.getClass()) {
				if(t instanceof Plate) {
					List<Thing> theirItems = ((Plate) t).getItems().getAllAsList();
					List<Thing> ourItems = getItems().getAllAsList();
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
	
	@Override
	public DataMap getDataMap() {
		return dataMap;
	}
	@Override
	public Inventory giveAllItems() {
		Inventory out = ((Inventory) dataMap.get(DataMapKey.INVENTORY)).clone();
		((Inventory) dataMap.get(DataMapKey.INVENTORY)).clear();
		return out;
	}
}
