package benjaminc.chef_leveldesigner;

import javax.swing.JDialog;
import javax.swing.JPanel;

import benjaminc.chef_simulator.graphics.GameSpace;
import benjaminc.chef_simulator.things.Thing;

/**
 * @author Benjamin-C
 *
 */
public class SpaceEditDialog {
	
	protected GameSpace space;
	
	public SpaceEditDialog(GameSpace space, Runnable onUpdate) {
		this.space = space;
		
		JDialog jdl = new JDialog();
		jdl.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jdl.setTitle("Edit " + space.getLoc().getX() + "," + space.getLoc().getY());
		
		JPanel panel = new JPanel();
		
		;		
					
		ThingList sl = new ThingList(space.getThings(), new ThingListElementEditEvent() {
			@Override public void edit(Thing t, ThingListElement e, ThingList list, Runnable onUpdate) {
				new ThingEditDialog(t, "", new ThingTypeChangeEvent() {
					@Override public void onChange(Thing newThing) { list.replace(t, newThing); e.setThing(newThing); } }, onUpdate); } }, onUpdate);
		
		panel.add(sl);
		
		jdl.add(panel);
		jdl.pack();
		jdl.validate();
		jdl.setVisible(true);
	}

}
