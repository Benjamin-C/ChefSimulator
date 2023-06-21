package dev.orangeben.chef_simulator.control;

import dev.orangeben.chef_simulator.chef_graphics.ActionType;

public class Action {

	private ActionType type;
	private int key;
	private Double lastUse;
	private boolean used;
	private boolean doOnce;
	private boolean pressed;
	public Action(ActionType type, int key, Double lastUse, boolean pressed, boolean used, boolean doOnce) {
		super();
		this.type = type;
		this.key = key;
		this.lastUse = lastUse;
		this.pressed = pressed;
		this.used = used;
		this.doOnce = doOnce;
	}
	public ActionType getType() {
		return type;
	}
	public void setType(ActionType type) {
		this.type = type;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) { 
		this.key = key;
	}
	public Double getLastUse() {
		return lastUse;
	}
	public void setLastUse(Double lastUse) {
		this.lastUse = lastUse;
	}
	public boolean isPressed() {
		return pressed;
	}
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
	public boolean isDoOnce() {
		return doOnce;
	}
	public void setDoOnce(boolean doOnce) {
		this.doOnce = doOnce;
	}
	
}
