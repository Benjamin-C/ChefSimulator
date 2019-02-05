package benjaminc.chief_simulator.things.data;

import benjaminc.chief_simulator.things.food.FoodState;

public class DataMapValue {

	protected Object value;
	
	private Class<?> allowedTypes[] = {Integer.class, Double.class, String.class, Boolean.class, FoodState.class};
	public DataMapValue(Object value) throws InvalidDatatypeException {
		setValue(value);
	}
	
	public void setValue(Object value) throws InvalidDatatypeException {
		boolean didit = false;
		for(Class<?> c : allowedTypes) {
			if(c.equals(value.getClass())) {
				this.value = value;
				didit = true;
			}
		}
		if(!didit) {
			throw new InvalidDatatypeException("DataMapValue may not be of type " + value.getClass().getSimpleName());
		}
		if(	value instanceof Integer || value instanceof Double ||
				value instanceof String || value instanceof Boolean ||
				value instanceof FoodState) {
			this.value = value;
		} else {
			
		}
	}
	
	public int getInt() throws InvalidDatatypeException {
		if(value instanceof Integer) { return ((Integer) value).intValue();
		} else { throw new InvalidDatatypeException(throwErrorMessage(value, "int")); }
	}
	public double getDouble() throws InvalidDatatypeException {
		if(value instanceof Double) { return ((Double) value).doubleValue();
		} else { throw new InvalidDatatypeException(throwErrorMessage(value, "double")); }
	}
	public String getString() throws InvalidDatatypeException {
		if(value instanceof String) { return (String) value; }
		else { throw new InvalidDatatypeException(throwErrorMessage(value, "String")); }
	}
	public boolean getBoolean() throws InvalidDatatypeException {
		if(value instanceof Boolean) { return ((Boolean) value).booleanValue();
		} else { throw new InvalidDatatypeException(throwErrorMessage(value, "boolean")); }
	}
	
	public void update(Object upd) throws InvalidDatatypeException {
		if(value instanceof Integer) {
			if(upd instanceof Integer) { value = upd;
			} else { throw new InvalidDatatypeException(throwErrorMessage(value, "int")); }
		} else if(value instanceof Double) {
			if(upd instanceof Double) { value = upd;
			} else { throw new InvalidDatatypeException(throwErrorMessage(value, "double")); }
		} else if(value instanceof String) {
			if(upd instanceof String) { value = upd;
			} else { throw new InvalidDatatypeException(throwErrorMessage(value, "String")); }
		} else if(value instanceof Boolean) {
			if(upd instanceof Boolean) { value = upd;
			} else { throw new InvalidDatatypeException(throwErrorMessage(value, "boolean")); }
		} else {
			throw new InvalidDatatypeException("I do't know what went wrong, but something did!");
		}
	}
	
	private String throwErrorMessage(Object provided, String requiredType) {
		return "DataMapValue is of type " + provided.getClass().getSimpleName() + ", not " + requiredType;
	}
	
	public Object getObject() {
		return value;
	}
}
