package benjaminc.chief_simulator.things;

import java.awt.Graphics;
import benjaminc.chief_simulator.data.DataMap;

public interface Thing {

	public abstract void draw(Graphics g, int x, int y, int w, int h);
	public abstract boolean isSame(Thing t);
	public abstract DataMap getDataMap();
	public abstract Thing clone();
	public abstract boolean equals(Thing t);
}
