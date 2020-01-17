package benjaminc.chef_simulator.things.building;

import java.util.List;
import java.util.UUID;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.control.Direction;
import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.DataMap.DataMapKey;
import benjaminc.chef_simulator.rooms.Room;
import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.AttachedThing;
import benjaminc.chef_simulator.things.types.Tickable;
import benjaminc.chef_simulator.things.types.ToolThing;

/**
 * @author 705822
 *
 */
public class Button extends BasicThing implements ToolThing, AttachedThing, Tickable {

	protected final static int VARIANT_COUNT = 1;
	public Button() {
		this(null, (UUID) null);
	}
	public Button(DataMap dataMap, UUID uuid) {
		super(dataMap, Button.class, uuid);
	}
	public Button(Direction dir) {
		this(null, dir);
	}
	public Button(DataMap dm, Direction dir) {
		super(dm, VARIANT_COUNT, Button.class);
		if(dir != null) {
			dataMap.put(DataMapKey.DIRECTION, dir);
		} else {
			dataMap.put(DataMapKey.DIRECTION, Direction.UP);
		}
	}
	
	@Override
	public List<Thing> useTool(Thing t) {
		click();
		System.out.println("Button pressed");
		return null;
	}
	
	public void click() {
		Game.getGamePanel().getChatBox().addElement("MSG that takes space\nmsg This is a long message that should be split into lines because it length is longer thatn that of the box that it lives in so I need to test if it can be split in the correct ways to appear nicely but not be too wide", 120);
//		Game.chat("You pressed the button! Good job\nAnd this is more text");
		Game.getGamePanel().getChatBox().addElement("I want to test a message", 120);
	}
	
	@Override
	public boolean isSame(Thing t) {
		if(t.getClass() == this.getClass()) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public DataMap getDataMap() {
		return dataMap;
	}
	@Override
	public void tick(Room r, Location l, double f) {
		// TODO Auto-generated method stub
		
	}
}
