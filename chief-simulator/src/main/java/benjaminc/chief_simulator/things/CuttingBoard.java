package benjaminc.chief_simulator.things;

import java.awt.Color;
import java.awt.Graphics;

import benjaminc.chief_simulator.things.types.FoodThing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.ToolThing;

public class CuttingBoard implements ToolThing, SolidThing {

	public CuttingBoard() {
		super();
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(new Color(64, 64, 64));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(192, 192, 192));
		g.fillRect(x+8,  y+8,  w-16,  h-16);
	}
	
	@Override
	public Thing useTool(Thing t) {
		if(t instanceof FoodThing) {
			return ((FoodThing) t).getChoppedThing();
			
		} else {
			System.out.println("not Food");
			return t;
		}
	}
}
