package benjaminc.chief_simulator.things.building;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import benjaminc.chief_simulator.graphics.building.GraphicalDisposal;
import benjaminc.chief_simulator.graphics.food.GraphicalApple;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.SolidThing;
import benjaminc.chief_simulator.things.types.ToolThing;

public class Disposal implements SolidThing, ToolThing{

	protected GraphicalDisposal graphics;
	
	public Disposal() {
		this(0);
	}
	public Disposal(int var) {
		graphics = new GraphicalDisposal(var);
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		graphics.draw(g, x, y, w, h);
	}

	@Override
	public Thing duplicate() {
		return new Disposal();
	}

	@Override
	public List<Thing> useTool(Thing t) {
		return null;
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
