package benjaminc.chef_simulator.control;

import benjaminc.chef_simulator.events.Event;

public class EventHandler {

	public static void onEvent(Event e) {
		System.out.println(e);
	}
}
