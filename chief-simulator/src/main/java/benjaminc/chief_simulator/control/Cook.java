package benjaminc.chief_simulator.control;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.List;
import java.util.Map;
import java.awt.event.KeyEvent;

import benjaminc.chief_simulator.Game;
import benjaminc.chief_simulator.graphics.ActionType;
import benjaminc.chief_simulator.things.MovmentDirection;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.AttachedThing;
import benjaminc.chief_simulator.things.types.ToolThing;

public class Cook {
	protected int x;
	protected int y;
	protected Game game;
	protected Thing hand;
	protected MovmentDirection direction;
	
	int randomnum = 0;
	protected Map<ActionType,Integer> keys;

	protected String name;
	
	public Cook(Game g, String name, Map<ActionType,Integer> k) {
		this.name = name;
		x = 0;
		y = 0;
		game = g;
		direction = MovmentDirection.DOWN;
		keys = k;
		game.registerKeylistener(new KeyListenAction() {
			@Override
			public void keyReleaseEvent(int key) {
				
			}
			
			@Override
			public void keyPressEvent(int key) {
				if(key==keys.get(ActionType.MOVE_UP)) { move(MovmentDirection.UP); }
				else if (key==keys.get(ActionType.MOVE_DOWN)) { move(MovmentDirection.DOWN); }
				else if (key==keys.get(ActionType.MOVE_LEFT)) { move(MovmentDirection.LEFT); }
				else if (key==keys.get(ActionType.MOVE_RIGHT)) { move(MovmentDirection.RIGHT); }
				else if (key==keys.get(ActionType.PICKUP_ITEM)) { pickUp(); }
				else if (key==keys.get(ActionType.USE_ITEM)) { useItem(); }
			}
		});
	}
	
	public void useItem() {
		int newX = x + direction.getX();
		int newY = y + direction.getY();
		if(newX < game.getRoom().getSize().width && newX >= 0 && newY < game.getRoom().getSize().height && newY >= 0) {
			List<Thing> whatIsHere = game.getRoom().getSpace(newX, newY).getThings();
			ToolThing tool = null;
			int loc = whatIsHere.size() - 1;
			do {
				Thing thingHere = whatIsHere.get(loc);
				if (thingHere instanceof ToolThing) {
					tool = (ToolThing) thingHere;
				}
			} while(tool == null && loc-- >= 0);
			if(tool != null) {
				Thing food = null;
				loc = whatIsHere.size() - 1;
				do {
					Thing thingHere = whatIsHere.get(loc);
					if (!(thingHere instanceof AttachedThing)) {
						food = thingHere;
						game.getRoom().getSpace(newX, newY).removeThing(food);
					}
				} while(food == null && loc-- <= 0);
				Thing tempThing = tool.useTool(food);
				game.getRoom().getSpace(newX, newY).addThing(tempThing);
			}
		}
		game.updateGraphics();
	}
	
	public void pickUp() {
		int newX = x + direction.getX();
		int newY = y + direction.getY();
		if(newX < game.getRoom().getSize().width && newX >= 0 && newY < game.getRoom().getSize().height && newY >= 0) {
			if(hand == null) {
				List<Thing> whatIsHere = game.getRoom().getSpace(newX, newY).getThings();
				int loc = whatIsHere.size() - 1;
				if(whatIsHere.size() > 1) {
					do {
						Thing thingHere = whatIsHere.get(loc);
						if (!(thingHere instanceof AttachedThing)) {
							hand = thingHere;
							game.getRoom().getSpace(newX, newY).removeThing(hand);
						}
					} while(hand == null && loc-- <= 0);
				}
			} else {
				game.getRoom().getSpace(newX, newY).addThing(hand);
				hand = null;
			}
			game.updateGraphics();
		}
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
		direction = dir;
		int newX = x + dir.getX();
		int newY = y + dir.getY();
		if(newX >= 0 && newX < game.getRoom().getSize().width) {
			if(y + dir.getY() >= 0 && newY < game.getRoom().getSize().getHeight()) {
				if(!game.getRoom().getSpace(newX, newY).isSolid()) {
					x = newX;
					y = newY;
				}
			}
		}
		
		game.updateGraphics();
	}
	public void setDirection(MovmentDirection d) {
		direction = d;
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
		g.setColor(Color.CYAN);
		g.setFont(new Font(name, 0, (int) ((w * 0.9) * Toolkit.getDefaultToolkit().getScreenResolution() / 72.0)));
		String printName = name.substring(0, Math.min(name.length(), 1));
		g.drawString(printName,drawX + ((w - g.getFontMetrics().stringWidth(printName)) / 2), (drawY + w - 4));
		int handX = x + direction.getX();
		int handY = y + direction.getY();
		if(handX < game.getRoom().getSize().width && handX >= 0 && handY < game.getRoom().getSize().height && handY >= 0) {
			handX = handX * w;
			handY = handY * h;
			if(hand != null) {
				hand.draw(g, handX + w / 4, handY + w / 4, w / 2, h / 2);
			} else {
				g.setColor(Color.BLUE);
				g.fillRect(handX + (w/4),  handY + (w/4),  w/2, h/2);
			}
			
		}
		
		
	}
}
