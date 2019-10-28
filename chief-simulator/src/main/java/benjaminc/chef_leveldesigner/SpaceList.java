package benjaminc.chef_leveldesigner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import benjaminc.chef_simulator.data.FoodState;
import benjaminc.chef_simulator.graphics.GameSpace;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_textures.shapes.GraphicalShape;
import benjaminc.chef_utils.graphics.Shape;
import benjaminc.chef_utils.graphics.ShapeType;
import benjaminc.chef_utils.graphics.Texture;

public class SpaceList extends JPanel {
	
	/**  */ private static final long serialVersionUID = -242513008438104799L;
	
//	private List<SpaceListElement> elems;
	
	private Runnable onUpdate;
	
	private JPanel controls;
	
	private JButton add;
	
	private GameSpace space;
	
	public SpaceList(GameSpace s, Runnable onUpdate) {
		space = s;
		
		this.onUpdate = onUpdate;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(TOP_ALIGNMENT);
		
//		elems = new ArrayList<SpaceListElement>();

		JPanel stateboxpanel = new JPanel();

		add(stateboxpanel);
		
		add = new JButton("+");
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				GraphicalShape ns = new GraphicalShape(ShapeType.SOLID_RECTANTLE, 0, 0, 0, 0, 0, 0, 0, 0);
//				ns.createEditDialog(true, new Runnable() {
//					
//					@Override
//					public void run() {
//						addShape(ns);
//						update();
//						//me.getParent().get
//					}
//				});
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
			space.addThing(t);
			update();
		} else {
			System.out.println("Thing was null. Skipping");
		}
	}
	
	public GameSpace getSpace() {
		return space;
	}
	public void swap(Thing t, int dir) {
		int begin = 0;
		int end = space.size();
		List<Thing> elems = space.getThings();
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
			Thing se = elems.get(i);
			elems.set(i, elems.get(i+dir));
			elems.set(i+dir, se);
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
			space.removeThing(n);
		}
		update();
	}
	public int find(Thing t) {
		return find(t, 0, space.size());
	}
	public int find(Thing t, int begin, int end) {
		System.out.println("Finding " + t + " between " + begin + " and " + end);
		for(int i = begin; i < end; i++) {
			System.out.println("Searching element " + i);
			System.out.println("Chekcing if " + t + " equals " + space.getThing(i));
			if(space.getThing(i).equals(t)) {
				return i;
			}
		}
		return -1;
	}
	public void printelems() {
		String print = "ShapeLiseElement[";
		for(int i = 0; i < space.size(); i++) {
			if(i != 0) {
				print = print + ",";
			}
			print = print + space.getThing(i).toString();
		}
		System.out.println(print);
	}
	public int shapeCount() {
		return space.size();
	}
	public void update() {
//		SpaceListElement se = ;
		controls.removeAll();
		for(int i = space.size() - 1; i >= 0; i--) {
			controls.add(new SpaceListElement(space.getThing(i), onUpdate, this));
		}
		try {
			SwingUtilities.getWindowAncestor(this).pack();
		} catch(NullPointerException e) {
			
		}
		if(onUpdate != null) {
			onUpdate.run();
		}
	}
	public void redrawShape() {
		if(onUpdate != null) {
			onUpdate.run();
		}
	}
	
	public List<Thing> getThings() {
		List<Thing> out = new ArrayList<Thing>();
		for(int i = 0; i < space.size(); i++) {
			out.add(space.getThing(i));
		}
		return out;
	}
}
