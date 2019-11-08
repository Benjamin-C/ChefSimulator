package benjaminc.chef_leveldesigner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import benjaminc.chef_simulator.Objective;
import benjaminc.chef_simulator.things.Thing;
import benjaminc.chef_leveldesigner.ObjectiveEditDialog.ObjectiveTypeChangeEvent;
import benjaminc.chef_leveldesigner.ThingEditDialog.ThingTypeChangeEvent;

/**
 * @author Benjamin-C
 *
 */
public class ObjectiveEditDialogThatIsVeryWrong {
	
	protected Objective obj;
	
	protected JDialog jdl;
	protected JPanel jp;
	
	public ObjectiveEditDialogThatIsVeryWrong(Objective obj, ObjectiveTypeChangeEvent onUpdate, boolean seperate) {
		if(obj == null) {
			this.obj = new Objective(null, 0);
		} else {
			this.obj = obj;
		}
		
		System.out.println("This is a ThingEditDialog");

		jp  = new JPanel();
		
		ThingTypeChangeEvent ttce = new ThingTypeChangeEvent() {
			@Override public void onChange(Thing newThing) {
				obj.setTarget(newThing);
			}
		};
		
		JButton thingbutton = new JButton("Edit Thing");
		thingbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ThingEditDialog(obj.getTarget(), "for objective", ttce, new Runnable() {
					@Override public void run() {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		jp.add(thingbutton);
		
		JTextArea scoreArea = new JTextArea(1, 4);
		scoreArea.setText(Integer.toString(obj.getScore()));
		scoreArea.addFocusListener(new FocusListener() {
			@Override public void focusLost(FocusEvent arg0) { obj.setScore(Integer.parseInt(scoreArea.getText())); }
			@Override public void focusGained(FocusEvent arg0) { }
		});
		jp.add(scoreArea);
		
		if(seperate) {
			jdl = new JDialog();
			jdl.setTitle("Edit Objective");
			
			jdl.add(jp);
			jdl.pack();
			jdl.validate();
			jdl.setVisible(true);
		}
	}
}
