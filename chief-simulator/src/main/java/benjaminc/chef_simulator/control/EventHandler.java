package benjaminc.chef_simulator.control;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.events.Event;

public class EventHandler {
	
	/**
	  * IMPORTANT NOTE
	  * DO NOT FIRE EVENTS HERE. Make sure that no events are fired during
	  * the event handling routines. If an event must be fired during
	  * an Event, be sure that the event does not fire itself.
	 */
	
	/**
	 * Fires an {@link Event} to the server
	 * @param e	the {@link Event} to fire
	 */
	public static void fireEvent(Event e) {
		Game.getGamePanel().getChatBox().out.println("Firing Event");
		if(e != null) {
			if(Game.isMultiplayer()) {
				if(Game.getServerPrintStream() != null) {
					Game.getServerPrintStream().println(e.asJSON());
				} else {
					System.out.println("SPS is null");
					Game.getGamePanel().getChatBox().out.println("SPS is null");
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
		Game.getGamePanel().getChatBox().out.println("Got Event");
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
