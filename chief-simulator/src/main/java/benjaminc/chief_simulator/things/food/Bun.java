package benjaminc.chief_simulator.things.food;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import benjaminc.chief_simulator.graphics.food.GraphicalBun;
import benjaminc.chief_simulator.things.DataMapKey;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.ContainerThing;
import benjaminc.chief_simulator.things.types.FoodThing;

public class Bun implements FoodThing, ContainerThing{

	protected List<Thing> items;
	protected GraphicalBun graphics;
	protected int variant;
	protected FoodState state;
	Map<DataMapKey, Object> dataMap;

	public Bun() {
		this(-1, FoodState.RAW, null);
	}
	public Bun(List<Thing> items) {
		this(-1, FoodState.RAW, items);
	}
	public Bun(int variant, FoodState state) {
		this(variant, state, null);
	}
	public Bun(int variant, FoodState state, List<Thing> items) {
		super();
		dataMap = new HashMap<DataMapKey, Object>();
		if(variant == -1) {
			Random r = new Random();
			variant = r.nextInt(GraphicalBun.VARIANT_COUNT);
		}
		this.items = items;
		if(items == null) {
			this.items = new ArrayList<Thing>();
		}
		this.state = state;
		graphics = new GraphicalBun(variant, state);
	}
	
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		for(int i = 0; i < items.size(); i++) {
			switch(i%4) {
			case 0: { items.get(i).draw(g,  x,  y,  w/2,  h/2); } break;
			case 1: { items.get(i).draw(g,  x+(w/2),  y,  w/2,  h/2); } break;
			case 2: { items.get(i).draw(g,  x,  y+(h/2),  w/2,  h/2); } break;
			case 3: { items.get(i).draw(g,  x+(w/2),  y+(h/2),  w/2,  h/2); } break;
			}
		}
		graphics.draw(g, x, y, w, h);
	}

	@Override
	public List<Thing> useTool(Thing t) {
		addItem(t);
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
		return new Bun(variant, state, temp);
	}

	@Override
	public boolean isSame(Thing t) {
		if(t != null) {
			if(t.getClass() == this.getClass()) {
				if(t instanceof Bun) {
					List<Thing> theirItems = ((Bun) t).getItems();
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
	@Override
	public void setState(FoodState state) {
		this.state = state;
	}
	public int getVariant() {
		return variant;
	}
	@Override
	public FoodState getState() {
		return state;
	}
	@Override
	public Map<DataMapKey, Object> getDataMap() {
		return dataMap;
	}
	
	public List<Thing> giveAllItems() {
		List<Thing> out = items;
		items = null;
		return out;
	}
}
