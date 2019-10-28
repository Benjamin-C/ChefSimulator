package benjaminc.chef_leveldesigner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import benjaminc.chef_simulator.data.DataMapKey;
import benjaminc.chef_simulator.graphics.GraphicalDrawer;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_textures.dialog.AreYouSureDialog;
import benjaminc.chef_textures.dialog.AreYouSureDialogRunnable;
import benjaminc.chef_textures.dialog.CreationDialog;
import benjaminc.chef_textures.dialog.CreationDialogDoneRunnable;
import benjaminc.chef_textures.shapes.GraphicalShape;
import benjaminc.chef_utils.graphics.Shape;
import benjaminc.chef_utils.graphics.ShapeType;
import benjaminc.chef_utils.graphics.Texture;

public class SpaceListElement extends JPanel {
	
	/**  */ private static final long serialVersionUID = 6750985189976938626L;

	private Thing me;

	private JButton upbutton;
	private JButton downbutton;
	private JButton editbutton;
	private JButton deletebutton;
	
	private Runnable onUpdate;
	
	@SuppressWarnings("serial")
	public SpaceListElement(Thing th, Runnable onUpdate, SpaceList list) {
		me = th;
		this.onUpdate = onUpdate;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		editbutton = new JButton(" ") {
			@Override public void paint(Graphics g) {
				super.paint(g);
				int edgeofset = 4;
				int size = Math.min(getWidth(), (getHeight()-edgeofset));
				int osx = (getWidth() - size) / 2;
				int osy = (getHeight() - size) / 2;
				int sqsz = size/8;
				boolean color = true;
				
				g.setColor(Color.DARK_GRAY);
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(Color.white);
				g.drawRect(0, 0, getWidth(), getHeight());
				
				for(int i = 0; i < size; i = i + sqsz) {
					for(int j = 0; j < size; j = j + sqsz) {
						if(color) {
							g.setColor(new Color(200, 200, 200));
						} else {
							g.setColor(new Color(255, 255, 255));
						}
						color = !color;
						g.fillRect(i+osx, j+osy, sqsz, sqsz);
					}
					//color = !color;
				}
				GraphicalDrawer gd = new GraphicalDrawer(g);
				gd.draw(me, osx, osy, size, size);
		}};
		editbutton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) { openEditDialog(false, "Title", null);} });
		upbutton = new JButton(" ") {
			@Override public void paint(Graphics g) {
				super.paint(g);
				int w = getWidth();
				int h = getHeight();
				int sw = w/8;
				g.setColor(Color.darkGray);
				g.fillRect(0, 0, w, h);
				g.setColor(Color.white);
				g.drawRect(0, 0, w, h);
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect((w-sw)/2, sw*2, sw, h-(sw*3));
				int trix[] = {sw*2, w/2, w-(sw*2)};
				int triy[] = {h/2, sw, h/2};
				g.fillPolygon(trix, triy, 3);
		}};
		upbutton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) { list.swap(me, 1); System.out.println("up swap done"); } });
		downbutton = new JButton(" ") {
			@Override public void paint(Graphics g) {
				super.paint(g);
				int w = getWidth();
				int h = getHeight();
				int sw = w/8;
				g.setColor(Color.darkGray);
				g.fillRect(0, 0, w, h);
				g.setColor(Color.white);
				g.drawRect(0, 0, w, h);
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect((w-sw)/2, sw*2, sw, h-(sw*3));
				int trix[] = {sw*2, w/2, w-(sw*2)};
				int triy[] = {h/2, h-sw, h/2};
				g.fillPolygon(trix, triy, 3);
				g.setColor(Color.white);
				g.drawRect(0, 0, w, h);
		}};
		downbutton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) { list.swap(me, -1); System.out.println("down swap done"); } });
		deletebutton = new JButton(" ") {
			@Override public void paint(Graphics g) {
				super.paint(g);
				int w = getWidth();
				int h = getHeight();
				int sw = h/6;
				g.setColor(Color.darkGray);
				g.fillRect(0, 0, w, h);
				g.setColor(Color.white);
				g.drawRect(0, 0, w, h);
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(sw*2, (h-sw)/2, w-(sw*4), sw);
//				int trix[] = {sw*2, w/2, w-(sw*2)};
//				int triy[] = {h/2, h-sw, h/2};
//				g.fillPolygon(trix, triy, 3);
				g.setColor(Color.white);
				g.drawRect(0, 0, w, h);
		}};
		deletebutton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				new AreYouSureDialog("Delete", "Do you really want to delete the shape?", new AreYouSureDialogRunnable() {
					@Override public void yes() { list.remove(me); System.out.println("Remove done"); }
					@Override public void no() { } });
			}
		});
		add(editbutton);
		add(upbutton);
		add(downbutton);
		add(deletebutton);
	}
	
	public void setOnUpdate(Runnable onUpdate) {
		this.onUpdate = onUpdate;
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paint(g);
	}
	public void openEditDialog(boolean isNew, String title, Runnable onDone) {
		new ThingEditDialog(me.getDataMap());
	}
	public Thing getThing() {
		return me;
	}
}
