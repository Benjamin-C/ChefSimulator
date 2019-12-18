package benjaminc.chef_simulator.control.command;

import benjaminc.chef_simulator.Game;

public class MoveCommand implements Command {

	private final String NAME = "move";
	private final String HELP = 
			"Moves a player.\n"
			+ "move <player> <x> <y>\n"
			+ "player: the name of the player to move\n"
			+ "x: the x location to move to\n"
			+ "y: the y location to move to\n"
			+ "Does not work yet";
	
	@Override
	public void execute(String[] args) {
		Game.chat(HELP);
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public String getCommandHelp() {
		return HELP;
	}

}
