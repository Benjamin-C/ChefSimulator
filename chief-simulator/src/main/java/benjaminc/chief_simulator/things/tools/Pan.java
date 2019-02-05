package benjaminc.chief_simulator.things.tools;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import benjaminc.chief_simulator.graphics.tools.GraphicalPan;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.data.DataMapKey;
import benjaminc.chief_simulator.things.types.ContainerThing;
import benjaminc.chief_simulator.things.types.Cookable;
import benjaminc.chief_simulator.things.types.CookwareThing;

public class Pan implements CookwareThing, ContainerThing{

	protected Thing thing;
	protected int variant;
	Map<DataMapKey, Object> dataMap;
	
	GraphicalPan graphics;
	
	public Pan() {
		this(-1);
	}
	public Pan(Thing item) {
		this(-1, item);
	}
	public Pan(int variant) {
		this(variant, null);
	}
	public Pan(int variant, Thing item) {
		super();
		dataMap = new HashMap<DataMapKey, Object>();
		if(variant == -1) {
			Random r = new Random();
			variant = r.nextInt(GraphicalPan.VARIANT_COUNT);
		}
		thing = item;
		graphics = new GraphicalPan(variant);
	}
	
	@Override
	public List<Thing> useTool(Thing t) {
		List<Thing> out = new ArrayList<Thing>();
		if(thing == null) {
			thing = t;
		} else {
			out.add(thing);
			thing = null;
		}
		return out;
	}

	@Override
	public Thing getCookedThing() {
		if(thing instanceof Cookable) {
			thing = ((Cookable) thing).getCookedThing();
		}
		return this;
	}
	
	@Override
	public void addItem(Thing t) {
		thing = t;
	}

	@Override
	public void removeItem(Thing t) {
		if(t.isSame(thing)) {
			thing = null;
		}
	}

	@Override
	public List<Thing> getItems() {
		List<Thing> out = new ArrayList<Thing>();
		out.add(thing);
		return out;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
		if(thing != null) {
			thing.draw(g, x+(w/4), y+(h/4), w/2, h/2);
		}
	}
	
	@Override
	public Thing duplicate() {
		if(thing == null) {
			return new Pan(variant, null);
		} else {
			return new Pan(variant, thing.duplicate());
		}
	}
	@Override
	public boolean isSame(Thing t) {
		if(t != null) {
			if(t instanceof Pan) {
				if(((Pan) t).getItems().get(0).isSame(thing)) {
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public Map<DataMapKey, Object> getDataMap() {
		return dataMap;
	}
	@Override
	public List<Thing> giveAllItems() {
		List<Thing> out = new ArrayList<Thing>();
		out.add(thing);
		thing = null;
		return out;
	}
}
