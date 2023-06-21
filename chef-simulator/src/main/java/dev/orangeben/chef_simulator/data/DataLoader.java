package dev.orangeben.chef_simulator.data;

public interface DataLoader {
	
	/**
	 * Process a data string
	 * @param data	the {@link String} of data
	 */
	public abstract void processData(String data);

}
