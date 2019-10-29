package benjaminc.chef_leveldesigner;

import benjaminc.chef_simulator.things.Thing;

/**
 * @author Benjamin-C
 *
 */
public interface ThingListElementEditEvent {
	
	/**
	 * Called when the edit button of a {@link ThingListElement} is called
	 * @param t the {@link Thing} to edit
	 * @param te the {@link ThingListElement} the {@link Thing} belongs to
	 * @param tl the {@link ThingList} that the {@link ThingListElement} belongs to
	 * @param onUpdate the {@link Runnable} to run when done
	 */
	public abstract void edit(Thing t, ThingListElement te, ThingList tl, Runnable onUpdate);

}
