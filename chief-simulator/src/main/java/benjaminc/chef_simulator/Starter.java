package benjaminc.chef_simulator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class Starter {

	public Starter() {
		JFrame jf = new JFrame("Chef Simulator Launcher");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setIconImage(new ImageIcon("assets/icon/launcher-icon.png").getImage());
		JPanel jp = new JPanel();
		
		JLabel jl = new JLabel("Resolution");
		JTextArea jt = new JTextArea("40");
		jp.add(jl); jp.add(jt);
		jf.add(jp); jf.pack(); jf.validate();
		JLabel jlf = new JLabel("FPS");
		JTextArea jtf = new JTextArea("030");
		JCheckBox jcb = new JCheckBox("Lag-O-Meter");
		jcb.setSelected(true);
		JButton jb = new JButton("go");
		jtf.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "check");
		jtf.getActionMap().put("check", new AbstractAction() {
			private static final long serialVersionUID = 6672135527803981557L; // Keep Eclipse happy
			@Override public void actionPerformed(ActionEvent arg0) { System.out.println("Do da ting");jb.doClick(); }
		});
		jt.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "check");
		jt.getActionMap().put("check", new AbstractAction() {
			private static final long serialVersionUID = 6672135527803981557L; // Keep Eclipse happy
			@Override public void actionPerformed(ActionEvent arg0) { System.out.println("Do da ting");jb.doClick(); }
		});
		jb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				try {
					int sc = Integer.parseInt(jt.getText());int fs = Integer.parseInt(jtf.getText()); new Game(sc, fs, jcb.isSelected()); jf.dispose();
				} catch (NumberFormatException e) { System.out.println("Size must be number"); }
		} });
		jp.add(jlf); jp.add(jtf); jp.add(jcb); jp.add(jb);
		jf.add(jp); jf.pack(); jf.validate();
		jf.setVisible(true);
	}
}
