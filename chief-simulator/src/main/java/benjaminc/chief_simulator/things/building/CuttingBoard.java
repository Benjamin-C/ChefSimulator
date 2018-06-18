package benjaminc.chief_simulator.things.building;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.FoodThing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.ToolThing;

public class CuttingBoard implements ToolThing, SolidThing {

	public CuttingBoard() {
		super();
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		int indw = w / 8;
		int indh = h / 8;
		g.setColor(new Color(64, 64, 64));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x,  y,  w,  h);
		g.setColor(new Color(235, 235, 235));
		g.fillRect(x+indw,  y+(int)(h*.075),  w-(2*indw),  h-indh);
		g.setColor(new Color(64, 64, 64));
		g.fillOval(x+(int)(w*.35), y+(int)(h*.1), (int)(w*.3), (int)(h*.1));
		g.setColor(new Color(200, 200 ,200));
		g.drawRect(x+(int)(w*0.175), y+(int)(h*0.25), (int)(w*0.65), (int)(h*.65));
		
	}
	
	@Override
	public List<Thing> useTool(Thing t) {
		List<Thing> temp = new ArrayList<Thing>();
		if(t instanceof FoodThing) {
			temp.add(((FoodThing) t).getChoppedThing());
		} else {
			temp.add(t);
		}
		return temp;
	}

	@Override
	public Thing duplicate() {
		return new CuttingBoard();
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
