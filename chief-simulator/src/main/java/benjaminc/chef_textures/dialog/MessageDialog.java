package benjaminc.chef_textures.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MessageDialog {

	public MessageDialog(String title, String msg) {
		JDialog d = new JDialog();
		d.setTitle(title);
		JPanel p = new JPanel();
		JTextArea t = new JTextArea();
		t.setText(msg);
		t.setEditable(false);
		p.add(t);
		JButton b = new JButton("OK");
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				d.dispose();
			}
		});
		p.add(b);
		d.add(p);
		
		d.pack();
		d.setVisible(true);
	}
}
