package dev.benjaminc.chef_leveldesigner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import dev.benjaminc.chef_leveldesigner.EditableListEditDialog.EditableListEditDialogDrawEvent;
import dev.benjaminc.chef_simulator.things.Thing;

public class EditableList<T> extends JPanel {
	
	/**  */ private static final long serialVersionUID = -242513008438104799L;
	
	public interface EditableListEvents<T> {
		public abstract T makeNew();
		/**
		 * Called when the edit button of a {@link EditableListElement} is called
		 * @param t the {@link Thing} to edit
		 * @param te the {@link EditableListElement} the {@link Thing} belongs to
		 * @param tl the {@link EditableList} that the {@link EditableListElement} belongs to
		 * @param onUpdate Runs {@link EditableListEvents#onUpdate()} when editing asks to update
		 */
		public abstract void edit(T t, EditableListElement<T> te, EditableList<T> tl, EditableListEvents<T> onUpdate);
		public abstract void onUpdate();
	}
	
	private JPanel controls;
	
	private JButton add;
	
	private List<T> list;
	
	private EditableListEvents<T> onEdit;
	private EditableListEditDialogDrawEvent<T> onDraw;
	
	/**
	 * Makes a new {@link EditableList} of a type
	 * @param editlist a {@link List} of type to edit
	 * @param editableListEvents an {@link EditableListEvents} to control what the list does
	 */
	public EditableList(List<T> editlist, EditableListEvents<T> editableListEvents, EditableListEditDialogDrawEvent<T> onDraw) {
		System.out.println("Where is this?");
		list = editlist;
		
		onEdit = editableListEvents;
		this.onDraw = onDraw;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(TOP_ALIGNMENT);

		JPanel stateboxpanel = new JPanel();

		add(stateboxpanel);
		
		add = new JButton("+");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(onEdit.makeNew());
				add(onEdit.makeNew());
			}
		});
		add(add);
		
		controls = new JPanel();
		controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
		controls.add(new JTextArea("you no should see dis"));
		add(controls);
		
		update();
	}

	/**
	 * Sets what happens when this is edited
	 * @param onEdit the {@link EditableListEvents} to run when edits happen
	 */
	public void setOnEdit(EditableListEvents<T> onEdit) {
		this.onEdit = onEdit;
	}
	
	/**
	 * Adds something to the {@link EditableList}
	 * @param t somethign to add
	 */
	public void add(T t) {
		System.out.println("Adding something: " + t);
		if(t != null) {
			list.add(t);
			update();
		} else {
			System.out.println("Thing was null. Skipping");
		}
	}
	
	/**
	 * Gets the stuff from the {@link EditableList}
	 * @return the stuff
	 */
	public List<T> getThings() {
		return list;
	}
	
	/**
	 * Replaces something with somethign else
	 * @param oldThing something to be replaced
	 * @param newThing something else to replace it with
	 */
	public void replace(T oldThing, T newThing) {
		list.set(list.indexOf(oldThing), newThing);
		System.out.println(list);
	}
	
	/**
	 * Swap a some somethings
	 * @param t the something to swap
	 * @param dir the int distance to swap
	 */
	public void swap(T t, int dir) {
		int begin = 0;
		int end = list.size();
		if(dir > 0) {
			end = end - dir;
		} else if(dir < 0) {
			begin = begin - dir;
		} else if(dir == 0) {
			return;
		}
		int i = find(t, begin, end);
		if(i >= 0) {
			System.out.println("Moving " + i + " to " + (i + dir));
			printelems();
			T se = list.get(i);
			list.set(i, list.get(i+dir));
			list.set(i+dir, se);
			printelems();
			//Collections.swap(elems, i, i+dir);
			onEdit.onUpdate();
			update();
		}
	}
	/**
	 * Remove something
	 * @param t the thing to remove
	 */
	public void remove(T t) {
		int n = find(t);
		if(n >= 0) {
			list.remove(n);
		}
		update();
	}
	/**
	 * Finds something
	 * @param t the something to find
	 * @return the index of the something
	 */
	public int find(T t) {
		return find(t, 0, list.size());
	}
	/**
	 * Finds something between indexes
	 * @param t the something to find
	 * @param begin the int begin search index
	 * @param end the int end search index
	 * @return the index of the something
	 */
	public int find(T t, int begin, int end) {
		System.out.println("Finding " + t + " between " + begin + " and " + end);
		for(int i = begin; i < end; i++) {
			System.out.println("Searching element " + i);
			System.out.println("Chekcing if " + t + " equals " + list.get(i));
			if(list.get(i).equals(t)) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Prints the elems to {@link System#out}
	 */
	public void printelems() {
		String print = "ShapeLiseElement[";
		for(int i = 0; i < list.size(); i++) {
			if(i != 0) {
				print = print + ",";
			}
			print = print + list.get(i).toString();
		}
		System.out.println(print);
	}
	/**
	 * The nunber of somethings
	 * @return the int count
	 */
	public int shapeCount() {
		return list.size();
	}
	/**
	 * Updates graphcis
	 */
	public void update() {
		controls.removeAll();
		for(int i = list.size() - 1; i >= 0; i--) {
			controls.add(new EditableListElement<T>(list.get(i), this, onEdit, onDraw));
		}
		try {
			SwingUtilities.getWindowAncestor(this).pack();
		} catch(NullPointerException e) {
			
		}
		onEdit.onUpdate();
	}
	public void runOnUpdate() {
		onEdit.onUpdate();
	}
	
	@Override
	public String toString() {
		String s = "EditableList[";
		for(int i = 0; i < list.size(); i++) {
			if(i != 0) {
				s = s + ", ";
			}
			s = s + list.get(i);
		}
		s = s + "]";
		return s;
	}
}
