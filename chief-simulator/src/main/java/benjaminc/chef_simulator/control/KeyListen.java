package benjaminc.chef_simulator.control;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.graphics.GamePanel;

public class KeyListen {
	
	protected List<KeyListenAction> actions;
	protected Game g;
	protected GamePanel gp;
	
	public KeyListen(Game g, GamePanel gp) {
		actions = new ArrayList<KeyListenAction>();
		this.g = g;
		this.gp = gp;
	}

	public void keyTyped(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) { for(KeyListenAction a : actions) { a.keyReleaseEvent(e.getKeyCode());} }
	
	public void keyPressed(KeyEvent e) {
		for(KeyListenAction a : actions) { a.keyPressEvent(e.getKeyCode());}
		switch(e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE: { System.exit(1); } break;
		case KeyEvent.VK_F3: { gp.enableLagometer(!gp.getLagometerEnabled()); } break;
		}
	}
	
	public void addKeyListen(KeyListenAction a) {
		actions.add(a);
	}
}
