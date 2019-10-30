package benjaminc.chef_leveldesigner;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;

import benjaminc.chef_simulator.things.Thing;

/**
 * @author Benjamin-C
 *
 */
public class ThingListEditDialog {
	
	/** the {@link List} of {@link Thing} to edit */
	protected List<Thing> things;
	/** the {@link JPanel} where the dialog lives */
	protected JPanel panel;
	
	/**
	 * Makes a {@link ThingEditDialog} in a popup
	 * @param things the {@link List} of {@link Thing} to edit
	 * @param onUpdate the {@link Runnable} to run when done
	 */
	public ThingListEditDialog(List<Thing> things, Runnable onUpdate) {
		this(things, onUpdate, true);
	}
	
	/**
	 * Makes a {@link ThingEditDialog}
	 * @param things the {@link List} of {@link Thing} to edit
	 * @param onUpdate the {@link Runnable} to run when done
	 * @param seperate a {@link Boolean} to select if the dialog should create a popup to exist in
	 */
	public ThingListEditDialog(List<Thing> things, Runnable onUpdate, boolean seperate) {
		this.things = things;
		
		if(seperate) {
			
		}
		
		panel = new JPanel();
					
		System.out.println(things);
		
		ThingList sl = new ThingList(things, new ThingListElementEditEvent() {
			@Override public void edit(Thing t, ThingListElement e, ThingList list, Runnable onUpdate) {
				new ThingEditDialog(t, "", new ThingTypeChangeEvent() {
					@Override public void onChange(Thing newThing) { list.replace(t, newThing); e.setThing(newThing); } }, onUpdate); } }, onUpdate);
		
		panel.add(sl);
		
		if(seperate) {
			JDialog jdl = new JDialog();
			jdl.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			jdl.setTitle("Edit Things");
			
			jdl.add(panel);
			jdl.pack();
			jdl.validate();
			jdl.setVisible(true);
		}
	}
	
	/**
	 * Gets the {@link JPanel} of the dialog
	 * @return the {@link JPanel}
	 */
	public JPanel getPanel() {
		return panel;
	}

}
