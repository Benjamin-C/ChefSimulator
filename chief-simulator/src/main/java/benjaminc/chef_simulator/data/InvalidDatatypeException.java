package benjaminc.chef_simulator.data;

public class InvalidDatatypeException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 6409473077500295042L;

	public InvalidDatatypeException(String msg) {
		super(msg);
	}
}
