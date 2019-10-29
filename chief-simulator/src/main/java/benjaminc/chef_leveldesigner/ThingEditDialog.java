package benjaminc.chef_leveldesigner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.DataMapKey;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.ThingType;
import benjaminc.chef_simulator.things.food.Potato;

/**
 * @author Benjamin-C
 *
 */
public class ThingEditDialog {
	
	protected Thing t;
	protected ThingTypeChangeEvent onThingTypeChange;
	
	/**
	 * Makes a new {@link ThingEditDialog}
	 * @param th the {@link Thing} to edit
	 * @param onThingTypeChange the {@link Runnable} to run when the ThingType changes
	 * @param onUpdate the {@link Runnable} to run when anything changes. Runs after others
	 */
	public ThingEditDialog(Thing th, String extra, ThingTypeChangeEvent onThingTypeChange, Runnable onUpdateRunnable) {
		if(th == null) {
			t = new Potato();
			onThingTypeChange.onChange(t);
			onUpdateRunnable.run();
		} else {
			t = th;
		}
		
		this.onThingTypeChange = onThingTypeChange;
		
		System.out.println("This is a ThingEditDialog");
		JDialog jdl = new JDialog();
		setTitle(jdl, t, extra);
		
		Runnable onUpdate = new Runnable() {
			@Override
			public void run() {
				onUpdateRunnable.run();
				setTitle(jdl, t, extra);
			}
		};
		
		DataMap d = t.getDataMap();
		
		JPanel jp = new JPanel();
		
		JComboBox<ThingType>typecombobox = new JComboBox<ThingType>(ThingType.values());
		typecombobox.setSelectedItem(ThingType.getTypeOfThing(t));
		jp.add(typecombobox);
		typecombobox.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				t = ThingType.getThingOfType((ThingType) typecombobox.getSelectedItem(), d);
				ThingEditDialog.this.onThingTypeChange.onChange(t);
				onUpdate.run();
			}
		});
		
		ThingAttributeElementEditor taed = new ThingAttributeElementEditor(d, DataMapKey.DIRECTION, onUpdate);
		ThingAttributeElementEditor taef = new ThingAttributeElementEditor(d, DataMapKey.FOOD_STATE, onUpdate);
		ThingAttributeElementEditor taei = new ThingAttributeElementEditor(d, DataMapKey.INVENTORY, onUpdate);
		ThingAttributeElementEditor taem = new ThingAttributeElementEditor(d, DataMapKey.MAKES, onUpdate);
		
		jp.add(taed);
		jp.add(taef);
		jp.add(taei);
		jp.add(taem);
		
		jdl.add(jp);
		jdl.pack();
		jdl.validate();
		jdl.setVisible(true);
	}
	
	/**
	 * Sets the {@link Runnable} to run when the ThingType changes
	 * @param onThingTypeChange the {@link Runnable} to run
	 */
	public void setOnThingTypeChange(ThingTypeChangeEvent onThingTypeChange) {
		this.onThingTypeChange = onThingTypeChange;
	}
	
	/**
	 * Gets the {@link Thing} this {@link ThingEditDialog} is editing
	 * @return the {@link Thing}
	 */
	public Thing getThing() {
		return t;
	}

	private void setTitle(JDialog me, Thing t, String extra) {
		me.setTitle("Edit " + t.getName() + " " + extra);
	}
}
