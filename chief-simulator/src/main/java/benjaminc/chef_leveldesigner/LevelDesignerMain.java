package benjaminc.chef_leveldesigner;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import benjaminc.chef_simulator.control.Location;
import benjaminc.chef_simulator.data.FoodState;
import benjaminc.chef_simulator.graphics.GraphicalDrawer;
import benjaminc.chef_simulator.rooms.Room;
import benjaminc.chef_simulator.things.ThingType;
import benjaminc.chef_simulator.things.food.Beef;

/**
 * @author Benjamin-C
 *
 */
public class LevelDesignerMain {
	
	protected static int boxSize = 40;
	
	public static void run() {
		JFrame jf = new JFrame("Chef Simulator Level Designer");
		jf.setSize(new Dimension(128, 128));
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		
		Room r = new Room(16, 16, null, null, null);
		
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(r.getWidth(), r.getHeight()));
		for(int x = 0; x < r.getWidth(); x++) {
			final int mx = x;
			for(int y = 0; y < r.getHeight(); y++) {
				final int my = y;
				JButton b = new JButton() {
					@Override
					public void paint(Graphics g) {
						super.paint(g);
						r.getSpace(new Location(mx, my)).draw(new GraphicalDrawer(g), 0, 0, boxSize, boxSize);
					}
				};
				b.setPreferredSize(new Dimension(boxSize, boxSize));
				b.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						Location here = new Location(mx, my);
						if(r.getSpace(here).contains(Beef.class)) {
							r.getSpace(here).removeAll(new Beef(0, FoodState.COOKED));
						} else {
							r.addThing(new Beef(0, FoodState.COOKED), new Location(mx, my));
						}
						System.out.println("(" + mx + "," + my + ")");
					}
				});
				jp.add(b);
			}
		}
		
		jf.add(jp);
		
		jf.pack();
		jf.validate();
		jf.setVisible(true);
	}

}
