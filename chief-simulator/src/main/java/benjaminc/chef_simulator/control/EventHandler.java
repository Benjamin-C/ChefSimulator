package benjaminc.chef_simulator.control;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.events.Event;

public class EventHandler {

	public static void onEvent(Event e) {
		System.out.println(e.asJSON());
		if(Game.isServerConnected()) {
			Game.getServerPrintStream().println(e.asJSON());
		}
	}
	
	public static void runEvent(Event e) {
		System.out.println("Trying to run event");
		try {
			if(e != null) {
				e.run();
			} else {
				System.out.println("Event was null");
			}
		} catch(Exception ex) {
			System.out.println("The event generated an exception");
			ex.printStackTrace();
		}
	}
	
	// /event {"EVENT_TYPE":"GS_CHANGE_EVENT","TYPE":"ADD","THING":"{"TYPE":"SPAWNER", "DATAMAP":{"MAKES":{"TYPE":"PLATE", "DATAMAP":{"INVENTORY":[], "DIRECTION":"UP", "VARIANT":"-1", "FOOD_STATE":"RAW"}}, "DIRECTION":"UP", "FOOD_STATE":"RAW"}","LOCATION":"{"D":"3","X":"2","Y":"8","Z":"1"}"}
}
