package benjaminc.chef_simulator.graphics;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.data.DataMapKey;
import benjaminc.chef_simulator.data.Inventory;
import benjaminc.chef_simulator.things.*;
import benjaminc.chef_simulator.things.building.Floor;
import benjaminc.chef_simulator.things.types.SolidThing;
import benjaminc.chef_simulator.things.types.Tickable;

public class GameSpace {

	protected List<Thing> things;
	public GameSpace() {
		things = new ArrayList<Thing>();
		things.add(new Floor());
	}
	
	public void addThing(Thing t) {
		if(t != null) {
			things.add(t);
		}
	}
	
	public void addThing(int loc, Thing t) {
		if(t != null) {
			things.add(loc, t);
		}
	}
	
	public Thing removeThing(Thing t) {
		things.remove(t);
		return t;
	}
	public void removeAll(Thing t) {
		for(int i = 0; i < things.size(); i++) {
			if(things.get(i).getClass().isAssignableFrom( t.getClass() )) {
				things.remove(i--);
			}
		}
	}
	public Thing removeThing(int loc) {
		Thing temp = things.remove(loc);
		if(things.size() < 1) {
			things.add(new Floor());
		}
		return temp;
	}
	
	public Thing getThing(int loc) {
		return things.get(loc);
	}
	
	public Thing getThing() {
		return things.get(things.size() - 1);
	}
	
	public List<Thing> getThings() {
		return things;
	}
	
	public void draw(GraphicalDrawer g, int x, int y, int w, int h) {
		for(int i = 0; i < things.size(); i++) {
			g.draw(things.get(i), x, y, w, h);
			
//			Bun
//			@Override
//			public void draw(Graphics g, int x, int y, int w, int h) {
//				List<Thing> items = ((Inventory) dataMap.get(DataMapKey.INVENTORY)).getThingsAsList();
//				for(int i = 0; i < items.size(); i++) {
//					switch(i%4) {
//					case 0: { items.get(i).draw(g,  x,  y,  w/2,  h/2); } break;
//					case 1: { items.get(i).draw(g,  x+(w/2),  y,  w/2,  h/2); } break;
//					case 2: { items.get(i).draw(g,  x,  y+(h/2),  w/2,  h/2); } break;
//					case 3: { items.get(i).draw(g,  x+(w/2),  y+(h/2),  w/2,  h/2); } break;
//					}
//				}
//				graphics.draw(g, x, y, w, h);
//			}
			
//			pan
//			@Override
//			public void draw(Graphics g, int x, int y, int w, int h) {
//				checkItemKey();
//				graphics.draw(g, x, y, w, h);
//				Thing t = ((Inventory) dataMap.get(DataMapKey.INVENTORY)).get(0);
//				if(t != null) {
//					t.draw(g, x+(w/4), y+(h/4), w/2, h/2);
//				}
//			}
		}
	}
	
	public boolean isSolid() {
		for(Thing th : things) {
			if(th instanceof SolidThing) {
				return true;
			}
		}
		return false;
	}
	
	public void tick(Room r, Location l, long frame, Game g) {
		for(int i = 0; i < things.size(); i++) {
			Thing t = things.get(i);
			if(t instanceof Tickable) {
				((Tickable) t).tick(r, l, frame, g);
			}
		}
	}
}
