package benjaminc.chef_leveldesigner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import benjaminc.chef_simulator.control.Direction;
import benjaminc.chef_simulator.data.DataMapKey;
import benjaminc.chef_simulator.data.FoodState;

/**
 * @author Benjamin-C
 *
 */
public class ThingAttributeElement {
	
	protected DataMapKey dmk;

	protected JComponent me;
	
	public ThingAttributeElement(DataMapKey dmk) {
		this.dmk = dmk;
		
		switch(dmk) {
		case DIRECTION:
			me = new JComboBox<Direction>(Direction.values());
		break;
		case FOOD_STATE:
			me = new JComboBox<FoodState>(FoodState.values());
		break;
		case INVENTORY:
			me = new JButton("Edit Inv");
		break;
		case MAKES:
			me = new JButton("Edit makes");
		break;
		default:
		break;
		}
		
	}
	
	public JComponent getElementEditor() {
		return me;
	}

}
