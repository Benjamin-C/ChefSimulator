package dev.benjaminc.chef_simulator.control;

import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class KeyListen {
	
	protected Map<UUID, KeyListenAction> actions;
	
	public KeyListen() {
		actions = new ConcurrentHashMap<UUID, KeyListenAction>();
	}

	public void keyTyped(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) {
		for (Iterator<KeyListenAction> iterator = actions.values().iterator(); iterator.hasNext();) {
			iterator.next().keyReleaseEvent(e.getKeyCode());
		}
	}
	
	public void keyPressed(KeyEvent e) {
		for (Iterator<KeyListenAction> iterator = actions.values().iterator(); iterator.hasNext();) {
		    iterator.next().keyPressEvent(e.getKeyCode());
		}
	}
	
	public UUID addKeyListen(KeyListenAction a) {
		UUID id = UUID.randomUUID();
		actions.put(id, a);
		return id;
	}
	
	public void removeKeyListen(UUID id) {
		actions.remove(id);
	}
}
