package benjaminc.chief_simulator.things.food;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.FoodThing;

public class ChoppedApple extends Apple {
	
	protected int textureNumber;
	protected final int VARIANT_COUNT;

	public ChoppedApple() {
		super();
		VARIANT_COUNT = 2;
		textureNumber = (int)(Math.random()*VARIANT_COUNT);
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		switch(textureNumber) {
		case 0: { 
			g.setColor(new Color(206, 0, 0));
			g.fillOval(x+(int)(w*0.05),  y+(int)(h*0.05), (int)(w*0.9),  (int)(h*0.9));
			g.setColor(new Color(216, 221, 117));
			g.drawLine(x+(w/2), y+(int)(h*0.05), x+(w/2), y+(int)(h*0.95));
			g.drawOval(x+(w/4), y+(int)(h*0.06), w/2, (int)(h*0.88));
		} break;
		case 1: {
			g.setColor(new Color(57, 219, 46));
			g.fillOval(x+(int)(w*0.05),  y+(int)(h*0.05), (int)(w*0.9),  (int)(h*0.9));
			g.setColor(new Color(249, 252, 189));
			g.drawLine(x+(w/2), y+(int)(h*0.05), x+(w/2), y+(int)(h*0.95));
			g.drawOval(x+(w/4), y+(int)(h*0.06), w/2, (int)(h*0.88));
		} break;
		}
	}
	
	@Override
	public Thing duplicate() {
		return new ChoppedApple();
	}

	@Override
	public boolean isSame(Thing t) {
		if(t.getClass() == this.getClass()) {
			return true;
		} else {
			return false;
		}
	}
}
