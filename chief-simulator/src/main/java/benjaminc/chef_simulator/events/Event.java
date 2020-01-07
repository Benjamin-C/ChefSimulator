package benjaminc.chef_simulator.events;

/**
 * Basic event
 * @author Benjamin-C
 *
 */
public class Event {
	
	boolean canceled;
	
	/**
	 * Sets weather or not the event should be canceled
	 * 
	 * @param cancel the boolean new cancel state
	 */
	public void setCanceled(boolean cancel) {
		canceled = cancel;
	}
	/**
	 * Gets weather or not the event was canceled
	 * 
	 * @return the boolean if canceled
	 */
	public boolean getCanceled() {
		return canceled;
	}

}
