package dev.orangeben.chef_simulator.things.types;

import dev.orangeben.chef_simulator.chef_graphics.GraphicalDrawer;

public interface CustomDrawingThing {
	public abstract void draw(GraphicalDrawer g, int x, int y, int w, int h);
}
