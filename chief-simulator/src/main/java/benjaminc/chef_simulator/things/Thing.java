package benjaminc.chef_simulator.things;

import java.util.UUID;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.Savable;

public interface Thing extends Savable {

	//public abstract void draw(Graphics g, int x, int y, int w, int h);
	public abstract boolean isSame(Thing t);
	public abstract DataMap getDataMap();
	public abstract void setDataMap(DataMap m);
	public abstract UUID getUUID();
	public abstract Thing clone();
	public abstract boolean equals(Thing t);
	public abstract String getName();
	public abstract Class<?> getSubclass();
	
	/**
	 * @author Benjamin-C
	 *
	 */
	public enum ThingDataKey {
	/** the {@link ThingType} of the {@link Thing} */
	TYPE,
	/** the {@link DataMap} of the {@link Thing} */
	DATAMAP,
	/** the {@link UUID} of the {@link Thing} */
	UUID;
	}
}
