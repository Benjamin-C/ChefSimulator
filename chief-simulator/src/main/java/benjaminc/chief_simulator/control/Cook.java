package benjaminc.chief_simulator.control;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import benjaminc.chief_simulator.Game;
import benjaminc.chief_simulator.graphics.ActionType;
import benjaminc.chief_simulator.graphics.Room;
import benjaminc.chief_simulator.things.Thing;
import benjaminc.chief_simulator.things.types.AttachedThing;
import benjaminc.chief_simulator.things.types.Tickable;
import benjaminc.chief_simulator.things.types.ToolThing;

public class Cook implements Tickable {
	protected Location loc;
	protected Game game;
	protected Thing hand;
	protected Direction direction;
	protected Color color;
	protected int randomnum = 0;
	protected Map<ActionType,Integer> keys;
	protected Map<ActionType,Action> keyActions;
	protected double movesPerSecond = 6.5d;
	protected long movesDel;

	protected String name;
	public Cook(Game g, String name, Color color, Map<ActionType,Integer> k) {
		this(g, name, color, k, new Location(0, 0));
	}
	public Cook(Game g, String name, Color color, Map<ActionType,Integer> k, Location l) {
		this.name = name;
		this.color = color;
		loc = l;
		game = g;
		direction = Direction.DOWN;
		keys = k;
		keyActions = new HashMap<ActionType, Action>();
		for(Map.Entry<ActionType, Integer> e : keys.entrySet()) {
			keyActions.put(e.getKey(), new Action(e.getKey(), e.getValue(), 0L, false, false, false));
		}
		movesDel = Math.round(((double) game.getTps()) / movesPerSecond);
		System.out.println(movesDel);
		game.registerKeylistener(new KeyListenAction() {
			@Override
			public void keyReleaseEvent(int key) {
				for(Map.Entry<ActionType, Action> e : keyActions.entrySet()) {
					Action a = e.getValue();
					if(a.getKey() == key) {
						a.setPressed(false);
						if(!a.isUsed()) {
							a.setDoOnce(true);
						}
						a.setUsed(false);
						break;
					}
				}
			}
			
			@Override
			public void keyPressEvent(int key) {
				for(Map.Entry<ActionType, Action> e : keyActions.entrySet()) {
					if(e.getValue().getKey() == key) {
						e.getValue().setPressed(true);
						break;
					}
				}
			}
		});
	}
	
	private boolean tryTick(Action a, long now, long del) {
		if(a.getLastUse() <= (now - del) || a.isDoOnce()) {
			a.setLastUse(now);
			if(a.isDoOnce()) {
				a.setDoOnce(false);
			} else {
				a.setUsed(true);
			}
			return true;
		}
		return false;
	}
	@Override
	public void tick(Room r, Location l, long f, Game g) {
		for(Map.Entry<ActionType, Action> e : keyActions.entrySet()) {
			Action a = e.getValue();
			if(a.isPressed() || a.isDoOnce()) {
				switch(e.getKey()) {
				case MOVE_DOWN: if(tryTick(e.getValue(), f, movesDel)) { move(Direction.DOWN); } break;
				case MOVE_LEFT: if(tryTick(e.getValue(), f, movesDel)) { move(Direction.LEFT); } break;
				case MOVE_RIGHT: if(tryTick(e.getValue(), f, movesDel)) { move(Direction.RIGHT); } break;
				case MOVE_UP: if(tryTick(e.getValue(), f, movesDel)) { move(Direction.UP); } break;
				case PICKUP_ITEM: if(tryTick(e.getValue(), f, movesDel)) { pickUp(); } break;
				case USE_ITEM: if(tryTick(e.getValue(), f, movesDel)) { useItem(); } break;
				default: System.out.println("Cook tick reached default state. Something messed up"); break;
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void doAction(int key) {
		if(key==keys.get(ActionType.MOVE_UP)) { move(Direction.UP); }
		else if (key==keys.get(ActionType.MOVE_DOWN)) { move(Direction.DOWN); }
		else if (key==keys.get(ActionType.MOVE_LEFT)) { move(Direction.LEFT); }
		else if (key==keys.get(ActionType.MOVE_RIGHT)) { move(Direction.RIGHT); }
		else if (key==keys.get(ActionType.PICKUP_ITEM)) { pickUp(); }
		else if (key==keys.get(ActionType.USE_ITEM)) { useItem(); }
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
	}
	public void setDirection(Direction d) {
		direction = d;
	}
	public void addLocation(Location l) {
		loc.add(l);
	}
	public Location getLocation() {
		return loc;
	}
	public void draw(Graphics g, int xos, int yos, int w, int h) {
		int x = loc.getX();
		int y = loc.getY();
		int drawX = (w * x) + xos;
		int drawY = (h * y) + yos;
		g.setColor(new Color(240, 240, 240));
		g.fillOval(drawX+1,  drawY+1,  w-2, h-2);
		g.setColor(color);
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
				g.setColor(color);
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
