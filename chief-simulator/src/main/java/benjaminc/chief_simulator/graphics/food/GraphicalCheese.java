package benjaminc.chief_simulator.graphics.food;

import java.awt.Color;
import java.awt.Graphics;
import benjaminc.chief_simulator.data.DataMap;
import benjaminc.chief_simulator.graphics.GraphicalThing;

public class GraphicalCheese extends GraphicalThing {
	
	public GraphicalCheese(DataMap data) {
		super(data);
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		prep();
		
		g.setColor(Color.YELLOW);
		g.fillRect(x+(w/20), y+(h/20), w-(w/10), h-(h/10));
	}
}
