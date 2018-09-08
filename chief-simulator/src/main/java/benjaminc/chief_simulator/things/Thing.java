package benjaminc.chief_simulator.things;

import java.awt.Graphics;
import java.util.Map;

public interface Thing {
	
	public abstract void draw(Graphics g, int x, int y, int w, int h);
	public abstract Thing duplicate();
	public abstract boolean isSame(Thing t);
	public abstract Map<DataMapKey, Object> getDataMap();
}
