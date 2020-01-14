package benjaminc.chef_simulator.control.command;

import java.util.UUID;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.rooms.Room;
import benjaminc.chef_simulator.things.Thing;

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
					Thing t = r.getSpace(new Location(x, y)).getThing(UUID.fromString(args[1]));
					if(t != null) {
						Game.chat(t.toString());
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
