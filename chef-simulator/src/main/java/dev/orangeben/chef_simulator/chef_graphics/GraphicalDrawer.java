package dev.orangeben.chef_simulator.chef_graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import dev.orangeben.chef_simulator.control.Direction;
import dev.orangeben.chef_simulator.data.FoodState;
import dev.orangeben.chef_simulator.data.DataMap.DataMapKey;
import dev.orangeben.chef_simulator.things.Thing;
import dev.orangeben.chef_simulator.things.types.CustomDrawingThing;
import dev.orangeben.chef_simulator.things.types.DirectionalThing;
import dev.orangeben.chef_utils.graphics.Shape;
import dev.orangeben.chef_utils.graphics.Texture;

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
					String adr = t.toString();
					adr = adr.substring(adr.indexOf("@")+1);
					System.out.println("A " + t.getName() + "@" + adr + " has no data map");
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
				drawTexture(s, x, y, w, h, d, t.getName());
			}
		} else {
			drawTexture(null, x, y, w, h, "null");
		}
	}
	
	public void drawTexture(List<Shape> s, int x, int y, int w, int h, Direction d, String name) {
		if(s != null && s.size() > 0) {
			for(int i = s.size() - 1; i >= 0; i--) { // Running through list backwards
				int dir = 0;
				if(d != null) {
					dir = d.getId();
				}
				s.get(i).draw(w, h, x, y, g, dir);
			}
		} else {
			g.setColor(Color.MAGENTA);
			g.fillRect(x, y, w/2, h/2);
			g.fillRect(x + w/2, y + h/2, w/2, h/2);
			g.setColor(Color.BLACK);
			g.fillRect(x, y + h/2, w/2, h/2);
			g.fillRect(x + w/2, y, w/2, h/2);
			g.setColor(Color.WHITE);
			g.setFont(g.getFont().deriveFont(w/4f));
			g.drawString(name, x, y+(h/2));
		}
	}
	public void drawTexture(List<Shape> s, int x, int y, int w, int h, String name) {
		drawTexture(s, x, y, w, h, null, name);
	}
}
