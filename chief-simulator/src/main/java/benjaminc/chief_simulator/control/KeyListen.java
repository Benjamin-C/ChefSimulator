package benjaminc.chief_simulator.control;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class KeyListen {
	
	protected List<KeyListenAction> actions;
	
	public KeyListen() {
		actions = new ArrayList<KeyListenAction>();
	}

	public void keyTyped(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) { for(KeyListenAction a : actions) { a.keyReleaseEvent(e.getKeyCode());} }
	
	public void keyPressed(KeyEvent e) {
		for(KeyListenAction a : actions) { a.keyPressEvent(e.getKeyCode());}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(1);
		}
	}
	
	public void addKeyListen(KeyListenAction a) {
		actions.add(a);
	}
}
