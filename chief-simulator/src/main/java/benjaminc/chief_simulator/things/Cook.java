package benjaminc.chief_simulator.things;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.awt.event.KeyEvent;

import benjaminc.chief_simulator.Game;
import benjaminc.chief_simulator.control.KeyListenAction;

public class Cook extends Thing {

	protected int x;
	protected int y;
	protected Game game;
	protected Thing hand;
	
	public Cook(Game g, String name) {
		super(name);
		x = 0;
		y = 0;
		game = g;
		game.registerKeylistener(new KeyListenAction() {
			@Override
			public void keyReleaseEvent(int key) {
				
			}
			
			@Override
			public void keyPressEvent(int key) {
				switch(key) {
				case KeyEvent.VK_LEFT: { move(MovmentDirection.LEFT); }break;
				case KeyEvent.VK_UP: { move(MovmentDirection.UP); }break;
				case KeyEvent.VK_RIGHT: { move(MovmentDirection.RIGHT); }break;
				case KeyEvent.VK_DOWN: { move(MovmentDirection.DOWN); }break;
				case KeyEvent.VK_CONTROL: { pickUp(); } break;
				}
			}
		});
	}
	
	public void pickUp() {
		if(hand == null) {
			List<Thing> whatIsHere = game.getRoom().getSpace(x, y).getThings();
			int loc = whatIsHere.size() - 1;
			do {
				Thing thingHere = whatIsHere.get(loc);
				if (!(thingHere instanceof SolidObject)) {
					hand = thingHere;
					game.getRoom().getSpace(x, y).removeThing(hand);
					System.out.println(thingHere);
				}
			} while(hand == null && loc-- <= 0);
		} else {
			game.getRoom().getSpace(x, y).addThing(hand);
			hand = null;
		}
		game.updateGraphics();
	}
	public void setX(int x) {
		this.x = x;
		game.updateGraphics();
	}
	
	public void setY(int y) {
		this.y = y;
		game.updateGraphics();
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		game.updateGraphics();
	}
	
	public void move(MovmentDirection dir) {
		if(x + dir.getX() >= 0 && x + dir.getX() < game.getRoomWidth() && y + dir.getY() >= 0 && y + dir.getY() < game.getRoomHeight()) {
			x = x + dir.getX();
			y = y + dir.getY();
		}
		game.updateGraphics();
	}
	public void gotoLocation(int xdif, int ydif) {
		x = x + xdif;
		y = y + ydif;
		game.updateGraphics();
	}
	
	public void draw(Graphics g, int w, int h) {
		int drawX = w * x;
		int drawY = h * y;
		g.setColor(Color.BLUE);
		g.fillRect(drawX+1,  drawY+1,  w-2, h-2);
		if(hand != null) {
			hand.draw(g, drawX + w / 4, drawY + w / 4, w / 2, h / 2);
		}
	}
}
