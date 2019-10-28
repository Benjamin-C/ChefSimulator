package benjaminc.chef_leveldesigner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import benjaminc.chef_simulator.graphics.GameSpace;

/**
 * @author Benjamin-C
 *
 */
public class SpaceEditDialog {
	
	protected GameSpace space;
	protected Runnable onDone;
	
	public SpaceEditDialog(GameSpace space, Runnable onDone, Runnable onUpdate) {
		this.space = space;
		this.onDone = onDone;
		
		JDialog jdl = new JDialog();
		jdl.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		jdl.setTitle("Edit " + space.getLoc().getX() + "," + space.getLoc().getY());
		
		JPanel panel = new JPanel();
		
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(onDone != null) {
					onDone.run();
				}
				jdl.dispose();
			}
		});
		
		SpaceList sl = new SpaceList(space, onUpdate);
		
		panel.add(sl);
		panel.add(exit);
		
		jdl.add(panel);
		jdl.pack();
		jdl.validate();
		jdl.setVisible(true);
	}

}
