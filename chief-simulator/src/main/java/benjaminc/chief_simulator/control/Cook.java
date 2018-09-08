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
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.AttachedThing;
import benjaminc.chief_simulator.things.types.ToolThing;

public class Cook {
	Location loc;
	protected Game game;
	protected Thing hand;
	protected Direction direction;
	
	int randomnum = 0;
	protected Map<ActionType,Integer> keys;

	protected String name;
	public Cook(Game g, String name, Map<ActionType,Integer> k) {
		this(g, name, k, new Location(0, 0));
	}
	public Cook(Game g, String name, Map<ActionType,Integer> k, Location l) {
		this.name = name;
		loc = l;
		game = g;
		direction = Direction.DOWN;
		keys = k;
		game.registerKeylistener(new KeyListenAction() {
			@Override
			public void keyReleaseEvent(int key) {
				
			}
			
			@Override
			public void keyPressEvent(int key) {
				if(key==keys.get(ActionType.MOVE_UP)) { move(Direction.UP); }
				else if (key==keys.get(ActionType.MOVE_DOWN)) { move(Direction.DOWN); }
				else if (key==keys.get(ActionType.MOVE_LEFT)) { move(Direction.LEFT); }
				else if (key==keys.get(ActionType.MOVE_RIGHT)) { move(Direction.RIGHT); }
				else if (key==keys.get(ActionType.PICKUP_ITEM)) { pickUp(); }
				else if (key==keys.get(ActionType.USE_ITEM)) { useItem(); }
			}
		});
	}
	
	public void useItem() {
		Location newloc = loc.clone().add(direction);
		if(newloc.inside(0, game.getRoom().getSize().width, 0, game.getRoom().getSize().height)) {
			List<Thing> whatIsHere = game.getRoom().getSpace(newloc).getThings();
			ToolThing tool = null;
			int loc = 0;
			if(whatIsHere.size() > 0) {
				do {
					Thing thingHere = whatIsHere.get(loc);
					if (thingHere instanceof ToolThing) {
						tool = (ToolThing) thingHere;
					}
				} while(tool == null && ++loc < whatIsHere.size());
				if(tool != null && whatIsHere.size() > 0) {
					Thing food = null;
					loc = 0;
					do {
						Thing thingHere = whatIsHere.get(loc);
						if (!(thingHere instanceof AttachedThing) && (thingHere != tool)) {
							food = thingHere;
							game.getRoom().getSpace(newloc).removeThing(food);
						}
					} while(food == null && ++loc < whatIsHere.size());
					List<Thing> tempThing = tool.useTool(food);
					if(tempThing != null) {
						for(Thing t : tempThing) {
							game.getRoom().getSpace(newloc).addThing(t);
						}
					}
				}
			game.updateGraphics();
			}
		}
	}
	
	public void pickUp() {
		Location newloc = loc.clone().add(direction);
		if(newloc.inside(0, game.getRoom().getSize().width, 0, game.getRoom().getSize().height)) {
			if(hand == null) {
				List<Thing> whatIsHere = game.getRoom().getSpace(newloc).getThings();
				int loc = whatIsHere.size() - 1;
				if(whatIsHere.size() > 1) {
					do {
						Thing thingHere = whatIsHere.get(loc);
						if (!(thingHere instanceof AttachedThing)) {
							hand = thingHere;
							game.getRoom().getSpace(newloc).removeThing(hand);
						}
					} while(hand == null && loc-- <= 0);
				}
			} else {
				game.getRoom().getSpace(newloc).addThing(hand);
				hand = null;
			}
			game.updateGraphics();
		}
	}
	public void setLocation(Location l) {
		loc = l;
		game.updateGraphics();
	}
	
	public void move(Direction dir) {
		direction = dir;
		Location newloc = loc.clone().add(dir);
		if(newloc.inside(0, game.getRoom().getSize().width, 0, game.getRoom().getSize().height)) {
			if(!game.getRoom().getSpace(newloc).isSolid()) {
				loc = newloc;
			}
		}
		game.updateGraphics();
	}
	public void setDirection(Direction d) {
		direction = d;
	}
	public void addLocation(Location l) {
		loc.add(l);
		game.updateGraphics();
	}
	
	public void draw(Graphics g, int xos, int yos, int w, int h) {
		int x = loc.getX();
		int y = loc.getY();
		int drawX = (w * x) + xos;
		int drawY = (h * y) + yos;
		g.setColor(new Color(240, 240, 240));
		g.fillOval(drawX+1,  drawY+1,  w-2, h-2);
		g.setColor(new Color(255, 128, 0));
		g.setFont(new Font(name, 0, (int) ((w * 0.65) * Toolkit.getDefaultToolkit().getScreenResolution() / 72.0)));
		String printName = name.substring(0, Math.min(name.length(), 1));
		g.drawString(printName,drawX + ((w - g.getFontMetrics().stringWidth(printName)) / 2), (drawY + w - 4));
		int handX = x + direction.getX();
		int handY = y + direction.getY();
		if(handX < game.getRoom().getSize().width && handX >= 0 && handY < game.getRoom().getSize().height && handY >= 0) {
			handX = (handX * w) + xos;
			handY = (handY * h) + yos;
			if(hand != null) {
				hand.draw(g, handX + w / 4, handY + w / 4, w / 2, h / 2);
			} else {
				g.setColor(new Color(255, 128, 0));
				for(int i = 0; i < Math.max(w/16, 1); i++) {
					g.drawRect(handX + i,  handY + i,  w - (2*i), h - (2*i));
				}
			}
			
		}
		
		
	}
	
	@Override
	public String toString() {
		return "Cook[name=" + name + ",loc= " + loc.toString() + ",d=" + direction + ",h=" + hand + "]";
	}
}
