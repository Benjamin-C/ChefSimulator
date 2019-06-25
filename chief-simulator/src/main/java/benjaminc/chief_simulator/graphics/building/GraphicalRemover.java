package benjaminc.chief_simulator.graphics.building;

import java.awt.Color;
import java.awt.Graphics;
import benjaminc.chief_simulator.data.DataMap;
import benjaminc.chief_simulator.graphics.GraphicalThing;

public class GraphicalRemover extends GraphicalThing {

	public GraphicalRemover(DataMap data) {
		super(data);
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		prep();
		
		int indw = w / 8;
		int indh = h / 8;
		g.setColor(new Color(100, 100, 100));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x,  y,  w,  h);
		g.setColor(new Color(16, 16, 16));
		g.drawRect(x+indw,  y+indh,  w-(2*indw),  h-(2*indh));
		g.setColor(new Color(48, 48, 48));
		g.fillRect(x+indw,  y+indw,  w-(2*indw),  h-(2*indh));
		g.setColor(new Color(32, 32, 32));
		g.fillRect(x+(int)(indw*1.35),  y+(int)(indw*1.35),  w-(int)(2.7*indw),  h-(int)(2.7*indh));
		g.setColor(new Color(16, 16, 16));
		g.fillRect(x+(int)(indw*1.7),  y+(int)(indw*1.7),  w-(int)(3.4*indw),  h-(int)(3.4*indh));
		g.setColor(new Color(64, 0, 0));
		g.fillRect(x+(int)(indw*2.05),  y+(int)(indw*2.05),  w-(int)(4.1*indw),  h-(int)(4.1*indh));
	}
}
