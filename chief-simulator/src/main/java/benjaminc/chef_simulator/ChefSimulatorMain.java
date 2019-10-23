package benjaminc.chef_simulator;

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
	public static void run(int sc, int fs, boolean lm) {
		new Game(sc, fs, lm);
	}

}
