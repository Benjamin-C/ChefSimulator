package benjaminc.chef_simulator.things.building;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import benjaminc.chef_simulator.Objective;
import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.FoodState;
import benjaminc.chef_simulator.graphics.GameSpace;
import benjaminc.chef_simulator.rooms.Room;
import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.tools.Plate;
import benjaminc.chef_simulator.things.types.SolidThing;
import benjaminc.chef_simulator.things.types.ToolThing;
import benjaminc.util.Util;

public class Window extends BasicThing implements ToolThing, Thing, SolidThing {
	
	protected Room room;
	protected final static int VARIANT_COUNT = 1;
	public Window(Room r) {
		this(r, null);
	}
	public Window(Room r, DataMap dataMap) {
		super(dataMap, VARIANT_COUNT, Window.class);
		room = r;
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
				List<GameSpace> returnlist = new ArrayList<GameSpace>();
				for(int x = 0; x < room.getWidth(); x++) {
					for(int y = 0; y < room.getHeight(); y++) {
						Location here = new Location(x, y);
						if(room.getSpace(here).contains(DishReturn.class)) {
						 returnlist.add(room.getSpace(here));
						}
					}
				}
				returnlist.get(new Random().nextInt(returnlist.size())).addThing(new Plate(FoodState.COOKED));
				//System.out.println(game.getObjectives().size());
				return null;
			}
		}
		List<Thing> li = new ArrayList<Thing>();
		li.add(t);
		return li;
	}
	@Override
	public DataMap getDataMap() {
		return dataMap;
	}
}
