package benjaminc.chief_simulator.data;

import java.util.HashMap;
import java.util.Map;

public class DataMap {
	
	protected Map<DataMapKey, Object> dataMap;
	
	public DataMap() {
		dataMap = new HashMap<DataMapKey, Object>();
	}
	
	public Object get(DataMapKey key) {
		return dataMap.get(key);
	}
	
	public void put(DataMapKey key, Object value) {
		if(key.getType() == value.getClass()) {
			dataMap.put(key, value);
		} else {
			throw new InvalidDatatypeException("DataMapValue must be of type " + key.getType() + ", not " + value.getClass().getSimpleName());
		}
	}

}
