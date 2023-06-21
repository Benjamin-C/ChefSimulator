package dev.orangeben.chef_simulator.control.command;

public interface Command {

	/**
	 * Executes a command. args[0] is the name of the command.
	 * 
	 * @param args An array of {@link String} args for the command
	 */
	public abstract boolean execute(String[] args);
	/**
	 * Gets the name of the command
	 * 
	 * @return the {@link String} name
	 */
	public abstract String getName();
	/**
	 * Gets the help text of the command. Use "\n" to create a new line.<br/>
	 * Must include the following separated by line breaks:<ul>
	 * <li>	A brief description of what the command does </li>
	 * <li> The syntax for the command preceded by "Usage: "
	 * <li>	All arguments and their uses in form arg: use</li>
	 * <li> Any other information as needed</li>
	 * </ul>
	 * Syntax will be in the form:<ul>
	 * <li> Required arguments with angle brackets EX: &ltarg&gt</li>
	 * <li> Optional arguments with square brackets EX: [arg]</li>
	 * <li> Arguments have options separated by | if applicable EX: [op1 | op2]</li>
	 * <li> All required arguments must come before any optional arguments.</li>
	 * </ul>
	 * 
	 * @return the {@link String} help text
	 */
	public abstract String getHelp();
	
}
