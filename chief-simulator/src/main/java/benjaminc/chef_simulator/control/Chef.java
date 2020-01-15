package benjaminc.chef_simulator.control;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.data.DataMap.DataMapKey;
import benjaminc.chef_simulator.events.ChefInteractEvent;
import benjaminc.chef_simulator.events.ChefInteractEvent.ChefInteractEventTypes;
import benjaminc.chef_simulator.events.ChefMoveEvent;
import benjaminc.chef_simulator.events.ThingAddEvent;
import benjaminc.chef_simulator.events.ThingChangeEvent;
import benjaminc.chef_simulator.events.ThingRemoveEvent;
import benjaminc.chef_simulator.graphics.ActionType;
import benjaminc.chef_simulator.graphics.GraphicalDrawer;
import benjaminc.chef_simulator.rooms.Room;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.AttachedThing;
import benjaminc.chef_simulator.things.types.Tickable;
import benjaminc.chef_simulator.things.types.ToolThing;

public class Chef implements Tickable {
	
	protected Location loc;
	protected Thing hand;
	protected Direction direction;
	protected Color color;
	protected int randomnum = 0;
	protected Map<ActionType,Integer> keys;
	protected Map<ActionType,Action> keyActions;
	protected double movesPerSecond = 6.5d;
	protected long movesDel;

	protected String name;
	
	/**
	 * Makes a new {@link Chef}.
	 * @param name	the {@link String} name of the {@link Chef}. Must be unique
	 * @param color	the {@link Color} of the cook
	 * @param k		the {@link Map} of {@link ActionType} to {@link Integer} of key actions
	 */
	public Chef(String name, Color color, Map<ActionType,Integer> k) {
		this(name, color, k, new Location(0, 0));
	}
	/**
	 * Makes a new {@link Chef}.
	 * @param name	the {@link String} name of the {@link Chef}. Must be unique
	 * @param color	the {@link Color} of the cook
	 * @param k		the {@link Map} of {@link ActionType} to {@link Integer} of key actions
	 * @param l		the {@link Location} of the cook
	 */
	public Chef(String name, Color color, Map<ActionType,Integer> k, Location l) {
		this.name = name;
		this.color = color;
		loc = l;
		direction = Direction.DOWN;
		keys = k;
		keyActions = new HashMap<ActionType, Action>();
		for(Map.Entry<ActionType, Integer> e : keys.entrySet()) {
			keyActions.put(e.getKey(), new Action(e.getKey(), e.getValue(), 0d, false, false, false));
		}
		movesDel = Math.round((1000d) / movesPerSecond);
		System.out.println(movesDel);
		Game.registerKeylistener(new KeyListenAction() {
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
	
	/**
	 * Tries to tick the cook
	 * @param a		the {@link Action} to try
	 * @param now	the double current frame
	 * @param del	the long delay
	 * @return		boolean of success
	 */
	private boolean tryTick(Action a, double now, long del) {
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
	public void tick(Room r, Location l, double f) {
		for(Map.Entry<ActionType, Action> e : keyActions.entrySet()) {
			Action a = e.getValue();
			f = System.currentTimeMillis();
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
	
	/**
	 * Uses the item in the {@link Chef} hand
	 */
	public void useItem() {
		Location newloc = loc.clone().add(direction);
		if(newloc.inside(0, Game.getRoom().getSize().width, 0, Game.getRoom().getSize().height)) {
			List<Thing> whatIsHere = Game.getRoom().getSpace(newloc).getThings();
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
						}
					} while(food == null && ++loc < whatIsHere.size());
					List<Thing> tempThing = tool.useTool(food);
					if(tempThing != null) {
						boolean changedFood = false;
						for(Thing t : tempThing) {
							if(t.getDataMap().get(DataMapKey.UUID).equals(food.getDataMap().get(DataMapKey.UUID))) {
								EventHandler.fireEvent(
										new ThingChangeEvent((UUID) food.getDataMap().get(DataMapKey.UUID), t.getDataMap(), newloc.as3d(0)));
							}
							if(t != null) {
								EventHandler.fireEvent(new ThingAddEvent(t, newloc.as3d(0)));
							}
//							Game.getRoom().getSpace(newloc).addThing(t); Replaced by above event
						}
						EventHandler.fireEvent(new ThingRemoveEvent(food, newloc.as3d(0)));
						
					}
				}
			}
		}
	}
	
	/**
	 * Picks up an item at the {@link Chef} location
	 */
	public void pickUp() {
		Location newloc = loc.clone().add(direction);
		if(newloc.inside(0, Game.getRoom().getSize().width, 0, Game.getRoom().getSize().height)) {
			if(hand == null) {
				List<Thing> whatIsHere = Game.getRoom().getSpace(newloc).getThings();
				int loc = whatIsHere.size() - 1;
				if(whatIsHere.size() > 1) {
					do {
						Thing thingHere = whatIsHere.get(loc);
						if (!(thingHere instanceof AttachedThing)) {
							EventHandler.fireEvent(new ChefInteractEvent(ChefInteractEventTypes.PICK_UP, name, thingHere));
						}
					} while(hand == null && loc-- <= 0);
				}
			} else {
				EventHandler.fireEvent(new ChefInteractEvent(ChefInteractEventTypes.PUT_DOWN, name, hand));
			}
		}
	}
	/**
	 * Sets the {@link Location} of the {@link Chef}
	 * @param l	the new {@link Location}
	 */
	public void setLocation(Location l) {
		loc = l;
	}
	/**
	 * Moves the {@link Chef} 1 step in a direction
	 * @param dir the {@link Direction} to move
	 */
	public void move(Direction dir) {
		Direction startDir = direction;
		Location startLoc = loc;
		direction = dir;
		Location newloc = loc.clone().add(dir);
		if(newloc.inside(0, Game.getRoom().getSize().width, 0, Game.getRoom().getSize().height)) {
			if(!Game.getRoom().getSpace(newloc).isSolid()) {
				loc = newloc;
			}
		}
		EventHandler.fireEvent(new ChefMoveEvent(startDir, direction, startLoc, loc, this));
	}
	/**
	 * Gets the {@link Direction} the cook is facing
	 * @return the {@link Direction}
	 */
	public Direction getDirection() {
		return direction;
	}
	/**
	 * Sets the {@link Direction} the cook is facing
	 * @param d	the new {@link Direction}
	 */
	public void setDirection(Direction d) {
		direction = d;
	}
	/**
	 * Adds a {@link Location} to the cook's location and moves there
	 * @param l	the {@link Location} to add
	 */
	public void addLocation(Location l) {
		loc.add(l);
	}
	/**
	 * Gets the {@link Chef} location
	 * @return	the {@link Location}
	 */
	public Location getLocation() {
		return loc;
	}
	/**
	 * Draws the {@link Chef} on the screen
	 * @param g		the {@link Graphics} to draw on
	 * @param xos	the int x ofset to draw at
	 * @param yos	the int y ofset to draw at
	 * @param w		the int width to draw
	 * @param h		the int height to draw
	 */
	public void draw(Graphics g, int xos, int yos, int w, int h) {
		GraphicalDrawer gd = new GraphicalDrawer(g);
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
		if(handX < Game.getRoom().getSize().width && handX >= 0 && handY < Game.getRoom().getSize().height && handY >= 0) {
			handX = (handX * w) + xos;
			handY = (handY * h) + yos;
			if(hand != null) {
				gd.draw(hand, handX + w / 4, handY + w / 4, w / 2, h / 2);
			} else {
				g.setColor(color);
				for(int i = 0; i < Math.max(w/16, 1); i++) {
					g.drawRect(handX + i,  handY + i,  w - (2*i), h - (2*i));
				}
			}
			
		}
		
		
	}
	/**
	 * Gets the {@link Thing} in the {@link Chef} hand.
	 * @return the {@link Thing}
	 */
	public Thing getHand() {
		return hand;
	}
	/**
	 * Sets the {@link Thing} in the {@link Chef} hand.
	 * @param hand the new {@link Thing}
	 */
	public void setHand(Thing hand) {
		this.hand = hand;
	}
	/**
	 * Gets the name of the {@link Chef}
	 * @return the {@link String} name
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Cook[name=" + name + ",loc= " + loc.toString() + ",d=" + direction + ",h=" + hand + "]";
	}
}
