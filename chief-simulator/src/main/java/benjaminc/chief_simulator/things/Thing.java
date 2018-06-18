package benjaminc.chief_simulator.things;

import java.awt.Graphics;

public interface Thing {
	
	public abstract void draw(Graphics g, int x, int y, int w, int h);
	public abstract Thing duplicate();
	public abstract boolean isSame(Thing t);
}