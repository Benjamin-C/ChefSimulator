package dev.orangeben.chef_simulator.control.command;

import java.util.UUID;

import dev.orangeben.chef_simulator.Game;
import dev.orangeben.chef_simulator.data.location.Location2d;
import dev.orangeben.chef_simulator.rooms.Room;
import dev.orangeben.chef_simulator.things.Thing;

public class FindCommand implements Command{
	
	private final String NAME = "find";
	private final String HELP = 
			"Finds an item from UUID.\n"
			+ "Usage: /find <UUID>\n"
			+ "UUID: the UUID of the item to find";
					
	private final int ARG_COUNT = 1;
	
	@Override
	public boolean execute(String[] args) {
		if(args.length > ARG_COUNT) {
			Room r = Game.getRoom();
			for(int x = 0; x < r.getWidth(); x++) {
				for(int y = 0; y < r.getHeight(); y++) {
					Thing t = r.getSpace(new Location2d(x, y)).getThing(UUID.fromString(args[1]));
					if(t != null) {
						Game.chat(t.toString());
						System.out.println(t.toString());
						return true;
					}
				}
			}
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
