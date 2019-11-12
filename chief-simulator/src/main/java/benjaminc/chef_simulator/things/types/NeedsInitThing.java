package benjaminc.chef_simulator.things.types;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.things.Thing;

/**
 * @author Benjamin-C
 *
 */
public interface NeedsInitThing {
	
	/**
	 * Initializes the {@link Thing} for the {@link Game}
	 * @param g the current {@link Game}
	 */
	public abstract void init(Game g);

}
