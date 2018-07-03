package benjaminc.chief_simulator.things;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import benjaminc.chief_simulator.graphics.GraphicalPan;
import benjaminc.chief_simulator.graphics.food.GraphicalBun;
import benjaminc.chief_simulator.things.food.FoodState;
import benjaminc.chief_simulator.things.types.ContainerThing;

public class Pan implements ContainerThing{

	protected Thing thing;
	protected int variant;
	
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
			
		}
		return out;
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
		thing.draw(g, x, y, w, h);
	}
	
	@Override
	public Thing duplicate() {
		return new Pan(variant, thing.duplicate());
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

}
