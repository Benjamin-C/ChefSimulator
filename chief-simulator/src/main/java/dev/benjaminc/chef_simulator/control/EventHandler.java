package dev.benjaminc.chef_simulator.control;

import dev.benjaminc.chef_simulator.Game;
import dev.benjaminc.chef_simulator.events.Event;
import dev.benjaminc.chef_simulator.events.EventDestination;

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
		fireEvent(e, EventDestination.BOTH);
	}
	
	/**
	 * Fires an {@link Event} to the server
	 * @param e	the {@link Event} to fire
	 */
	public static void fireEvent(Event e, EventDestination d) {
		if(e != null) {
			if(d == EventDestination.SERVER || d == EventDestination.BOTH) {
				if(Game.isMultiplayer()) {
					if(Game.getServerPrintStream() != null) {
						Game.getServerPrintStream().println(e.asJSON());
					} else {
						System.out.println("SPS is null");
						Game.getGamePanel().getChatBox().out.println("SPS is null");
					}
				}
			}
			if(d == EventDestination.LOCAL || d == EventDestination.BOTH) {
				reciveEvent(e);
			}
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
