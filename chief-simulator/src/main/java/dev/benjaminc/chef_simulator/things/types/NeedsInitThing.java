package dev.benjaminc.chef_simulator.things.types;

import dev.benjaminc.chef_simulator.things.Thing;

/**
 * @author Benjamin-C
 *
 */
public interface NeedsInitThing {
	
	/**
	 * Initializes the {@link Thing} for the {@link Game}
	 */
	public abstract void init();

}
