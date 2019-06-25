package benjaminc.chief_simulator.graphics.food;

import java.awt.Color;
import java.awt.Graphics;
import benjaminc.chief_simulator.data.DataMap;
import benjaminc.chief_simulator.graphics.GraphicalThing;

public class GraphicalLettuce extends GraphicalThing {

	public GraphicalLettuce(DataMap data) {
		super(data);
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		prep();
		
		switch(state) {
		case CHOPPED: {
			g.setColor(new Color(0, 180 ,0));
			g.fillRect(x+(int)(w*0.9), y+(int)(h*.425), (int)(w*.1), (int)(h*.15));
			g.setColor(new Color(0, 255, 0));
			g.fillOval(x+(w/20),  y+(h/5),  w-(int)(w*.15),  h-(int)(h/2.5));
		} break;
		case CHOPPED_COOKED:
			break;
		case COOKED:
			break;
		case RAW: {
			g.setColor(new Color(0, 255, 0));
			g.fillOval(x+(w/20),  y+(h/20),  w-(w/10),  h-(h/10));
		} break;
		}
	}
}
