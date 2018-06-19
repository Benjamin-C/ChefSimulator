package benjaminc.chief_simulator;

import java.awt.Graphics;

import benjaminc.chief_simulator.things.Thing;

public class Objective {

	protected Thing target;
	protected int points;
	
	public Objective(Thing tgt, int pts) {
		target = tgt;
		points = pts;
	}
	
	public boolean isMet(Thing t) {
		if(target.isSame(t)) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getScore() {
		return points;
	}
	public void draw(Graphics g, int x, int y, int w, int h) {
		target.draw(g, x, y, w, h);
	}
}
