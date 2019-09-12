package benjaminc.chief_simulator.graphics.tools;

import java.awt.Color;
import java.awt.Graphics;
import benjaminc.chief_simulator.data.DataMap;
import benjaminc.chief_simulator.graphics.GraphicalThing;

public class GraphicalPlate extends GraphicalThing {

	public GraphicalPlate(DataMap data) {
		super(data);
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		prep();
		
		g.setColor(new Color(255-32, 255-32, 255-32));
		g.fillOval(x+(int)(w*0.05),  y+(int)(h*0.05), (int)(w*0.9),  (int)(h*0.9));
		g.setColor(new Color(255-16, 255-16, 255-16));
		g.fillOval(x+(int)(w*0.15),  y+(int)(h*0.15), (int)(w*0.7),  (int)(h*0.7));
	}
}
