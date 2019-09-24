package benjaminc.chef_simulator.graphics.food;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.graphics.GraphicalThing;

public class GraphicalBeef extends GraphicalThing {

	public GraphicalBeef(DataMap data) {
		super(data);
	}

	@Override
	public void draw(Graphics ga, int x, int y, int w, int h) {
		prep();
		
		Graphics2D g = (Graphics2D) ga;
		switch(state) {
		case CHOPPED: {
			g.setColor(new Color(230, 65, 30));
			g.fillOval(x+(int)(w*0.05),  y+(int)(h*0.05), (int)(w*0.9),  (int)(h*0.9));
		} break;
		case CHOPPED_COOKED: {
			g.setColor(new Color(140, 70, 30));
			g.fillOval(x+(int)(w*0.05),  y+(int)(h*0.05), (int)(w*0.9),  (int)(h*0.9));
		} break;
		case COOKED: {
			g.setColor(new Color(140, 70, 30));
			g.fillOval(x+(int)(w*0.1),  y+(int)(h*0.1), (int)(w*0.6),  (int)(h*0.6));
			g.fillOval(x+(int)(w*0.5),  y+(int)(h*0.5), (int)(w*0.4),  (int)(h*0.4));
			g.fillOval(x+(int)(w*0.4),  y+(int)(h*0.4), (int)(w*0.43),  (int)(h*0.43));
			g.fillOval(x+(int)(w*0.45),  y+(int)(h*0.45), (int)(w*0.41),  (int)(h*0.41));
			g.fillOval(x+(int)(w*0.35),  y+(int)(h*0.35), (int)(w*0.45),  (int)(h*0.45));
			g.setStroke(new BasicStroke(w/30));
			g.drawLine(x+(int)(w*0.6), y+(int)(h*0.85), x+(int)(w*0.2), y+(int)(h*0.6));
			g.drawLine(x+(int)(w*0.85), y+(int)(h*0.6), x+(int)(w*0.6), y+(int)(h*0.2));
			g.setStroke(new BasicStroke(w/40));
			g.setColor(new Color(255, 180, 140));
			g.drawLine(x+(int)(w*0.75), y+(int)(h*0.675), x+(int)(w*0.375), y+(int)(h*0.325));
			g.drawLine(x+(int)(w*0.65), y+(int)(h*0.775), x+(int)(w*0.275), y+(int)(h*0.425));
			g.setStroke(new BasicStroke(1));
		} break;
		case RAW: {
			g.setColor(new Color(230, 65, 30));
			g.fillOval(x+(int)(w*0.1),  y+(int)(h*0.1), (int)(w*0.6),  (int)(h*0.6));
			g.fillOval(x+(int)(w*0.5),  y+(int)(h*0.5), (int)(w*0.4),  (int)(h*0.4));
			g.fillOval(x+(int)(w*0.4),  y+(int)(h*0.4), (int)(w*0.43),  (int)(h*0.43));
			g.fillOval(x+(int)(w*0.45),  y+(int)(h*0.45), (int)(w*0.41),  (int)(h*0.41));
			g.fillOval(x+(int)(w*0.35),  y+(int)(h*0.35), (int)(w*0.45),  (int)(h*0.45));
			g.setStroke(new BasicStroke(w/30));
			g.drawLine(x+(int)(w*0.6), y+(int)(h*0.85), x+(int)(w*0.2), y+(int)(h*0.6));
			g.drawLine(x+(int)(w*0.85), y+(int)(h*0.6), x+(int)(w*0.6), y+(int)(h*0.2));
			g.setStroke(new BasicStroke(w/40));
			g.setColor(new Color(255, 180, 140));
			g.drawLine(x+(int)(w*0.75), y+(int)(h*0.675), x+(int)(w*0.375), y+(int)(h*0.325));
			g.drawLine(x+(int)(w*0.65), y+(int)(h*0.775), x+(int)(w*0.275), y+(int)(h*0.425));
			g.setStroke(new BasicStroke(1));
		} break;
		default: break;
		}
	}
}
