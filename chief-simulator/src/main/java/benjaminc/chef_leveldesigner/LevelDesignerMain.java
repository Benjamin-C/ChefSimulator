package benjaminc.chef_leveldesigner;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import benjaminc.chef_simulator.control.Direction;
import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.data.DataMap;
import benjaminc.chef_simulator.data.DataMapKey;
import benjaminc.chef_simulator.data.FoodState;
import benjaminc.chef_simulator.graphics.GraphicalLoader;
import benjaminc.chef_simulator.rooms.Room;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.ThingType;

/**
 * @author Benjamin-C
 *
 */
public class LevelDesignerMain {
	
	protected static int boxSize = 40;
	
	protected static JComboBox<FoodState> foodstateCB;
	protected static JComboBox<ThingType> thingTypeCB;
	protected static JComboBox<Direction> directionCB;
	protected static Room r;
	
	protected static JPanel roomjp;
	
	protected static Location lastDragLoc;
	protected static int mouseButton[] = {0, 0};
	
	public static void run() {
		JFrame jf = new JFrame("Chef Simulator Level Designer");
		jf.setSize(new Dimension(128, 128));
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		
		r = new Room(16, 16, null, null, null);
		
		JPanel controljp = new JPanel();
		controljp.setBackground(new Color(16, 16, 16));
		thingTypeCB = new JComboBox<ThingType>(ThingType.values());
		thingTypeCB.setSelectedItem(ThingType.BEEF);
		controljp.add(thingTypeCB);
		
		foodstateCB = new JComboBox<FoodState>(FoodState.values());
		foodstateCB.setSelectedItem(FoodState.COOKED);
		controljp.add(foodstateCB);
		
		directionCB = new JComboBox<Direction>(Direction.values());
		directionCB.setSelectedItem(Direction.UP);
		controljp.add(directionCB);
		
		jp.add(controljp);
		
		roomjp = new JPanel() {
			/** */ private static final long serialVersionUID = -2039036730443292291L;

			@Override
			public void paint(Graphics g) {
				r.drawRoom(g, 0, 0, boxSize, boxSize);
			}
		};
		roomjp.setPreferredSize(new Dimension(r.getWidth() * boxSize, r.getHeight() * boxSize));
		
		roomjp.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				Location loc = new Location(e.getX()/boxSize, e.getY()/boxSize);
				if(lastDragLoc == null || !loc.equals(lastDragLoc)) {
					doMouseThing(e);
				}
			}
		});
		roomjp.addMouseListener(new MouseListener() {
			
			@Override public void mousePressed(MouseEvent e) {
				if(mouseButton[0] != 0) {
					mouseButton[1] = mouseButton[0];
					lastDragLoc = null;
				}
				mouseButton[0] = e.getButton();
				printMouseList();
				doMouseThing(e);
			}
			@Override public void mouseReleased(MouseEvent e) {
				if(mouseButton[1] != 0) {
					mouseButton[0] = mouseButton[1];
					mouseButton[1] = 0;
					doMouseThing(e);
				} else {
					mouseButton[0] = 0;
				}
				lastDragLoc = null;
				printMouseList();
				
			}
			@Override public void mouseClicked(MouseEvent e) { }
			@Override public void mouseExited(MouseEvent e) { }
			@Override public void mouseEntered(MouseEvent e) { }
		});
		
		jp.add(roomjp);
		jf.add(jp);
		
		jf.pack();
		jf.validate();
		jf.setVisible(true);
	}

	protected static void printMouseList() {
		System.out.println("[" + mouseButton[0] + "," + mouseButton[1] + "]" + lastDragLoc);
	}
	
	protected static void doMouseThing(MouseEvent e) {
		Location loc = new Location(e.getX()/boxSize, e.getY()/boxSize);
		
		if(loc.getX() >= 0 && loc.getX() < r.getWidth() && loc.getY() >= 0 && loc.getY() < r.getHeight()) {
			switch(mouseButton[0]) { // 1=left 2=middle 3=right
			case 1: {
				Object newobj = null;
				DataMap dataMap = new DataMap();
				dataMap.put(DataMapKey.FOOD_STATE, foodstateCB.getSelectedItem());
				dataMap.put(DataMapKey.DIRECTION, directionCB.getSelectedItem());
				try {
					//Class.forName( ClassNotFoundException | 
					
					Class<?> clazz = ((ThingType) thingTypeCB.getSelectedItem()).getMyClass();//.getName());
					Constructor<?> ctor = clazz.getConstructor(DataMap.class);
					newobj = ctor.newInstance(dataMap);
				} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException err) {
					err.printStackTrace();
				}
				
				if(newobj != null && newobj instanceof Thing) {
					r.addThing((Thing) newobj, loc);
				}
			} break;
			case 3: {
				r.getSpace(loc).removeTopThing();
			} break;
			}
			roomjp.repaint();
		}
	}
}
