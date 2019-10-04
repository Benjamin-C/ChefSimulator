package benjaminc.chef_simulator.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import benjaminc.chef_simulator.control.Direction;
import benjaminc.chef_simulator.data.DataMapKey;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.CustomDrawingThing;
import benjaminc.chef_simulator.things.types.DirectionalThing;
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
				Direction d = null;
				if(t instanceof DirectionalThing) {
					d = (Direction) t.getDataMap().get(DataMapKey.DIRECTION);
				}
				drawTexture(s, x, y, w, h, d);
			}
		} else {
			drawTexture(null, x, y, w, h);
		}
	}
	
	public void drawTexture(List<Shape> s, int x, int y, int w, int h, Direction d) {
		if(s != null && s.size() > 0) {
			for(int i = s.size() - 1; i >= 0; i--) { // Running through list backwards
				int dir = 0;
				if(d != null) {
					dir = d.getId();
				}
				s.get(i).draw(w, h, x, y, g, dir);
			}
			if(d != null && false) {
				int b = w/16;
				int c = (w/2)-(b/2);
				g.setColor(Color.RED);
				switch(d) {
				case DOWN: g.setColor(Color.RED); g.fillRect(x, y+h-b, w, b); g.fillRect(x+c, y+h/2, b, h/2); break;
				case LEFT: g.fillRect(x, y, b, h); g.fillRect(x, y+c, w/2, b); break;
				case RIGHT: g.fillRect(x+w-b, y, b, h); g.fillRect(x+w/2, y+c, w/2, b); break;
				case UP: g.fillRect(x, y, w, b); g.fillRect(x+c, y, b, h/2); break;
				default: break;
				}
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
	public void drawTexture(List<Shape> s, int x, int y, int w, int h) {
		drawTexture(s, x, y, w, h, null);
	}
}
