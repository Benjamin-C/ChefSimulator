package benjaminc.chef_simulator.control.command;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.control.Chef;
import benjaminc.chef_simulator.control.Direction;
import benjaminc.chef_simulator.data.location.Location2d;

public class MoveCommand implements Command {

	private final String NAME = "move";
	private final String HELP = 
			"Moves a player.\n"
			+ "Usage: move <player> <x> <y> [dir] [check]\n"
			+ "player: the name of the player to move\n"
			+ "x: the x location to move to\n"
			+ "y: the y location to move to\n"
			+ "dir = up|down|left|right: the direction to face"
			+ "check = true|false: Check if the move is legal. Defaults to false";
	private final int ARG_COUNT = 3;
	
	@Override
	public boolean execute(String[] args) {
		if(args.length > ARG_COUNT) {
			for(Chef c : Game.getCooks()) {
				if(c.getName().equalsIgnoreCase(args[1])) {
					int x = -1;
					int y = -1;
					Direction dir = c.getDirection();
					x = CommandUtils.parseNum(args[2], 0, Game.getRoom().getWidth(), c.getLocation().getX(), "X");
					y = CommandUtils.parseNum(args[3], 0, Game.getRoom().getWidth(), c.getLocation().getY(), "Y");
					if(args.length > ARG_COUNT + 1) {
						if(!args[4].equals("~")) {
							try {
								dir = Direction.valueOf(args[4].toUpperCase());
							} catch(IllegalArgumentException e) {
								Game.chat("dir is not a valid direction");
							}
						}
					}
					if(x != Integer.MIN_VALUE && y != Integer.MIN_VALUE) {
						Game.chat("Moving to " + x + ", " + y);
						Location2d loc = new Location2d(x, y);
						boolean check = true;
						if(args.length > ARG_COUNT + 2) {
							check = Boolean.parseBoolean(args[5]);
						}
						if(check && Game.getRoom().getSpace(loc).isSolid()) {
							Game.chat("The requested move space is solid");
						} else {
							c.setLocation(new Location2d(x, y));
							c.setDirection(dir);
						}
						return true;
					} else {
						return false;
					}
				}
			}
			Game.chat("Move can not find cook");
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
