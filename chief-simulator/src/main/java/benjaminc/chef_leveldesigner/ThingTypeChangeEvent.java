package benjaminc.chef_leveldesigner;

import benjaminc.chef_simulator.things.Thing;

/**
 * @author Benjamin-C
 *
 */
public interface ThingTypeChangeEvent {
	
	public abstract void onChange(Thing newThing);

}
