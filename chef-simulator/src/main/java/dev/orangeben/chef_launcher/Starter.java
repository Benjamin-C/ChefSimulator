package dev.orangeben.chef_launcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import dev.orangeben.chef_simulator.ChefSimulatorControl;

public class Starter {

	private File gameFile;
	
	public Starter() {
		JFrame jf = new JFrame("Chef Simulator Launcher");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setIconImage(new ImageIcon("assets/icon/launcher-icon.png").getImage());
		
		gameFile = null;
		
		JPanel window = new JPanel();
		window.setLayout(new BoxLayout(window, BoxLayout.Y_AXIS));
	
		JPanel gamestartjp = new JPanel();
		gamestartjp.setLayout(new BoxLayout(gamestartjp, BoxLayout.Y_AXIS));
		
		JPanel gamestartjpTop = new JPanel();
		
		JLabel gamestartjl = new JLabel("Resolution");
		JTextArea gamestartjt = new JTextArea(1, 3);
		gamestartjt.setText("40");
		gamestartjpTop.add(gamestartjl); gamestartjpTop.add(gamestartjt);
		
		JLabel gamestartjlf = new JLabel("FPS");
		JTextArea gamestartjtf = new JTextArea(1, 3);
		gamestartjtf.setText("15");
		JCheckBox gamestartjcb = new JCheckBox("Lag-O-Meter");
		gamestartjcb.setSelected(true);
		JButton gamestartjb = new JButton("go");
		addGoFeature(gamestartjtf, gamestartjb);
		addGoFeature(gamestartjt, gamestartjb);
		gamestartjb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				try {
					int sc = Integer.parseInt(gamestartjt.getText());
					int fs = Integer.parseInt(gamestartjtf.getText());
					ChefSimulatorControl.run(sc, fs, gamestartjcb.isSelected(), gameFile);
					jf.dispose();
				} catch (NumberFormatException e) { System.out.println("Size must be number"); }
		} });
		
		gamestartjpTop.add(gamestartjlf); gamestartjpTop.add(gamestartjtf); gamestartjpTop.add(gamestartjcb); gamestartjpTop.add(gamestartjb);
		gamestartjp.add(gamestartjpTop);
		
		JPanel gamestartjpBottom = new JPanel();
		
		JLabel levelselect = new JLabel();
		levelselect.setText("No level chosen");
		
		gamestartjpBottom.add(levelselect);
		JButton lsb = new JButton("Choose file");
		lsb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser fc = new JFileChooser();
				fc.setApproveButtonText("Load");
				
				int returnVal = fc.showOpenDialog(null);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        	gameFile = fc.getSelectedFile();
		        	levelselect.setText(gameFile.getName());
		        } else {
		        	System.out.println("Open command cancelled by user.");
		        }
			}
		});
		gamestartjpBottom.add(lsb);
        
		gamestartjp.add(gamestartjpBottom);
		window.add(gamestartjp);
		
		JPanel serverConnect = new JPanel();
		
		JTextArea serverAddress = new JTextArea();
		serverAddress.setText("localhost:25242");
		serverConnect.add(serverAddress);
		
		JButton serverConnectButton = new JButton("Connect");
		
		serverConnectButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				try {
					int sc = Integer.parseInt(gamestartjt.getText());
					int fs = Integer.parseInt(gamestartjtf.getText());
					ChefSimulatorControl.run(sc, fs, gamestartjcb.isSelected(), serverAddress.getText());
					jf.dispose();
				} catch (NumberFormatException e) { System.out.println("Size must be number"); }
		} });
		serverConnect.add(serverConnectButton);
		window.add(serverConnect);
		
		JPanel others = new JPanel();
		
		JButton texturestartjb = new JButton("Texture Editor");
		texturestartjb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dev.orangeben.chef_textures.TextureMain.run();
				jf.dispose();
			}
		});
		others.add(texturestartjb);
		
		JButton leveldesignstartjb = new JButton("Level Designer");
		leveldesignstartjb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dev.orangeben.chef_leveldesigner.LevelDesignerMain.run();
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
