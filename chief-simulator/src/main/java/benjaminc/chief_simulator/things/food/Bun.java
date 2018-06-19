package benjaminc.chief_simulator.things.food;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.ContainerThing;
import benjaminc.chief_simulator.things.types.FoodThing;

public class Bun implements FoodThing, ContainerThing{

	protected List<Thing> items;
	
	public Bun() {
		items = new ArrayList<Thing>();
	}
	public Bun(List<Thing> items) {
		this.items = items;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(new Color(210, 180, 140));
		g.fillOval(x+(int)(w*0.05),  y+(int)(h*0.05), (int)(w*0.9),  (int)(h*0.9));
		g.setColor(new Color(200, 170, 130));
		g.fillOval(x+(int)(w*0.15),  y+(int)(h*0.15), (int)(w*0.7),  (int)(h*0.7));
		for(int i = 0; i < items.size(); i++) {
			switch(i%4) {
			case 0: { items.get(i).draw(g,  x,  y,  w/2,  h/2); } break;
			case 1: { items.get(i).draw(g,  x+(w/2),  y,  w/2,  h/2); } break;
			case 2: { items.get(i).draw(g,  x,  y+(h/2),  w/2,  h/2); } break;
			case 3: { items.get(i).draw(g,  x+(w/2),  y+(h/2),  w/2,  h/2); } break;
			}
		g.setColor(new Color(210, 180, 140));
		g.fillOval(x+(int)(w*0.05),  y+(int)(h*0.05), (int)(w*0.9),  (int)(h*0.9));
		g.setColor(new Color(200, 170, 130));
		g.fillOval(x+(int)(w*0.15),  y+(int)(h*0.15), (int)(w*0.7),  (int)(h*0.7));
		
		}
	}

	@Override
	public Thing getChoppedThing() {
		return this;
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
		return new Bun(temp);
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
}
