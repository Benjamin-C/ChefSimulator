package benjaminc.chef_simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Benjamin-C
 *
 */
public class ChefSimulatorMain {
	
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
				new Game(sc, fs, lm, true).playJSONMap(s);
			} catch (FileNotFoundException e) {
				System.out.println("There is no file there or something went bad");
			}
		} else {
			new Game(sc, fs, lm, true).playDefaultGame();
		}
		
	}

}
