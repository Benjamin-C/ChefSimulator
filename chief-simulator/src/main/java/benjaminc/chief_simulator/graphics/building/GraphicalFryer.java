package benjaminc.chief_simulator.graphics.building;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import benjaminc.chief_simulator.graphics.GraphicalThing;
import benjaminc.chief_simulator.things.food.FoodState;

public class GraphicalFryer implements GraphicalThing {

	public static final int VARIANT_COUNT = 1;
	protected int variant;
	public GraphicalFryer(int variant) {
		this.variant = variant;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		int n = w/6;
		g.setColor(new Color(64, 64, 64));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x,  y,  w,  h);
		g.setColor(new Color(128, 128, 128));
		g.fillRect(x+(int)(.5*n),  y+(int)(.5*n),  w-(1*n),  h-(1*n));
		g.setColor(Color.YELLOW);
		g.fillRect(x+n,  y+n, (w/2)-(int)(1.5*n), h-(3*n));
		g.fillRect(x+(w/2)+(int)(.5*n),  y+n, (w/2)-(int)(1.5*n), h-(3*n));
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x+(int)(.5*n),  y+(int)(.5*n),  w-(1*n),  h-(1*n));
		((Graphics2D) g).setStroke(new BasicStroke(w/31));
		g.drawRect(x+n,  y+n, (w/2)-(int)(1.5*n), h-(3*n));
		g.drawRect(x+(w/2)+(int)(.5*n),  y+n, (w/2)-(int)(1.5*n), h-(3*n));
		((Graphics2D) g).setStroke(new BasicStroke(1));
		g.fillRect(x+(int)(1.5*n), y+(4*n), w=(int)(.5*n) , h=(int)(.9*n));
		g.fillRect(x+(int)(4.04*n), y+(4*n), w=(int)(.5*n) , h=(int)(.9*n));
		g.fillOval(x+(int)(1.4999*n), y+(int)(4.6*n), w=(int)(.5*n) , h=(int)(.5*n));
		g.fillOval(x+(int)(4.04*n), y+(int)(4.6*n), w=(int)(.5*n) , h=(int)(.5*n));
	}

	@Override
	public void setState(FoodState state) {
		
	}

	@Override
	public void setVariant(int var) {
		variant = var;
	}

}
