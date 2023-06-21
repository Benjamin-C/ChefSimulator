package dev.orangeben.chef_simulator;

import java.awt.Graphics;
import java.util.Map;

import dev.orangeben.chef_simulator.chef_graphics.GraphicalDrawer;
import dev.orangeben.chef_simulator.data.Savable;
import dev.orangeben.chef_simulator.things.BasicThing;
import dev.orangeben.chef_simulator.things.Thing;
import dev.orangeben.util.JSONTools;

public class Objective implements Savable, Cloneable {
	
	// fixed?
	int bobwillbuildit = 7;
	
	/** the {@link Thing} to be made */
	protected Thing target;
	/** the int score */
	protected int points;
	
	/**
	 * Creates an {@link Objective}
	 * @param tgt the {@link Thing} to be made
	 * @param pts the int point value
	 */
	public Objective(Thing tgt, int pts) {
		target = tgt;
		points = pts;
	}
	
	/**
	 *  Creates an {@link Objective} from a JSON {@link String}
	 *  @param json the JSON {@link String}
	 */
	public Objective(String json) {
		if(json != null && json.length() > 0) {
			Map<String, String> j = JSONTools.splitJSON(JSONTools.peelChar(json, '{'));
			target = BasicThing.makeThingFromJSON(j.get(ObjectiveDataKey.TARGET.toString()));
			points = Integer.parseInt(j.get(ObjectiveDataKey.POINTS.toString()));
		} else {
			System.out.println("ERROR can not make Objective from an empty/null string");
		}
	}
	
	@Override
	public String asJSON() {
		return "{\"" + ObjectiveDataKey.TARGET + "\":" + target.asJSON() + ", \"" + ObjectiveDataKey.POINTS + "\":\"" + points + "\"}";
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
	 * Gets the score of the item
	 * @return the int score
	 */
	public int getScore() {
		return points;
	}
	
	/**
	 * Sets the score of the {@link Objective}
	 * @param score the int new score
	 */
	public void setScore(int score) { 
		points = score;
	}
	/**
	 * Draws the Objective
	 * @param g the {@link Graphics} to draw on
	 * @param x the int x ofset
	 * @param y the int y ofset
	 * @param w the int width
	 * @param h the int height
	 */
	public void draw(Graphics g, int x, int y, int w, int h) {
		GraphicalDrawer gd = new GraphicalDrawer(g);
		gd.draw(target, x, y, w, h);
	}
	
	/**
	 * Gets the {@link Thing} target of the {@link Objective}
	 * @return the {@link Thing} target
	 */
	public Thing getTarget() {
		return target;
	}
	
	/**
	 * Sests the {@link Thing} target for the {@link Objective}
	 * @param newTarget the {@link Thing} to target
	 */
	public void setTarget(Thing newTarget) {
		target = newTarget;
	}
	
	@Override
	public Objective clone() {
		return new Objective(target.clone(), points);
	}
	
	/**
	 * Data Keys for storing JSON data about the {@link Objective}
	 * @author Benjamin-C
	 *
	 */
	public enum ObjectiveDataKey {
		/** the target {@link Thing} for the objective; */
		TARGET,
		/** the int score for the objective */
		POINTS
		}
}
