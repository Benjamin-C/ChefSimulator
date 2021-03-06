package dev.benjaminc.chef_simulator.things.food;

import java.util.List;
import java.util.UUID;

import dev.benjaminc.chef_simulator.chef_graphics.GraphicalDrawer;
import dev.benjaminc.chef_simulator.data.DataMap;
import dev.benjaminc.chef_simulator.data.FoodState;
import dev.benjaminc.chef_simulator.data.InvalidDatatypeException;
import dev.benjaminc.chef_simulator.data.Inventory;
import dev.benjaminc.chef_simulator.data.DataMap.DataMapKey;
import dev.benjaminc.chef_simulator.things.BasicThing;
import dev.benjaminc.chef_simulator.things.Thing;
import dev.benjaminc.chef_simulator.things.types.ContainerThing;
import dev.benjaminc.chef_simulator.things.types.CustomDrawingThing;
import dev.benjaminc.chef_simulator.things.types.FoodThing;
import dev.benjaminc.chef_utils.graphics.Texture;

/**
 * A Bun to hold food on to serve
 * extends BasicThing implements FoodThing, ContainerThing, CustomDrawingThing
 * @author Benjamin-C
 *
 */
public class Bun extends BasicThing implements FoodThing, ContainerThing, CustomDrawingThing {

	protected final static int VARIANT_COUNT = 1;
	protected final static int MAX_INV_SIZE = 64;
	public Bun() {
		this(null, null);
	}
	public Bun(DataMap idataMap, UUID uuid) {
		super(idataMap, Bun.class, uuid);
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
		this(null, null);
		dataMap.put(DataMapKey.INVENTORY, new Inventory(toppings));
		finalPrep();
	}
	private void finalPrep() {
		if(!dataMap.containsKey(DataMapKey.INVENTORY) || dataMap.get(DataMapKey.INVENTORY) == null) {
			dataMap.put(DataMapKey.INVENTORY, new Inventory());
		}
	}
	
	@Override
	public void draw(GraphicalDrawer g, int x, int y, int w, int h) {
		List<Thing> items = ((Inventory) dataMap.get(DataMapKey.INVENTORY)).getAll();
		for(int i = 0; i < items.size(); i++) {
			switch(i%4) {
			case 0: { g.draw(items.get(i), x,  y,  w/2,  h/2); } break;
			case 1: { g.draw(items.get(i), x+(w/2),  y,  w/2,  h/2); } break;
			case 2: { g.draw(items.get(i), x,  y+(h/2),  w/2,  h/2); } break;
			case 3: { g.draw(items.get(i), x+(w/2),  y+(h/2),  w/2,  h/2); } break;
			}
		}
		g.drawTexture(((Texture) dataMap.get(DataMapKey.TEXTURE)).getList().get(FoodState.RAW), x, y, w, h, getName());
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
		return ((Inventory) dataMap.get(DataMapKey.INVENTORY));
	}

	@Override
	public boolean isSame(Thing t) {
		System.out.println("BunSame?");
		if(t != null) {
			if(t.getClass() == this.getClass()) {
				if(t instanceof Bun) {
					System.out.println("Checking items");
					return getItems().hasSame(((Bun) t).getItems());
				} else { System.out.println("Type match fail"); }
			} else { System.out.println("Class match fail"); }
		} else { System.out.println("t is null fail"); }
		System.out.println("Bun returns false");
		return false;
	}
	
	public void setState(FoodState state) {
		try { dataMap.put(DataMapKey.FOOD_STATE, state);
		} catch (InvalidDatatypeException e) { e.printStackTrace(); };
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
		Inventory out = ((Inventory) dataMap.get(DataMapKey.INVENTORY)).clone();
		((Inventory) dataMap.get(DataMapKey.INVENTORY)).clear();
		return out;
	}
}
