package benjaminc.chef_simulator.things.building;

import java.util.List;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.control.Direction;
import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.DataMap.DataMapKey;
import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.types.AttachedThing;
import benjaminc.chef_simulator.things.types.ToolThing;

/**
 * @author 705822
 *
 */
public class Button extends BasicThing implements ToolThing, AttachedThing {

	protected final static int VARIANT_COUNT = 1;
	public Button() {
		this(null, null);
	}
	public Button(DataMap dataMap) {
		this(dataMap, null);
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
		Game.gamePanel.getChatBox().addElement("You pressed the button! Good job", 60*4);
		System.out.println("Button pressed");
		return null;
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
}
