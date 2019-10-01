package benjaminc.chef_simulator.things.food;

import java.util.List;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.DataMapKey;
import benjaminc.chef_simulator.data.InvalidDatatypeException;
import benjaminc.chef_simulator.data.Inventory;
import benjaminc.chef_simulator.graphics.GraphicalDrawer;
import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.ContainerThing;
import benjaminc.chef_simulator.things.types.CustomDrawingThing;
import benjaminc.chef_simulator.things.types.FoodThing;
import benjaminc.chef_utils.data.FoodState;
import benjaminc.chef_utils.graphics.Texture;

public class Bun extends BasicThing implements FoodThing, ContainerThing, CustomDrawingThing {

	protected final static int VARIANT_COUNT = 1;
	protected final static int MAX_INV_SIZE = 64;
	public Bun() {
		this((DataMap) null);
	}
	public Bun(DataMap idataMap) {
		super(idataMap, VARIANT_COUNT, Bun.class);
		finalPrep();
	}
	public Bun(int variant, FoodState state) {
		super(variant, state, VARIANT_COUNT, Bun.class);
		finalPrep();
	}
	public Bun(int variant, FoodState state, List<Thing> items) {
		this(variant, state);
		dataMap.put(DataMapKey.INVENTORY, new Inventory(items));
		finalPrep();
	}
	public Bun(List<Thing> toppings) {
		this((DataMap) null);
		dataMap.put(DataMapKey.INVENTORY, new Inventory(toppings));
		finalPrep();
	}
	private void finalPrep() {
		if(!dataMap.containsKey(DataMapKey.INVENTORY) || dataMap.get(DataMapKey.INVENTORY) == null) {
			dataMap.put(DataMapKey.INVENTORY, new Inventory(MAX_INV_SIZE));
		}
	}
	
	@Override
	public void draw(GraphicalDrawer g, int x, int y, int w, int h) {
		List<Thing> items = ((Inventory) dataMap.get(DataMapKey.INVENTORY)).getThingsAsList();
		for(int i = 0; i < items.size(); i++) {
			switch(i%4) {
			case 0: { g.draw(items.get(i), x,  y,  w/2,  h/2); } break;
			case 1: { g.draw(items.get(i), x+(w/2),  y,  w/2,  h/2); } break;
			case 2: { g.draw(items.get(i), x,  y+(h/2),  w/2,  h/2); } break;
			case 3: { g.draw(items.get(i), x+(w/2),  y+(h/2),  w/2,  h/2); } break;
			}
		}
		g.drawTexture(((Texture) dataMap.get(DataMapKey.GRAPHICS)).getList().get(FoodState.RAW), x, y, w, h);
	}

	@Override
	public List<Thing> useTool(Thing t) {
		addItem(t);
		return null;
	}

	@Override
	public void addItem(Thing t) {
		if(t != null) {
			Inventory inv = (Inventory) dataMap.get(DataMapKey.INVENTORY);
			inv.add(t);
			dataMap.put(DataMapKey.INVENTORY, inv);
		}
	}

	@Override
	public void removeItem(Thing t) {
		if(t != null) {
			Inventory inv = (Inventory) dataMap.get(DataMapKey.INVENTORY);
			inv.remove(t);
			dataMap.put(DataMapKey.INVENTORY, inv);
		}
	}

	@Override
	public Inventory getItems() {
		return ((Inventory) dataMap.get(DataMapKey.INVENTORY)).clone();
	}

	@Override
	public boolean isSame(Thing t) {
		if(t != null) {
			if(t.getClass() == this.getClass()) {
				if(t instanceof Bun) {
					return getItems().hasSame(((Bun) t).getItems());
				}
			}
		}
		return false;
	}
	
	public void setVariant(int var) {
		try { dataMap.put(DataMapKey.VARIANT, var);
		} catch (InvalidDatatypeException e) { e.printStackTrace(); }
	}
	public void setState(FoodState state) {
		try { dataMap.put(DataMapKey.FOOD_STATE, state);
		} catch (InvalidDatatypeException e) { e.printStackTrace(); };
	}
	public int getVariant() {
		try { return (int) dataMap.get(DataMapKey.VARIANT);
		} catch (InvalidDatatypeException e) { e.printStackTrace(); return -1; }
	}
	public FoodState getState() {
		try { return (FoodState) dataMap.get(DataMapKey.FOOD_STATE);
		} catch (InvalidDatatypeException e) { e.printStackTrace(); return null; }
	}
	@Override
	public DataMap getDataMap() {
		return dataMap;
	}
	
	public Inventory giveAllItems() {
		return ((Inventory) dataMap.get(DataMapKey.INVENTORY)).clone();
	}
}
