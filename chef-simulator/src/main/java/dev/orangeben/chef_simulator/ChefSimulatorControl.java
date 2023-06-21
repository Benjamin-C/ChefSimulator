package dev.orangeben.chef_simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import dev.orangeben.chef_simulator.control.EventHandler;
import dev.orangeben.chef_simulator.data.DataLoader;
import dev.orangeben.chef_simulator.events.EventDestination;
import dev.orangeben.chef_simulator.events.SpecialActionEvent;
import dev.orangeben.chef_simulator.events.SpecialActionEvent.SpecialActionEventTypes;

/**
 * @author Benjamin-C
 *
 */
public class ChefSimulatorControl {

	/**
	 * Starts a new Chief Simulator Game
	 * @param sc the int number of pixels for the width and height of each gamespace
	 * @param fs the int number of target frames per second / ticks per second
	 * @param lm the boolean to show the lagometer by default
	 */
	public static void run(int sc, int fs, boolean lm, String ipAndPort) {
		try {
			int c = ipAndPort.indexOf(':');
			String ip = ipAndPort.substring(0, c);
			System.out.println(ip);
			System.out.println(ipAndPort.substring(c+1));
			int port = Integer.parseInt(ipAndPort.substring(c+1));
			Game.connectToServer(ip, port);
			Game.setupGame(sc, fs, lm, true);
			Game.setDataLoader(new DataLoader() {
				@Override public void processData(String data) {
					Game.setDataLoader(null);
					Game.playJSONMap(data);
				}
			});
			EventHandler.fireEvent(new SpecialActionEvent(SpecialActionEventTypes.GET_MAP, ""), EventDestination.SERVER);
			System.out.println("Waiting for data ...");
			System.out.println(Thread.currentThread().getName());
		} catch(NumberFormatException e) {
			System.out.println("A number broke me");
		}
		
	}
	
	/**
	 * Starts a new Chief Simulator Game
	 * @param sc the int number of pixels for the width and height of each gamespace
	 * @param fs the int number of target frames per second / ticks per second
	 * @param lm the boolean to show the lagometer by default
	 */
	public static void run(int sc, int fs, boolean lm, File savefile) {
		if(savefile != null && savefile.exists() && savefile.isFile()) {
        	try {
				Scanner scan = new Scanner(savefile);
				String s = "";
				while(scan.hasNextLine()) {
					s = s + scan.nextLine();
				}
				scan.close();
				Game.setupGame(sc, fs, lm, true);
				Game.playJSONMap(s);
			} catch (FileNotFoundException e) {
				System.out.println("There is no file there or something went bad");
			}
		} else {
			Game.setupGame(sc, fs, lm, true);
			Game.playDefaultGame();
		}	
	}
}
