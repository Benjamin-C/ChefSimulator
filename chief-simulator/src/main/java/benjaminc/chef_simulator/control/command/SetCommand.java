package benjaminc.chef_simulator.control.command;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.control.EventHandler;
import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.location.Location2d;
import benjaminc.chef_simulator.data.location.Location3d;
import benjaminc.chef_simulator.events.ThingAddEvent;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.ThingType;

public class SetCommand implements Command {

	private final String NAME = "set";
	private final String HELP = 
			"Puts a thing in the room.\n"
			+ "Usage: set <thing> <x> <y> [elev] [data]\n"
			+ "thing: the name of the Thing to move\n"
			+ "x: the x location to set at\n"
			+ "y: the y location to set at\n"
			+ "elev: the elecation to set at\n"
			+ "data: the JSON DataMap of the thing";
	private final int ARG_COUNT = 3;
	
	@Override
	public boolean execute(String[] args) {
		if(args.length > ARG_COUNT) {
			int x = CommandUtils.parseNum(args[2], 0, Game.getRoom().getWidth(), Integer.MIN_VALUE, "X");
			int y = CommandUtils.parseNum(args[3], 0, Game.getRoom().getWidth(), Integer.MIN_VALUE, "Y");
			int elev = -1;
			DataMap dm = null;
			if(args.length > ARG_COUNT + 1) {
				elev = CommandUtils.parseNum(args[4], 0, Integer.MAX_VALUE, Integer.MAX_VALUE, "E");
				if(args.length > ARG_COUNT + 2) {
					Game.chat(args[5]);
					dm = new DataMap(args[5]);
				}
			}
			try {
				Thing t = ThingType.getThingOfType(ThingType.valueOf(args[1].toUpperCase()), dm);
				if(x >= 0 && y >= 0 && t != null) {
					if(elev == -1) {
						EventHandler.fireEvent(new ThingAddEvent(t, new Location2d(x, y)));
					} else {
						EventHandler.fireEvent(new ThingAddEvent(t, new Location3d(x, y, elev)));
					}
					Game.chat("Spawned " + t.getName());
					return true;
				} else {
					Game.chat("Could not spawn thing as specified");
				}
			} catch(IllegalArgumentException e) {
				Game.chat("Type is not valid");
				return false;
			}
		}
		return false;
	}
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getHelp() {
		return HELP;
	}

}
