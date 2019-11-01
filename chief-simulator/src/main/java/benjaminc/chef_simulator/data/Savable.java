package benjaminc.chef_simulator.data;

/**
 * <p><h3>Methods</h3>
 * public {@link String} {@link Savable#asJSON()}
 * </p>
 * @author Benjamin-C
 *
 */
public interface Savable {
	
	/**
	 * Saves this to a JSON {@link String}<br/>
	 * Addes quotes if needed for JSON type<br/>
	 * @return the JSON {@link String}
	 */
	public abstract String asJSON();
}
