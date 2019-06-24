package benjaminc.chief_simulator.data;

import java.util.ArrayList;
import java.util.List;

public class DataMapArray {
	
	protected List<DataMapValue> data;
	
	public DataMapArray() {
		data = new ArrayList<DataMapValue>();
	}
	public DataMapArray(List<?> d) {
		this();
		for(Object o : d) {
			add(new DataMapValue(o));
		}
	}
	
	public List<DataMapValue> getList() {
		return data;
	}
	public void setList(List<DataMapValue> list) {
		data = list;
	}
	public void add(DataMapValue v) {
		data.add(v);
	}
	public void add(int i, DataMapValue v) {
		data.add(i, v);
	}
	public void clear() {
		data.clear();
	}
	public boolean contains(DataMapValue v) {
		return data.contains(v);
	}
	public DataMapValue get(int i) {
		return data.get(i);
	}
	public DataMapValue remove(int i) {
		return data.remove(i);
	}
	public boolean remove(DataMapValue v) {
		return data.remove(v);
	}
	public int size() {
		return data.size();
	}

}
