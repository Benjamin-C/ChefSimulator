package dev.benjaminc.chef_simulator.things.types;

import dev.benjaminc.chef_simulator.chef_graphics.GraphicalDrawer;

public interface CustomDrawingThing {
	public abstract void draw(GraphicalDrawer g, int x, int y, int w, int h);
}
