package benjaminc.chef_simulator.things.tools;

import java.util.ArrayList;
import java.util.List;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.DataMapKey;
import benjaminc.chef_simulator.data.Inventory;
import benjaminc.chef_simulator.graphics.GraphicalDrawer;
import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.ContainerThing;
import benjaminc.chef_simulator.things.types.CustomDrawingThing;
import benjaminc.chef_utils.data.FoodState;
import benjaminc.chef_utils.graphics.Texture;

public class Dishbin extends BasicThing implements ContainerThing, CustomDrawingThing {

	private static final int VARIANT_COUNT = 1;
	private static final int MAX_INV_SIZE = 64;
	public Dishbin() {
		this(-1, null, null);
	}
	public Dishbin(Thing item) {
		this(-1, null, item);
	}
	public Dishbin(int variant, Thing item) {
		this(variant, null, item);
	}
	public Dishbin(List<Thing> items) {
		this(-1, items, null);
	}
	
	public Dishbin(int variant) {
		this(variant, null, null);
	}
	public Dishbin(int variant, List<Thing> items) {
		this(variant, items, null);
	}
	private Dishbin(int variant, List<Thing> items, Thing t) {
		super(variant, FoodState.RAW, VARIANT_COUNT, Dishbin.class);
		Inventory myinv = new Inventory(MAX_INV_SIZE);
		if(t != null) {
			myinv.add(t);
		}
		if(items != null) {
			for(Thing th : items) {
				myinv.add(th);
			}
		}
		dataMap.put(DataMapKey.INVENTORY, myinv);
	}
	public Dishbin(DataMap data) {
		super(data, Dishbin.class);
	}
	
	@Override
	public void draw(GraphicalDrawer g, int x, int y, int w, int h) {
		g.drawTexture(((Texture) dataMap.get(DataMapKey.TEXTURE)).getList().get(FoodState.RAW), x, y, w, h, getName());
		List<Thing> items = ((Inventory) dataMap.get(DataMapKey.INVENTORY)).getThingsAsList();
		switch(items.size()) {
		case 1: {
			g.draw(items.get(0), x+(w/4),  y+(h/4),  w/2,  h/2);
		} break;
		case 2: {
			g.draw(items.get(0), x,  y+(h/4),  w/2,  h/2);
			g.draw(items.get(1), x+(w/2),  y+(h/4),  w/2,  h/2);
		} break;
		case 3: {
			g.draw(items.get(0), x,  y,  w/2,  h/2);
			g.draw(items.get(1), x+(w/2),  y,  w/2,  h/2);
			g.draw(items.get(2), x+(w/4),  y+(h/2),  w/2,  h/2);
		} break;
		default: {
			for(int i = 0; i < items.size(); i++) {
				switch(i%4) {
				case 0: { g.draw(items.get(i), x,  y,  w/2,  h/2); } break;
				case 1: { g.draw(items.get(i), x+(w/2),  y,  w/2,  h/2); } break;
				case 2: { g.draw(items.get(i), x,  y+(h/2),  w/2,  h/2); } break;
				case 3: { g.draw(items.get(i), x+(w/2),  y+(h/2),  w/2,  h/2); } break;
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
		System.out.println("PlateIsSame? " + t);
		if(t != null) {
			if(t.getClass() == this.getClass()) {
				if(t instanceof Dishbin) {
					if(getItems().hasSame(((Dishbin) t).getItems())) {
						return true;
					} else { System.out.println("Inv match fail"); }
				} else { System.out.println("Type match fail"); }
			} else { System.out.println("Class match fail"); }
		} else { return false; }
		System.out.println("Plate returns false");
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
