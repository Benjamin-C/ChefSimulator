package benjaminc.chief_simulator.things.tools;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import benjaminc.chief_simulator.graphics.food.GraphicalBun;
import benjaminc.chief_simulator.graphics.tools.GraphicalPlate;
import benjaminc.chief_simulator.things.DataMapKey;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.ContainerThing;
import benjaminc.chief_simulator.things.types.FoodThing;

public class Plate implements FoodThing, ContainerThing{

	protected List<Thing> items;
	protected GraphicalPlate graphics;
	protected int variant;
	Map<DataMapKey, Object> dataMap;

	public Plate() {
		this(-1, null);
	}
	public Plate(List<Thing> items) {
		this(-1, items);
	}
	public Plate(int variant) {
		this(variant, null);
	}
	public Plate(int variant, List<Thing> items) {
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
		graphics = new GraphicalPlate(variant);
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
			out.add(items.remove(items.size() - 1));
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
}
