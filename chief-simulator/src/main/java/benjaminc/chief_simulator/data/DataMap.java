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
	public void update() {
		
	}
	public boolean containsKey(DataMapKey test) {
		return dataMap.containsKey(test);
	}
	public void put(DataMapKey key, Object value) {
		if(checkForType(value, key.getType())) {
			dataMap.put(key, value);
		} else {
			throw new InvalidDatatypeException("DataMapValue must be of type " + key.getType() + ", not " + value.getClass().getSimpleName());
		}
	}
	public DataMap clone() {
		DataMap newdm = new DataMap();
		for(DataMapKey k : dataMap.keySet()) {
			newdm.put(k, dataMap.get(k));
		}
		return newdm;
	}
	public boolean equals(DataMap dm) {
		if(dataMap.keySet().size() != dm.dataMap.keySet().size()) {
			return false;
		}
		for(DataMapKey k : dataMap.keySet()) {
			if(!dm.containsKey(k)) {
				return false;
			}
			if(!dataMap.get(k).equals(dm.get(k))) {
				return false;
			}
		}
		return true;
	}
	private boolean checkForType(Object candidate, Class<?> type){
	    return type.isInstance(candidate);
	}

}
