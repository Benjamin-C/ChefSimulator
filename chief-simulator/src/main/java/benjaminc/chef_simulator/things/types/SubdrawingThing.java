package benjaminc.chef_simulator.things.types;

import benjaminc.chef_simulator.graphics.GraphicalDrawer;

public interface SubdrawingThing {
	public abstract void drawBefore(GraphicalDrawer g, int x, int y, int w, int h);
	public abstract void drawAfter(GraphicalDrawer g, int x, int y, int w, int h);
}
