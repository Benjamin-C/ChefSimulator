package dev.benjaminc.chef_simulator.control.command;

import dev.benjaminc.chef_simulator.control.EventHandler;
import dev.benjaminc.chef_simulator.events.Event;

public class EventCommand implements Command {

	private final String NAME = "event";
	private final String HELP = 
			"Triggers an event.\n"
			+ "Usage: /event [json]\n"
			+ "json: the JSON representation of the event";
					
	private final int ARG_COUNT = 1;
	
	@Override
	public boolean execute(String[] args) {
		if(args.length > ARG_COUNT) {
			String json = "";
			for(int i = 1; i < args.length; i++) {
				json += args[i];
			}
			System.out.println(json);
			Event e = Event.loadEventFromJSON(json);
			System.out.println(e);
			EventHandler.fireEvent(e);
			return true;
		}
		return false;
	}
	
	@Override public String getName() {
		return NAME;
	}
	
	@Override
	public String getHelp() {
		return HELP;
	}
}
