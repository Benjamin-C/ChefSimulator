package benjaminc.chef_simulator;

import java.awt.Graphics;

import benjaminc.chef_simulator.graphics.GraphicalDrawer;
import benjaminc.chef_simulator.things.Thing;

public class Objective {

	protected Thing target;
	protected int points;
	
	public Objective(Thing tgt, int pts) {
		target = tgt;
		points = pts;
	}
	
	/**
	 * Checks if the objective has been met
	 * @param t the Thing to test if it is correct
	 * @return the boolean of if it is met
	 */
	public boolean isMet(Thing t) {
		System.out.println("isMet?");
		if(target.isSame(t)) {
			System.out.println("Yes");
			return true;
		} else {
			System.out.println("No");
			return false;
		}
	}
	
	/**
	 * Gets thhe score of the item
	 * @return the int score
	 */
	public int getScore() {
		return points;
	}
	
	public void draw(Graphics g, int x, int y, int w, int h) {
		GraphicalDrawer gd = new GraphicalDrawer(g);
		gd.draw(target, x, y, w, h);
	}
}
