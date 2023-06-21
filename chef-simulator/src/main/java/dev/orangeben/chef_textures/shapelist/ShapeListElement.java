package dev.orangeben.chef_textures.shapelist;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import dev.orangeben.chef_textures.dialog.AreYouSureDialog;
import dev.orangeben.chef_textures.dialog.AreYouSureDialogRunnable;
import dev.orangeben.chef_textures.shapes.GraphicalShape;

public class ShapeListElement extends JPanel {
	
	/**  */ private static final long serialVersionUID = 6750985189976938626L;

	private GraphicalShape me;

	private JButton upbutton;
	private JButton downbutton;
	private JButton editbutton;
	private JButton deletebutton;
	
	private Runnable onUpdate;
	
	@SuppressWarnings("serial")
	public ShapeListElement(GraphicalShape sh, Runnable onUpdate, ShapeList list) {
		me = sh;
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
				me.draw(size, size, osx, osy, g);
//				int w = getWidth();
//				int h = getHeight();
//				int sw = w/8;
//				g.setColor(Color.darkGray);
//				g.fillRect(0, 0, w, h);
//				g.setColor(Color.white);
//				g.drawRect(0, 0, w, h);
//				g.setColor(Color.LIGHT_GRAY);
//				g.fillRect((w-sw)/2, (int) (sw*2.5), sw, (h/2)-(sw*2));
//				g.fillRect((w-sw)/2, sw*1, sw, sw);
//				int trix[] = {(w-sw)/2, w/2, ((w-sw)/2)+sw};
//				int triy[] = {(h/2)+(1*sw), h-sw, (h/2)+(1*sw)};
//				g.fillPolygon(trix, triy, 3);
//				g.setColor(Color.white);
//				g.drawRect(0, 0, w, h);
		}};
		editbutton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) { me.createEditDialog(new Runnable() {
				@Override public void run() { ShapeListElement.this.onUpdate.run(); } }); } });
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
			@Override public void actionPerformed(ActionEvent arg0) { list.swap(me, -1); System.out.println("up swap done"); } });
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
			@Override public void actionPerformed(ActionEvent arg0) { list.swap(me, 1); System.out.println("down swap done"); } });
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
	public void openEditDialog() {
		me.createEditDialog(null);
	}
	public GraphicalShape getShape() {
		return me;
	}
}
