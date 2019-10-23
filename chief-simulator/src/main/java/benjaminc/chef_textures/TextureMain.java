package benjaminc.chef_textures;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import benjaminc.chef_textures.dialog.AreYouSureDialog;
import benjaminc.chef_textures.dialog.AreYouSureDialogRunnable;
import benjaminc.chef_textures.dialog.MessageDialog;
import benjaminc.chef_textures.shapelist.ShapeList;
import benjaminc.chef_textures.shapes.GraphicalShape;
import benjaminc.chef_utils.files.ShapeLoader;
import benjaminc.chef_utils.files.ShapeSaver;
import benjaminc.chef_utils.graphics.ShapeType;
import benjaminc.chef_utils.graphics.Texture;

public class TextureMain {

//	public static void main(String args[]) { // Could be main if wanted
	public static void run() {
		JFrame frame = new JFrame("Texture Editor");
		// Doesn't work RN, maybe later > frame.setBackground(Color.RED);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		frame.setIconImage(new ImageIcon("/assets/icon/texture_icon.png").getImage());
		frame.setIconImage(new ImageIcon("assets/icon/texture-icon.png").getImage());
		//frame.setIconImage(new ImageIcon("assets/icon.png").getImage());
		
		Texture tx = new Texture();
		ShapeList sl = new ShapeList(tx);
		
		JFileChooser fc = new JFileChooser();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File"); fileMenu.setMnemonic('f'); menuBar.add(fileMenu);
		JMenuItem loadMenuItem = new JMenuItem("Open", KeyEvent.VK_D);
		loadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		loadMenuItem.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				new AreYouSureDialog("Open file?", "Are you sure you want to open a new file?\nThis will delte any unsaved work", new AreYouSureDialogRunnable() {
					@Override public void yes() {
						//Handle open button action.
						fc.setApproveButtonText("Load");
						int returnVal = fc.showOpenDialog(null);

				        if (returnVal == JFileChooser.APPROVE_OPTION) {
				        	File file = fc.getSelectedFile();
				        	ShapeLoader sload = new ShapeLoader();
				        	Texture txt = sload.loadShapeListFromFile(file);
				        	System.out.println(txt);
				        	sl.setAll(txt);
				        } else {
				        	System.out.println("Open command cancelled by user.");
				        }
					}
					@Override public void no() { } });
		}});
		fileMenu.add(loadMenuItem);
		
		JMenuItem saveMenuItem = new JMenuItem("Save", KeyEvent.VK_D);
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveMenuItem.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				fc.setApproveButtonText("Save");
		        int returnVal = fc.showOpenDialog(null);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            System.out.println("Saving to: " + file.getName() + ".");
		            try {
		            	PrintWriter pr = new PrintWriter(file);
		            	ShapeSaver ssave = new ShapeSaver();
//						for(ShapeListElement s : sl.getShapeListElements()) {
//							pr.println(ssave.getJsonFromShape(s.getShape()));
//						}
		            	sl.save();
		            	pr.println(ssave.save(sl.getTexture()));
						pr.flush();
						pr.close();
						System.out.println("Done");
						new MessageDialog("Save texture", "Saving complete to " + file.getPath());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		        } else {
		        	System.out.println("Saving command cancelled by user.");
		        } }});
		fileMenu.add(saveMenuItem);
		
		frame.setJMenuBar(menuBar);
		
		// Graphics editor
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		// Shape preview
		GraphicsPanel graphics = new GraphicsPanel(sl);
		graphics.setPreferredSize(new Dimension(512, 512));
		panel.add(graphics);
		
		// Example Shapes
		ShapeLoader sload = new ShapeLoader();
		ShapeSaver ssave = new ShapeSaver();
		GraphicalShape s = new GraphicalShape(ShapeType.SOLID_RECTANTLE, 0.5f, 0.45f, 0.2f, 0.2f, 0f, 255, 0, 0);
		sl.addShape(s);
		GraphicalShape s2 = new GraphicalShape(ShapeType.SOLID_RECTANTLE, 0.55f, 0.6f, 0.20f, 0.20f, 0f, 0, 222, 0);
		//sl.addShape(s2);
		String js = ssave.getJsonFromShape(s2);
		System.out.println(js);
		GraphicalShape s2b = new GraphicalShape(sload.getShapeFromJSON(js));
		sl.addShape(s2b);
		GraphicalShape s3 = new GraphicalShape(ShapeType.SOLID_RECTANTLE, 0.6f, 0.5f, 0.20f, 0.20f, 0f, 0, 0, 226);
		sl.addShape(s3);
		GraphicalShape s4 = new GraphicalShape(sload.getShapeFromJSON("{\"shapetype\":\"solid_elipse\",\"x\":\"0.7\",\"y\":\"0.1\",\"w\":\"0.1\",\"h\":\"0.3\",\"r\":\"255\",\"g\":\"0\",\"b\":\"252\"}"));
		sl.addShape(s4);
		
		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		sidePanel.add(sl);
		panel.add(sidePanel);
		
		frame.add(panel);
		frame.validate();
		frame.pack();
		frame.setVisible(true);
	}
}
