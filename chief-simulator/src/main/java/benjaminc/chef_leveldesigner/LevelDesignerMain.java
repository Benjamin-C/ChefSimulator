package benjaminc.chef_leveldesigner;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.rooms.Room;
import benjaminc.chef_simulator.things.BasicThing;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_simulator.things.food.Potato;

/**
 * @author Benjamin-C
 *
 */
public class LevelDesignerMain {
	
	protected static int boxSize = 40;
	
	protected static Thing toAdd;
	
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
		
		toAdd = new Potato();
		
		String json = "{\"TYPE\":\"DISHBIN\", \"DATAMAP\":{\"VARIANT\":\"-1\", \"DIRECTION\":\"UP\", \"UUID\":\"63492fd5-3e93-4a7a-9a1d-43c8fc968286\", \"INVENTORY\":[{\"TYPE\":\"POTATO\", \"DATAMAP\":{\"DIRECTION\":\"UP\", \"UUID\":\"2a86b4d8-f303-4968-9a52-0eb544eadc51\", \"FOOD_STATE\":\"RAW\"}}, {\"TYPE\":\"TOMATO\", \"DATAMAP\":{\"DIRECTION\":\"UP\", \"UUID\":\"f18f579b-7a65-4df5-863d-cca07b696f2e\", \"FOOD_STATE\":\"RAW\"}}], \"FOOD_STATE\":\"RAW\"}}";
		Thing toAdd2 = BasicThing.makeThingFromJSON(json);
		System.out.println(toAdd2.asJSON());
		r.addThing(toAdd2, new Location(2, 2));
		
		jf.setJMenuBar(new LevelDesignerMenuBar(r));
		
		ThingEditDialog controlediter = new ThingEditDialog(toAdd, "Title?", new ThingTypeChangeEvent() {
			@Override public void onChange(Thing newThing) { toAdd = newThing; }
		}, new Runnable() { public void run() { } }, false);
		JPanel controljp = controlediter.getPanel();
		controljp.setBackground(new Color(16, 16, 16));
		
		for(Component c : controljp.getComponents()) {
			if(c instanceof JPanel) {
				c.setBackground(new Color(16, 16, 16));
			}
		}
		
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
					doMouseThing(loc);
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
		doMouseThing(new Location(e.getX()/boxSize, e.getY()/boxSize));
	}
	protected static void doMouseThing(Location loc) {
		lastDragLoc = loc;
		if(loc.getX() >= 0 && loc.getX() < r.getWidth() && loc.getY() >= 0 && loc.getY() < r.getHeight()) {
			switch(mouseButton[0]) { // 1=left 2=middle 3=right
			case 1: {
				r.addThing(toAdd.clone(), loc);
			} break;
			case 2: {
				new SpaceEditDialog(r.getSpace(loc), new Runnable() {
					@Override public void run() { roomjp.repaint();}
				});
			} break;
			case 3: {
				r.getSpace(loc).removeTopThing();
			} break;
			}
			roomjp.repaint();
		}
	}
}
