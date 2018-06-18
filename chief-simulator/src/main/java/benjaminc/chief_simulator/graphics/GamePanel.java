package benjaminc.chief_simulator.graphics;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import benjaminc.chief_simulator.control.KeyListen;
import benjaminc.chief_simulator.control.KeyListenAction;

public class GamePanel extends JPanel {
	
	private JFrame jf;
	private JPanel panel;
	
	private int width;
	private int height;
	private int boxWidth;
	private int boxHeight;
	private int xloc;
	private int yloc;
	
	private Room level;
	
	private KeyListen keyListen;
	
	public GamePanel(Room lvl) {
		level = lvl;
		boxWidth = 64;
		boxHeight = 64;
		xloc = 0;
		yloc = 32;
		width = (int) level.getSize().getWidth();
		height = (int) level.getSize().getHeight();
		jf = new JFrame("I am bob");
		jf.setSize((width * boxWidth) + xloc + 17, (height * boxHeight) + yloc + 40);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("I make picture now");
		drawRoom(xloc, yloc);
        keyListen = new KeyListen();
        jf.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {keyListen.keyTyped(e);}
			public void keyReleased(KeyEvent e) {keyListen.keyReleased(e);}
			public void keyPressed(KeyEvent e) {keyListen.keyPressed(e);}
		});
        jf.validate();
        jf.setVisible(true);
        
	}
	
	public void update() {
		drawRoom(xloc, yloc);
	}
	
	public void addKeyListener(KeyListenAction a) {
		keyListen.addKeyListen(a);
	}
	
	private void drawRoom(int x, int y) {
		panel = new JPanel() {
			@Override
            public void paintComponent(Graphics g) {
				level.draw(g, x, y, boxWidth, boxHeight);
			}
        };
        jf.add(panel);
        jf.validate();
	}
}
