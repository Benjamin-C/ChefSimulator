package benjaminc.chief_simulator.graphics.building;

import java.awt.Color;
import java.awt.Graphics;
import benjaminc.chief_simulator.data.DataMap;
import benjaminc.chief_simulator.graphics.GraphicalThing;

public class GraphicalFryer extends GraphicalThing {

	public GraphicalFryer(DataMap data) {
		super(data);
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		prep();
		
		int n = w/16;
		g.setColor(new Color(64, 64, 64));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x,  y,  w,  h);
		g.setColor(Color.YELLOW);
		g.fillRect(x+n,  y+n, (w/2)-n, y+n);
		g.fillRect(x+(w/2)+n,  y+n, w-n, y+n);
	}
}
