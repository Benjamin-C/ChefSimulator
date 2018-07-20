package benjaminc.chief_simulator.things.building;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import benjaminc.chief_simulator.Game;
import benjaminc.chief_simulator.Objective;
import benjaminc.chief_simulator.graphics.building.GraphicalWindow;
import benjaminc.chief_simulator.graphics.food.GraphicalApple;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.ToolThing;

public class Window implements ToolThing, Thing, SolidThing{
	
	protected Game game;
	protected GraphicalWindow graphics;
	
	public Window(Game g) {
		this(g, 0);
	}
	public Window(Game g, int var) {
		game = g;
		graphics = new GraphicalWindow(var);
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
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
				//System.out.println("isMet");
				game.addScore(o.getScore());
				game.getObjectives().remove(i);
				//System.out.println(game.getObjectives().size());
				return null;
			}
		}
		List<Thing> li = new ArrayList<Thing>();
		li.add(t);
		return li;
	}

}
