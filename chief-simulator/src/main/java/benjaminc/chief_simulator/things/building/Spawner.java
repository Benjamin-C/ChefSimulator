package benjaminc.chief_simulator.things.building;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.ToolThing;

public class Spawner implements ToolThing, SolidThing {

	protected Thing toMake;
	
	public Spawner(Thing t) {
		toMake = t;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		int indw = w / 8;
		int indh = h / 8;
		int indmkw = w / 4;
		int indmkh = h / 4;
		g.setColor(new Color(64, 64, 64));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(192, 192, 192));
		g.fillRect(x+indw,  y+indh,  w-(2*indw),  h-(2*indh));
		toMake.draw(g, x+indmkw,  y+indmkh,  w-(2*indmkw),  h-(2*indmkh));
	}

	@Override
	public List<Thing> useTool(Thing t) {
		List<Thing> temp = new ArrayList<Thing>();
		if(t == null) {
			temp.add(toMake.duplicate());
		}
		temp.add(t);
		return temp;
	}

	@Override
	public Thing duplicate() {
		return new Spawner(toMake.duplicate());
	}

	@Override
	public boolean isSame(Thing t) {
		if(t.getClass() == this.getClass()) {
			return true;
		} else {
			return false;
		}
	}
}
