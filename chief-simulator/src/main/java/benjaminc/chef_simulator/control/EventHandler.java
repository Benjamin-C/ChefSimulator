package benjaminc.chef_simulator.control;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.events.Event;

public class EventHandler {
	
	/**
	 * Fires an {@link Event} to the server
	 * @param e	the {@link Event} to fire
	 */
	public static void fireEvent(Event e) {
		System.out.println("Trying to run event");
		if(e != null) {
			if(Game.isMultiplayer()) {
				if(Game.getServerPrintStream() != null) {
					Game.getServerPrintStream().println(e.asJSON());
				} else {
					System.out.println("SPS is null");
					Game.chat("SPS is null");
				}
			}
			reciveEvent(e);
		}
	}
	
	/**
	 * Executes an {@link Event} from the Server
	 * @param e	the {@link Event} to call {@link Event#run()} on
	 */
	public static void reciveEvent(Event e) {
		try {
			if(e != null) {
				// If the event exists ...
				e.run();
				System.out.println(e);
			} else {
				System.out.println("Event was null");
			}
		} catch(Exception ex) {
			System.out.println("The event generated an exception");
			ex.printStackTrace();
		}
	}
}
