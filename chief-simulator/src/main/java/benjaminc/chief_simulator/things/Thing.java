package benjaminc.chief_simulator.things;

import java.awt.Graphics;

public class Thing {

	protected String name;
	
	public Thing() {
		
	}
	
	public Thing(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void draw(Graphics g, int x, int y, int w, int h) {
	}
}
