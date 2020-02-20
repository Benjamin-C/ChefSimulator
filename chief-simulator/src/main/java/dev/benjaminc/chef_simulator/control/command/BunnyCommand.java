package dev.benjaminc.chef_simulator.control.command;

import dev.benjaminc.chef_simulator.Game;
import dev.benjaminc.chef_simulator.chef_graphics.GameSpace;
import dev.benjaminc.chef_simulator.data.location.Location2d;
import dev.benjaminc.chef_simulator.rooms.Room;

public class BunnyCommand implements Command{
	
	private final String NAME = "bunny";
	private final String HELP = 
			"Fills Bunnyland with Bunnies.\n"
			+ "Usage: /bunny";
					
	private final int ARG_COUNT = 0;
	
	@Override
	public boolean execute(String[] args) {
		if(args.length >= ARG_COUNT) {
			Room r = Game.getRoom();
			Game.openglEngine.mod = true;
			for(int x = 0; x < r.getWidth(); x++) {
				for(int y = 0; y < r.getHeight(); y++) {
					GameSpace s = r.getSpace(new Location2d(x, y));
					for(int z = 0; z < s.size(); z++) {
						Game.openglEngine.newBunny(x, z, y, 0.0f, 0.0f, 0.0f);
					}
				}
			}
			Game.openglEngine.mod = false;
			return true;
		} else {
			return false;
		}
	}
	
	@Override public String getName() {
		return NAME;
	}
	
	@Override
	public String getHelp() {
		return HELP;
	}
}
