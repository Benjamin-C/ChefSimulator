package benjaminc.chef_simulator.control.command;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.control.Cook;

public class MoveCommand implements Command {

	private final String NAME = "move";
	private final String HELP = 
			"Moves a player.\n"
			+ "move <player> <x> <y>\n"
			+ "player: the name of the player to move\n"
			+ "x: the x location to move to\n"
			+ "y: the y location to move to\n"
			+ "Does not work yet";
	private final int ARG_COUNT = 3;
	
	@Override
	public boolean execute(String[] args) {
		if(args.length > ARG_COUNT) {
			for(Cook c : Game.getCooks()) {
				if(c.getName().equals(args[1])) {
					int x = -1;
					int y = -1;
					parseNum(args[2], 0, Game.getRoom().getWidth());
					try {
						y = Integer.parseInt(args[3]);
					} catch (NumberFormatException e) {
						Game.chat("X is not a valid number");
					}
					
					
					
				} else {
					Game.chat("Move can not find cook");
					return false;
				}
			}
			for(int i = 0; i < ARG_COUNT; i++) {
				
				Game.chat(args[i+1]);
			}
			return true;
		}
		Game.chat(HELP);
		return false;
	}

	/**
	 * Parses numStr and checks if min &le num &ls max
	 * @param numStr 	the {@link String} to parse
	 * @param min		the int min value inclusice
	 * @param max		the int max value exclusive
	 * @param msgName	the {@link String} name to give the value in the error message
	 * @return 			the number, or {@link Integer#MIN_VALUE} if something failed
	 */
	private int parseNum(String numStr, int min, int max, String msgName) {
		try {
			int num = Integer.parseInt(numStr);
			if(num >= min && num < max) {
				return num;
			} else {
				Game.chat(msgName + " is out of bounds. " + min + " <= x < " + max);
			}
		} catch (NumberFormatException e) {
			Game.chat(msgName + " is not a valid number");
		}
		return Integer.MIN_VALUE;
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
