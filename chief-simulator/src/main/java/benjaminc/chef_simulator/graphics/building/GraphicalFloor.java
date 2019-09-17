package benjaminc.chef_simulator.graphics.building;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.graphics.GraphicalThing;

public class GraphicalFloor extends GraphicalThing {

	public GraphicalFloor(DataMap data) {
		super(data);
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		prep();
		
		g.setColor(new Color(180, 180, 180));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(120, 120, 120));
		g.fillRect(x,  y,  w/2,  h/2);
		g.fillRect(x+(int)(w*.5),  y+(int)(h*.5),  w/2,  h/2);
		g.setColor(new Color(20, 20, 20));
		g.drawRect(x,  y,  w,  h);
	}
}
