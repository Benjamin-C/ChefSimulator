package benjaminc.chef_leveldesigner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import benjaminc.chef_leveldesigner.EditableList.EditableListEvents;
import benjaminc.chef_leveldesigner.EditableListEditDialog.EditableListEditDialogDrawEvent;
import benjaminc.chef_textures.dialog.AreYouSureDialog;
import benjaminc.chef_textures.dialog.AreYouSureDialogRunnable;

public class EditableListElement<T> extends JPanel {
	
	private static final long serialVersionUID = 6750985189976938626L;
	
	private T me;

	private JButton upbutton;
	private JButton downbutton;
	private JButton editbutton;
	private JButton deletebutton;
	
	protected EditableList<T> list;
	protected EditableListEvents<T> onEdit;
	
	/**
	 * @param th type to be edited
	 * @param spacelist the {@link EditableList} to edit on
	 * @param onEditEvent the {@link EditableListEvents} to run when stuff happens
	 */
	public EditableListElement(T th, EditableList<T> spacelist, EditableListEvents<T> onEditEvent, EditableListEditDialogDrawEvent<T> onDraw) {
		me = th;
		list = spacelist;
		onEdit = onEditEvent;
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		editbutton = new JButton(" ") {
			private static final long serialVersionUID = -9214487232805201226L;

			@Override public void paint(Graphics g) {
				super.paint(g);
				int edgeofset = 4;
				int size = Math.min(getWidth(), (getHeight()-edgeofset));
				int osx = (getWidth() - size) / 2;
				int osy = (getHeight() - size) / 2;
				
				g.setColor(Color.DARK_GRAY);
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(Color.white);
				g.drawRect(0, 0, getWidth(), getHeight());
				
				int sqsz = size/8;
				boolean color = true;
				
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
				}

				if(onDraw != null) {
					onDraw.draw(g, me, osx, osy, size, size);
				} else {
					g.setColor(Color.BLUE);
					g.fillOval(osx, osy, size, size);
				}
		}};
		upbutton = new JButton(" ") {
			private static final long serialVersionUID = 743096334077956769L;

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
		downbutton = new JButton(" ") {
			private static final long serialVersionUID = -3624960225386403040L;

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
		deletebutton = new JButton(" ") {
			private static final long serialVersionUID = 7251218315421051984L;
			
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

		this.onEdit = new EditableListEvents<T>() {
			@Override public T makeNew() { return onEditEvent.makeNew(); }
			@Override public void edit(T t, EditableListElement<T> te, EditableList<T> tl, EditableListEvents<T> onUpdate) {
				onEditEvent.edit(th, te, tl, onUpdate); }
			@Override public void onUpdate() { editbutton.repaint(); onEditEvent.onUpdate(); }
		};
		
		editbutton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) { if(onEdit != null) { onEdit.edit(me, EditableListElement.this, list, EditableListElement.this.onEdit); } } });
		upbutton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) { list.swap(me, 1); System.out.println("up swap done"); } });
		downbutton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) { list.swap(me, -1); System.out.println("down swap done"); } });
		deletebutton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				new AreYouSureDialog("Delete", "Do you really want to delete this?", new AreYouSureDialogRunnable() {
					@Override public void yes() { list.remove(me); System.out.println("Remove done"); }
					@Override public void no() { } });
			}
		});
		
		add(editbutton);
		add(upbutton);
		add(downbutton);
		add(deletebutton);
	}
	
	/**
	 * Sets what happes when editing happens
	 * @param onEdit the {@link EditableListEvents} to run when happens
	 */
	public void setOnEdit(EditableListEvents<T> onEdit) {
		this.onEdit = onEdit;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paint(g);
	}
	
	/**
	 * Gets me
	 * @return what this represents
	 */
	public T get() {
		return me;
	}
	/**
	 * Sets what this represents
	 * @param t the [type]
	 */
	public void set(T t) {
		me = t;
	}
}
