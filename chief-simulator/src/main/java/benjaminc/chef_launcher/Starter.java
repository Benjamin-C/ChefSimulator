package benjaminc.chef_launcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
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
		
		JPanel window = new JPanel();
		window.setLayout(new BoxLayout(window, BoxLayout.Y_AXIS));
	
		JPanel gamestartjp = new JPanel();
		
		JLabel gamestartjl = new JLabel("Resolution");
		JTextArea gamestartjt = new JTextArea("40");
		gamestartjp.add(gamestartjl); gamestartjp.add(gamestartjt);
		
		JLabel gamestartjlf = new JLabel("FPS");
		JTextArea gamestartjtf = new JTextArea("060");
		JCheckBox gamestartjcb = new JCheckBox("Lag-O-Meter");
		gamestartjcb.setSelected(true);
		JButton gamestartjb = new JButton("go");
		addGoFeature(gamestartjtf, gamestartjb);
		addGoFeature(gamestartjt, gamestartjb);
		gamestartjb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				try {
					int sc = Integer.parseInt(gamestartjt.getText());int fs = Integer.parseInt(gamestartjtf.getText()); benjaminc.chef_simulator.ChefSimulatorMain.run(sc, fs, gamestartjcb.isSelected()); jf.dispose();
				} catch (NumberFormatException e) { System.out.println("Size must be number"); }
		} });
		gamestartjp.add(gamestartjlf); gamestartjp.add(gamestartjtf); gamestartjp.add(gamestartjcb); gamestartjp.add(gamestartjb);
		
		window.add(gamestartjp);
		
		JPanel others = new JPanel();
		
		JButton texturestartjb = new JButton("Texture Editor");
		texturestartjb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				benjaminc.chef_textures.TextureMain.run();
				jf.dispose();
			}
		});
		others.add(texturestartjb);
		
		JButton leveldesignstartjb = new JButton("Level Designer");
		leveldesignstartjb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				benjaminc.chef_leveldesigner.LevelDesignerMain.run();
				jf.dispose();
			}
		});
		others.add(leveldesignstartjb);
		
		window.add(others);
		
		jf.add(window); jf.pack(); jf.validate();
		jf.setVisible(true);
	}
	
	private void addGoFeature(JComponent c, JButton b) {
		c.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "check");
		c.getActionMap().put("check", new AbstractAction() {
			private static final long serialVersionUID = 6672135527803981557L; // Keep Eclipse happy
			@Override public void actionPerformed(ActionEvent arg0) { System.out.println("Do da ting");b.doClick(); }
		});
	}
}
