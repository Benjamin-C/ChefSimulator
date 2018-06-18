package benjaminc.chief_simulator.things.building;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.ToolThing;

public class Disposal implements SolidThing, ToolThing{

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		int indw = w / 8;
		int indh = h / 8;
		g.setColor(new Color(64, 64, 64));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x,  y,  w,  h);
		g.setColor(new Color(16, 16, 16));
		g.fillRect(x+indw,  y+indh,  w-(2*indw),  h-(2*indh));
	}

	@Override
	public Thing duplicate() {
		return new Disposal();
	}

	@Override
	public List<Thing> useTool(Thing t) {
		return null;
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
