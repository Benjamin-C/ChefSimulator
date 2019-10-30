package benjaminc.chef_leveldesigner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.food.Potato;

public class ThingList extends JPanel {
	
	/**  */ private static final long serialVersionUID = -242513008438104799L;
	
//	private List<SpaceListElement> elems;
	
	private Runnable onUpdate;
	
	private JPanel controls;
	
	private JButton add;
	
	private List<Thing> things;
	
	private ThingListElementEditEvent onEdit;
	
	public ThingList(List<Thing> t, ThingListElementEditEvent onEditEvent, Runnable onUpdate) {
		System.out.println("Where is this?");
		things = t;
		
		this.onUpdate = onUpdate;
		onEdit = onEditEvent;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(TOP_ALIGNMENT);

		JPanel stateboxpanel = new JPanel();

		add(stateboxpanel);
		
		add = new JButton("+");
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addThing(new Potato());
			}
		});
		add(add);
		
		controls = new JPanel();
		controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
		controls.add(new JTextArea("you no should see dis"));
		add(controls);
		
		update();
	}

	public void setOnUpdate(Runnable onUpdate) {
		this.onUpdate = onUpdate;
	}
	
	public void addThing(Thing t) {
		System.out.println("Adding Thing " + t);
		if(t != null) {
			things.add(t);
			update();
		} else {
			System.out.println("Thing was null. Skipping");
		}
	}
	
	public List<Thing> getThings() {
		return things;
	}
	
	public void replace(Thing oldThing, Thing newThing) {
		int loc = find(oldThing);
		things.set(loc, newThing);
		System.out.println(things);
	}
	
	public void swap(Thing t, int dir) {
		int begin = 0;
		int end = things.size();
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
			Thing se = things.get(i);
			things.set(i, things.get(i+dir));
			things.set(i+dir, se);
			printelems();
			//Collections.swap(elems, i, i+dir);
			if(onUpdate != null) {
				onUpdate.run();
			}
			update();
		}
	}
	public void remove(Thing t) {
		int n = find(t);
		if(n >= 0) {
			things.remove(n);
		}
		update();
	}
	public int find(Thing t) {
		return find(t, 0, things.size());
	}
	public int find(Thing t, int begin, int end) {
		System.out.println("Finding " + t + " between " + begin + " and " + end);
		for(int i = begin; i < end; i++) {
			System.out.println("Searching element " + i);
			System.out.println("Chekcing if " + t + " equals " + things.get(i));
			if(things.get(i).equals(t)) {
				return i;
			}
		}
		return -1;
	}
	public void printelems() {
		String print = "ShapeLiseElement[";
		for(int i = 0; i < things.size(); i++) {
			if(i != 0) {
				print = print + ",";
			}
			print = print + things.get(i).toString();
		}
		System.out.println(print);
	}
	public int shapeCount() {
		return things.size();
	}
	public void update() {
		controls.removeAll();
		for(int i = things.size() - 1; i >= 0; i--) {
			controls.add(new ThingListElement(things.get(i), onUpdate, this, onEdit));
		}
		try {
			SwingUtilities.getWindowAncestor(this).pack();
		} catch(NullPointerException e) {
			
		}
		if(onUpdate != null) {
			onUpdate.run();
		}
	}
	public void redrawThing() {
		if(onUpdate != null) {
			onUpdate.run();
		}
	}
}
