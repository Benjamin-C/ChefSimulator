package benjaminc.chef_leveldesigner;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import benjaminc.chef_simulator.control.Direction;
import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.DataMapKey;
import benjaminc.chef_simulator.data.FoodState;
import benjaminc.chef_simulator.data.Inventory;
import benjaminc.chef_simulator.things.Thing;

/**
 * @author Benjamin-C
 *
 */
public class ThingAttributeElementEditor extends JPanel {
	
	/** */ private static final long serialVersionUID = -7201188114121767045L;

	protected DataMapKey dmk;

	protected JComponent me;
	
	protected Runnable onUpdate;
	
	public static boolean willMakeEditor(DataMapKey dmk) {
		switch(dmk) {
		case DIRECTION:
		case FOOD_STATE:
		case INVENTORY:
		case MAKES:
			return true;
			
		default:
			return false;
		}
	}
	
	public ThingAttributeElementEditor(DataMap d, DataMapKey dmk, Runnable onUpdateRunnable) {
		this.dmk = dmk;
		
		onUpdate = onUpdateRunnable;
		
		switch(dmk) {
		case DIRECTION: {
			JComboBox<Direction> mecb = new JComboBox<Direction>(Direction.values());
			mecb.setSelectedItem(d.get(dmk));
			mecb.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent arg0) { d.put(dmk, (Direction) mecb.getSelectedItem());  onUpdate.run(); }});
			me = mecb;
		} break;
		case FOOD_STATE: {
			JComboBox<FoodState> mecb = new JComboBox<FoodState>(FoodState.values());
			mecb.setSelectedItem(d.get(dmk));
			mecb.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent arg0) { d.put(dmk, (FoodState) mecb.getSelectedItem()); onUpdate.run(); }});
			me = mecb;
		} break;
		case INVENTORY: {
			JButton mejb = new JButton("Edit Inv");
			mejb.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent arg0) {
					System.out.println("Edit Inv button pressed");
					List<Thing> inv = new ArrayList<Thing>();
					if(d.containsKey(DataMapKey.INVENTORY) && d.get(DataMapKey.INVENTORY) != null) {
						inv = ((Inventory) d.get(DataMapKey.INVENTORY)).getAll();
						System.out.println("Found inv " + inv.size());
					} else {
						d.put(DataMapKey.INVENTORY, new Inventory(inv));
						System.out.println("Didn't find inv, so I set it");
					}
					new ThingListEditDialog(inv, onUpdate);
				}
			});
			me = mejb;
		} break;
		case MAKES: {
			JButton mejb = new JButton("Makes ");
			mejb.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent arg0) {
					new ThingEditDialog((Thing) d.get(DataMapKey.MAKES), "", new ThingTypeChangeEvent() {
						@Override public void onChange(Thing newThing) { d.put(DataMapKey.MAKES, newThing); }
					}, onUpdate);
				}
			});
			me = mejb;
		} break;
		default:
		break;
		}
		
		add(me);
		
	}
}
