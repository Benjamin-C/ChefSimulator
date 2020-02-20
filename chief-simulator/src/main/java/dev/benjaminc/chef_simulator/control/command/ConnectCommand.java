package dev.benjaminc.chef_simulator.control.command;

import dev.benjaminc.chef_simulator.Game;

public class ConnectCommand implements Command{
	
	private final String NAME = "connect";
	private final String HELP = 
			"Connect to a game server.\n"
			+ "Usage: /connect <ip> <port>\n"
			+ "ip: the IP of the server. Can be localhost"
			+ "port: the port to connect to";
					
	private final int ARG_COUNT = 2;
	
	@Override
	public boolean execute(String[] args) {
		if(args.length > ARG_COUNT) {
			String ip = args[1];
			int port = CommandUtils.parseNum(args[2], 0, 65535, 25565, "port");
			if(ip != "" && port >= 0) {
				Game.connectToServer(ip, port);
				Game.chat("Connected to server");
				return true;
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
