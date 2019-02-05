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
				break;
			}
		}
		if(!didit) {
			throw new InvalidDatatypeException("DataMapValue may not be of type " + value.getClass().getSimpleName());
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
	public FoodState getFoodState() throws InvalidDatatypeException {
		if(value instanceof FoodState) { return (FoodState) value;
		} else { throw new InvalidDatatypeException(throwErrorMessage(value, "FoodState")); }
	}
	private String throwErrorMessage(Object provided, String requiredType) {
		return "DataMapValue is type" + provided.getClass().getSimpleName() + ", not " + requiredType;
	}
	
	public void update(Object upd) throws InvalidDatatypeException {
		if(value.getClass().equals(upd.getClass())) {
			value = upd;
		} else {
			throw new InvalidDatatypeException("DataMapValue recived type "
					+ upd.getClass().getSimpleName() + ", but it requires type " + value.getClass().getSimpleName());
		}
	}
	
	public Object getObject() {
		return value;
	}
}
