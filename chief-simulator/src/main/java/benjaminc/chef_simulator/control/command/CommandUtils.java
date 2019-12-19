package benjaminc.chef_simulator.control.command;

import benjaminc.chef_simulator.Game;

/**
 * Utilities for executing commands
 * @author Benjamin_C
 *
 */
public class CommandUtils {

	/**
	 * Parses numStr and checks if min &le num &ls max. Returns def if the "~" character is supplied
	 * 
	 * @param numStr 	the {@link String} to parse
	 * @param min		the int min value inclusice
	 * @param max		the int max value exclusive
	 * @param def		the int value to use if ~ is supplied
	 * @param msgName	the {@link String} name to give the value in the error message
	 * @return 			the number, or {@link Integer#MIN_VALUE} if something failed
	 */
	public static int parseNum(String numStr, int min, int max, int def, String msgName) {
		try {
			int num = Integer.parseInt(numStr);
			if(inRange(min, max, num, msgName)) {
				return num;
			}
		} catch (NumberFormatException e) {
			if(numStr.charAt(0) == '~') {
				if(numStr.length() > 1) {
					try {
						int num = def + Integer.parseInt(numStr.substring(1));
						if(inRange(min, max, num, msgName)) {
							return num;
						}
					} catch(NumberFormatException e2) {}
				} else {
					return def;
				}			
			}
			Game.chat(msgName + " is not a valid number");
		}
		return Integer.MIN_VALUE;
	}
	
	/**
	 * Checks if a number is in a specified range (min &le num &ls max), and shows a message in chat if it is not.
	 * 
	 * @param min		the int min allowable value
	 * @param max		the int max allowable value
	 * @param num		the int num to check
	 * @param msgName	the {@link String} name to show in the message
	 * @return			the boolean if num is in range
	 */
	public static boolean inRange(int min, int max, int num, String msgName) {
		if(num >= min && num < max) {
			return true;
		} else {
			Game.chat(msgName + " is out of bounds. " + min + " <= x < " + max);
			return false;
		}
	}

}
