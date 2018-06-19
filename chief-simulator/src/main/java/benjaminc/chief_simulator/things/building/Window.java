package benjaminc.chief_simulator.things.building;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import benjaminc.chief_simulator.Game;
import benjaminc.chief_simulator.Objective;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.ToolThing;

public class Window implements ToolThing, Thing, SolidThing{
	
	protected Game game;
	public Window(Game g) {
		game = g;
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		int indw = w / 8;
		int indh = h / 8;
		g.setColor(new Color(100, 100, 100));
		g.fillRect(x,  y,  w,  h);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x,  y,  w,  h);
		g.setColor(new Color(225, 225, 225));
		g.fillRect(x+(int)(indw*.7),  y,  w-(int)(1.4*indw),  h-(int)(indh*.7));
		g.setColor(new Color(192, 192, 192));
		g.fillRect(x+indw,  y,  w-(2*indw),  h-indh);
	}

	@Override
	public Thing duplicate() {
		return new Window(game);
	}

	@Override
	public boolean isSame(Thing t) {
		if(t.getClass() == this.getClass()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Thing> useTool(Thing t) {
		for(int i = 0; i < game.getObjectives().size(); i++) {
			Objective o = game.getObjectives().get(i);
			if(o.isMet(t)) {
				System.out.println("isMet");
				game.completeObjectiives(o);
				break;
			}
		}
		return null;
	}

}
