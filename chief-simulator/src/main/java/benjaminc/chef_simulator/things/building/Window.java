package benjaminc.chef_simulator.things.building;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.Objective;
import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.FoodState;
import benjaminc.chef_simulator.graphics.GameSpace;
import benjaminc.chef_simulator.rooms.Room;
import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.tools.Plate;
import benjaminc.chef_simulator.things.types.NeedsInitThing;
import benjaminc.chef_simulator.things.types.SolidThing;
import benjaminc.chef_simulator.things.types.ToolThing;
import benjaminc.util.Util;

public class Window extends BasicThing implements ToolThing, Thing, SolidThing, NeedsInitThing {
	
	protected Room room;
	protected final static int VARIANT_COUNT = 1;

	public Window() {
		this(null);
	}
	public Window(DataMap dataMap) {
		super(dataMap, Window.class);
	}
	
	@Override
	public void init(Game g) {
		room = g.getRoom();
	}
	
//	@Override
//	public void draw(Graphics g, int x, int y, int w, int h) {
//		graphics.draw(g, x, y, w, h);
//	}

	@Override
	public boolean isSame(Thing t) {
		if(t.getClass() == this.getClass()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Thing> useTool(Thing t) {
		List<Thing> li = new ArrayList<Thing>();
		System.out.println("Is objective complete?");
		for(int i = 0; i < room.getObjectives().size(); i++) {
			Objective o = room.getObjectives().get(i);
			if(o.isMet(t)) {
				System.out.println("isMet");
				room.getScore().addScore(o.getScore());
				room.getObjectives().remove(i);
				if(room.getObjectives().size() == 0) {
					Util.resume(room.getSyncObj());
				}
				if(t instanceof Plate) {
					List<GameSpace> returnlist = new ArrayList<GameSpace>();
					for(int x = 0; x < room.getWidth(); x++) {
						for(int y = 0; y < room.getHeight(); y++) {
							Location here = new Location(x, y);
							if(room.getSpace(here).contains(DishReturn.class)) {
							 returnlist.add(room.getSpace(here));
							}
						}
					}
				
					if(returnlist.size() < 1) {
						li.add(new Plate(FoodState.COOKED));
					} else {
						returnlist.get(new Random().nextInt(returnlist.size())).addThing(new Plate(FoodState.COOKED));
					}
				}
				//System.out.println(game.getObjectives().size());
				return null;
			}
		}
		
		li.add(t);
		return li;
	}
	@Override
	public DataMap getDataMap() {
		return dataMap;
	}
}
