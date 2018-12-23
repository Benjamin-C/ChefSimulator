package benjaminc.chief_simulator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Starter {

	public Starter() {
		JFrame jf = new JFrame("Chef Simulator Launcher");
		JPanel jp = new JPanel();
		
		JLabel jl = new JLabel("Resolution");
		JTextArea jt = new JTextArea("40");
		JButton jb = new JButton("go");
		jb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				try {
					int sc = Integer.parseInt(jt.getText()); new Game(sc); jf.dispose();
				} catch (NumberFormatException e) { System.out.println("Size must be number"); }
		} });
		jp.add(jl); jp.add(jt); jp.add(jb);
		jf.add(jp); jf.pack(); jf.validate();
		jf.setVisible(true);
	}
}
