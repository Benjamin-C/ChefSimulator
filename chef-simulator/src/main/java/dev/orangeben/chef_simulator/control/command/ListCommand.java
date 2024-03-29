package dev.orangeben.chef_simulator.control.command;

import dev.orangeben.chef_simulator.Game;
import dev.orangeben.chef_simulator.control.Chef;
import dev.orangeben.chef_simulator.data.location.Location2d;
import dev.orangeben.chef_simulator.things.Thing;

public class ListCommand implements Command{
	
	private final String NAME = "list";
	private final String HELP = 
			"Lists game information. If no args are provided, it lists cooks.\n"
			+ "Usage: /list [x] [y]\n"
			+ "x: the x position to list"
			+ "y: tye y position to list. Must be stated if x is stated";
					
	private final int ARG_COUNT = 0;
	
	@Override
	public boolean execute(String[] args) {
		if(args.length > ARG_COUNT) {
			if(args.length == 1) {
				String out = "Cooks: ";
				boolean fencepost = false;
				for(Chef c : Game.getCooks()) {
					if(fencepost) {
						out += ", ";
					} else {
						fencepost = true;
					}
					out += c.getName();
				}
				Game.chat(out);
				return true;
			} else if(args.length == 3) {
				int x = CommandUtils.parseNum(args[1], 0, Game.getRoom().getWidth(), Integer.MIN_VALUE, "X");
				int y = CommandUtils.parseNum(args[2], 0, Game.getRoom().getHeight(), Integer.MIN_VALUE, "Y");
				if(x >= 0 && y >= 0) {
					String out = "Things at " + x + ", " + y + ": ";
					boolean fencepost = false;
					for(Thing t : Game.getRoom().getSpace(new Location2d(x, y)).getThings()) {
						if(fencepost) {
							out += "\n";
						} else {
							fencepost = true;
						}
						out += t.getName() + "(" + t.getUUID() + ")";
					}
					Game.chat(out);
					return true;
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
