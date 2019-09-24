package benjaminc.chef_simulator.graphics.food;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.graphics.GraphicalThing;

public class GraphicalTomato extends GraphicalThing {

	public GraphicalTomato(DataMap data) {
		super(data);
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		prep();
		
		switch(state) {
		case CHOPPED: {
			g.setColor(new Color(255, 0, 0));
			g.fillOval(x+(int)(w*.05), y+(int)(h*.05), (int)(w*.9), (int)(h*.9));
			g.setColor(new Color(200, 0, 0));
			g.fillOval(x+(int)(w*.1), y+(int)(h*.1), (int)(w*0.8), (int)(h*0.8));
			g.setColor(new Color(255, 0, 0));
			g.fillRect(x+(int)(w*0.45), y+(int)(h*.1), (int)(w*0.1), (int)(h*0.8));
			g.fillRect(x+(int)(w*.1), y+(int)(h*0.45), (int)(w*0.8), (int)(h*0.1));
		} break;
		case CHOPPED_COOKED:
			break;
		case COOKED:
			break;
		case RAW: {
			g.setColor(new Color(255, 0, 0));
			g.fillOval(x+(int)(w*.05), y+(int)(h*.12), (int)(w*.9), (int)(h*.76));
		} break;
		default: break;
		}
	}
}
