package dev.orangeben.chef_textures.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class AreYouSureDialog {

	public AreYouSureDialog(String title, String msg, AreYouSureDialogRunnable done) {
		JDialog d = new JDialog();
		d.setTitle(title);
		
		JPanel p = new JPanel();
		//p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		JPanel txt = new JPanel();
		txt.setLayout(new BoxLayout(txt, BoxLayout.Y_AXIS));
		String b[] = msg.split("\n");
		
		for(int i = 0; i < b.length; i++) {
			JTextField t = new JTextField();
			t.setText(b[i]);
			t.setEditable(false);
			t.setBorder(BorderFactory.createEmptyBorder());
			txt.add(t);
		}
		p.add(txt);
		
		JPanel btn = new JPanel();
		//btn.setLayout(new BoxLayout(btn, BoxLayout.X_AXIS));
		JButton by = new JButton("Yes");
		by.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				done.yes();
				d.dispose();
			}
		});
		by.setSelected(true);
		btn.add(by);
		JButton bn = new JButton("No");
		bn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				done.no();
				d.dispose();
			}
		});
		btn.add(bn);
		p.add(btn);
		
		p.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "check");
		p.getActionMap().put("check", new AbstractAction() {
			private static final long serialVersionUID = 6672135527803981557L; // Keep Eclipse happy
			@Override public void actionPerformed(ActionEvent arg0) { System.out.println("Do da ting");by.doClick(); }
		});
		
		d.add(p);
		d.pack();
		d.setVisible(true);
	}
}