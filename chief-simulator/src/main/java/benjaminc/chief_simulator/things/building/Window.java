package benjaminc.chief_simulator.things.building;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.ToolThing;

public class Window implements ToolThing, Thing, SolidThing{

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		int indw = w / 8;
		int indh = h / 8;
		g.setColor(new Color(64, 64, 64));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x,  y,  w,  h);
		g.setColor(new Color(192, 192, 192));
		g.fillRect(x+indw,  y+indh,  w-(2*indw),  h-(2*indh));
	}

	@Override
	public Thing duplicate() {
		return new Window();
	}

	@Override
	public boolean isSame(Thing t) {
		if(t.getClass() == this.getClass()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Thing> useTool(Thing t) {
		
		return null;
	}

}
