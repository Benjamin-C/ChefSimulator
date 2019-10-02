package benjaminc.chef_simulator.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import benjaminc.chef_simulator.data.DataMapKey;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.CustomDrawingThing;
import benjaminc.chef_utils.data.FoodState;
import benjaminc.chef_utils.graphics.Shape;
import benjaminc.chef_utils.graphics.Texture;

public class GraphicalDrawer {

	protected Graphics g;
	
	public GraphicalDrawer(Graphics g) {
		this.g = g;
	}
	public void draw(Thing t, int x, int y, int w, int h) {
		if(t != null) {
			if(t instanceof CustomDrawingThing) {
				((CustomDrawingThing) t).draw(this, x, y, w, h);
			} else {
				List<Shape> s = new ArrayList<Shape>();
				if(t.getDataMap() == null) {
					System.out.println("A " + t + " has no data map");
				}
				if(t.getDataMap().containsKey(DataMapKey.FOOD_STATE)) {
					s = ((Texture) t.getDataMap().get(DataMapKey.TEXTURE)).get((FoodState) t.getDataMap().get(DataMapKey.FOOD_STATE));
				} else {
					s = ((Texture) t.getDataMap().get(DataMapKey.TEXTURE)).get(FoodState.RAW);
				}
				drawTexture(s, x, y, w, h);
			}
		} else {
			drawTexture(null, x, y, w, h);
		}
	}
	
	public void drawTexture(List<Shape> s, int x, int y, int w, int h) {
		if(s != null && s.size() > 0) {
			for(int i = s.size() - 1; i >= 0; i--) {
				s.get(i).draw(w, h, x, y, g);
			}
		} else {
			g.setColor(Color.MAGENTA);
			g.fillRect(x, y, w/2, h/2);
			g.fillRect(x + w/2, y + h/2, w/2, h/2);
			g.setColor(Color.BLACK);
			g.fillRect(x, y + h/2, w/2, h/2);
			g.fillRect(x + w/2, y, w/2, h/2);
			g.setColor(Color.WHITE);
			g.setFont(g.getFont().deriveFont(w/2f));
			String st = "s " + (w/32f);
			g.drawString(st, x, y);
		}
	}
}
