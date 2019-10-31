package benjaminc.chef_simulator.data;

/**
 * @author Benjamin-C
 *
 */
public interface Savable {
	
	/**
	 * UNFINISHED no reliability is guaranteed
	 * Saves this to a JSON {@link String}
	 * Addes quotes if needed for JSON type
	 * @return the JSON {@link String}
	 */
	public abstract String asJSON();
}
