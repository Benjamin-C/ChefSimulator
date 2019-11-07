package benjaminc.chef_leveldesigner;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;

import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_leveldesigner.EditableList.EditableListEvents;

/**
 * @author Benjamin-C
 *
 */
public class EditableListEditDialog<T> {
	
	/** the {@link T} of {@link Thing} to edit */
	protected List<T> things;
	/** the {@link JPanel} where the dialog lives */
	protected JPanel panel;
	
	/**
	 * Makes a {@link ThingEditDialog} in a popup
	 * @param things the {@link List} of {@link Thing} to edit
	 * @param onUpdate the {@link Runnable} to run when done
	 */
	public EditableListEditDialog(List<T> things, EditableListEvents<T> edits) {
		this(things, edits, true);
	}
	
	/**
	 * Makes a {@link ThingEditDialog}
	 * @param things the {@link List} of {@link Thing} to edit
	 * @param onUpdate the {@link Runnable} to run when done
	 * @param seperate a {@link Boolean} to select if the dialog should create a popup to exist in
	 */
	public EditableListEditDialog(List<T> things, EditableListEvents<T> edits, boolean seperate) {
		this.things = things;
		
		if(seperate) {
			
		}
		
		panel = new JPanel();
					
		System.out.println(things);
		
		EditableList<T> s0 = new EditableList<T>(things, edits);
		
		panel.add(s0);
		
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
