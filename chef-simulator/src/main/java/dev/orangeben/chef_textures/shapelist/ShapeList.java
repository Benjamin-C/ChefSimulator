package dev.orangeben.chef_textures.shapelist;

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

import dev.orangeben.chef_simulator.data.FoodState;
import dev.orangeben.chef_textures.shapes.GraphicalShape;
import dev.orangeben.chef_utils.graphics.Shape;
import dev.orangeben.chef_utils.graphics.ShapeType;
import dev.orangeben.chef_utils.graphics.Texture;

public class ShapeList extends JPanel {
	
	/**  */ private static final long serialVersionUID = -242513008438104799L;
	
	private List<ShapeListElement> elems;
	
	private Runnable onUpdate;
	
	private JPanel controls;
	
	private JButton add;
	
	private JComboBox<FoodState> statebox;
	private FoodState state;
	
	private Texture texture;
	
	public ShapeList(Texture t) {
		texture = t;
		state = FoodState.RAW;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(TOP_ALIGNMENT);
		
		elems = new ArrayList<ShapeListElement>();
		
		JPanel stateboxpanel = new JPanel();
		statebox = new JComboBox<FoodState>(FoodState.values());
		statebox.setSelectedItem(FoodState.RAW);
		statebox.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				texture.put(state, getShapes());
				state = (FoodState) statebox.getSelectedItem();
				setAll(texture, state);
				update();
			}
		});
		stateboxpanel.add(statebox);
		add(stateboxpanel);
		
		add = new JButton("+");
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GraphicalShape ns = new GraphicalShape(ShapeType.SOLID_RECTANTLE, 0, 0, 0, 0, 0, 0, 0, 0);
				ns.createEditDialog(true, new Runnable() {
					
					@Override
					public void run() {
						addShape(ns);
						update();
						//me.getParent().get
					}
				});
			}
		});
		add(add);
		
		controls = new JPanel();
		controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
		controls.add(new JTextArea("you no should see dis"));
		add(controls);
		
		update();
	}

	public void save() {
		texture.put(state, getShapes());
	}
	public void setOnUpdate(Runnable onUpdate) {
		this.onUpdate = onUpdate;
	}
	
	public void addShape(GraphicalShape s) {
		if(s != null) {
			ShapeListElement se = new ShapeListElement(s, onUpdate, this);
			elems.add(se);
			update();
			controls.add(se);
		} else {
			System.out.println("Shape was null. Skipping");
		}
	}
	
	public void setAll(Texture t) {
		setAll(t, FoodState.RAW);
	}
	public void setAll(Texture t, FoodState s) {
		texture = t;
		elems.clear();
		if(t.get(s) == null) {
			List<Shape> list = new ArrayList<Shape>();
			t.put(s, list);
		}
		System.out.println(t.get(s));
		for(Shape sh : t.get(s)) {
			addShape(new GraphicalShape(sh));
		}
	}
	
	public Texture getTexture() {
		return texture;
	}
	public void swap(GraphicalShape s, int dir) {
		int begin = 0;
		int end = elems.size();
		if(dir > 0) {
			end = end - dir;
		} else if(dir < 0) {
			begin = begin - dir;
		} else if(dir == 0) {
			return;
		}
		int i = find(s, begin, end);
		if(i >= 0) {
			System.out.println("Moving " + i + " to " + (i + dir));
			printelems();
			ShapeListElement se = elems.get(i);
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
	public void remove(GraphicalShape s) {
		int n = find(s);
		if(n >= 0) {
			elems.remove(n);
		}
		update();
	}
	public int find(GraphicalShape s) {
		return find(s, 0, elems.size());
	}
	public int find(GraphicalShape s, int begin, int end) {
		for(int i = begin; i < end; i++) {
			System.out.println("Searching element " + i);
			if(elems.get(i).getShape().equals(s)) {
				return i;
			}
		}
		return -1;
	}
	public void printelems() {
		String print = "ShapeLiseElement[";
		for(int i = 0; i < elems.size(); i++) {
			if(i != 0) {
				print = print + ",";
			}
			print = print + elems.get(i).getShape();
		}
		System.out.println(print);
	}
	public int shapeCount() {
		return elems.size();
	}
	public void update() {
		controls.removeAll();
		for(int i = 0; i < elems.size(); i++) {
			controls.add(elems.get(i));
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
	public List<ShapeListElement> getShapeListElements() {
		return elems;
	}
	
	public List<Shape> getShapes() {
		List<Shape> out = new ArrayList<Shape>();
		for(int i = 0; i < elems.size(); i++) {
			out.add(elems.get(i).getShape());
		}
		return out;
	}
}
