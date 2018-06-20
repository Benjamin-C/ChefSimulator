package benjaminc.chief_simulator.graphics.building;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import benjaminc.chief_simulator.graphics.GraphicalThing;
import benjaminc.chief_simulator.things.food.FoodState;

public class GraphicalStove implements GraphicalThing {

	public static final int VARIANT_COUNT = 1;
	protected int variant;
	public GraphicalStove(int variant) {
		this.variant = variant;
	}
	
	@Override
	public void draw(Graphics ga, int x, int y, int w, int h) {
		Graphics2D g = (Graphics2D) ga;
		int indw = w / 8;
		int indh = h / 8;
		g.setColor(new Color(64, 64, 64));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x,  y,  w,  h);
		g.setColor(new Color(128, 128, 128));
		g.fillRect(x + (w/4), y + (h/4), (w/2), (h/2));
		g.setColor(new Color(255, 128, 0));
		g.fillOval(x + (int)(w*0.275), y + (int)(h*0.275), (int)(w*0.45), (int)(h*0.45));
		g.setColor(new Color(0, 0, 0));
		g.setStroke(new BasicStroke(3));
		//Top Left
		g.drawLine(x+(w/4), y+(h/4), x+(int)(w*0.4), y+(int)(h*0.4));
		//Bottom Left
		g.drawLine(x+(w/4), y+(int)(h*.75), x+(int)(w*0.4), y+(int)(h*0.6));
		//Top Right
		g.drawLine(x+(int)(w*.75), y+(h/4), x+(int)(w*0.6), y+(int)(h*0.4));
		//Bottom Right
		g.drawLine(x+(int)(w*.75), y+(int)(h*.75), x+(int)(w*0.6), y+(int)(h*0.6));
		g.setStroke(new BasicStroke(1));
	}
	
	@Override
	public void setState(FoodState state) {
		
	}

	@Override
	public void setVariant(int var) {
		variant = var;
	}

}
