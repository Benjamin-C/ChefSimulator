package dev.benjaminc.chef_leveldesigner;

import java.util.List;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import dev.benjaminc.chef_leveldesigner.EditableList.EditableListEvents;
import dev.benjaminc.chef_leveldesigner.EditableListEditDialog.EditableListEditDialogDrawEvent;
import dev.benjaminc.chef_leveldesigner.ThingEditDialog.ThingTypeChangeEvent;
import dev.benjaminc.chef_simulator.control.Direction;
import dev.benjaminc.chef_simulator.data.DataMap;
import dev.benjaminc.chef_simulator.data.FoodState;
import dev.benjaminc.chef_simulator.data.Inventory;
import dev.benjaminc.chef_simulator.data.DataMap.DataMapKey;
import dev.benjaminc.chef_simulator.graphics.GraphicalDrawer;
import dev.benjaminc.chef_simulator.things.Thing;
import dev.benjaminc.chef_simulator.things.food.Potato;

/**
 * @author Benjamin-C
 *
 */
public class ThingAttributeElementEditor extends JPanel {
	
	/** */ private static final long serialVersionUID = -7201188114121767045L;

	/** the {@link DataMapKey} being edited */
	protected DataMapKey dmk;

	/** the {@link JComponent} this is */
	protected JComponent me;
	
	/** the {@link Runnable} for updates */
	protected Runnable onUpdate;
	
	/**
	 * Checks if a specific {@link DataMapKey} can make a {@link ThingAttributeElementEditor}
	 * @param dmk the {@link DataMapKey} to test
	 * @return boolean if it can make an editor
	 */
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
	
	/**
	 * @param d the {@link DataMap} to edit
	 * @param dmk the {@link DataMapKey} to edit in the {@link DataMap}
	 * @param onUpdateRunnable the {@link Runnable} to run when done
	 */
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
					new EditableListEditDialog<Thing>(inv, new EditableListEvents<Thing>() {
						@Override public Thing makeNew() { return new Potato(); }
						@Override public void edit(Thing t, EditableListElement<Thing> te, EditableList<Thing> tl, EditableListEvents<Thing> onUpdate) {
							new ThingEditDialog(t, "", new ThingTypeChangeEvent() {
								@Override public void onChange(Thing newThing) { tl.replace(t, newThing); te.set(newThing); } },
								new Runnable() { @Override public void run() { onUpdate.onUpdate(); }});
						}
						@Override public void onUpdate() { onUpdateRunnable.run(); }
					}, new EditableListEditDialogDrawEvent<Thing>() {
						@Override public void draw(Graphics g, Thing t, int x, int y, int w, int h) {
							new GraphicalDrawer(g).draw(t, x, y, w, h);
						}
					});
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
