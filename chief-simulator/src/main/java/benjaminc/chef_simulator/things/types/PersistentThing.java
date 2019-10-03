/**
 * 
 */
package benjaminc.chef_simulator.things.types;

import benjaminc.chef_simulator.events.OnDisposeEvent;

/**
 * A Thing that can not go in the disposal
 * @author Benjamin-C
 *
 */
public interface PersistentThing {
	
	/**
	 * What to do if the player tries to dispose the Thing
	 */
	public abstract void onDispose(OnDisposeEvent e);

}
