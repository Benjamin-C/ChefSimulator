package benjaminc.chief_simulator.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import benjaminc.chief_simulator.Game;
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
	private Game game;
	
	private KeyListen keyListen;
	
	public GamePanel(Game g, Room lvl) {
		game = g;
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
				for(int i = 0; i < level.getWidth(); i++) {
					if(game.getObjectives().size() > i) {
						game.getObjectives().get(i).draw(g, (i * boxWidth) + x, 0, boxWidth, boxHeight);
					}
					if(i == width - 1) {
						g.setColor(Color.GREEN);
						g.fillRect(x+(i*boxWidth), 0, boxWidth, boxHeight);
						g.setColor(Color.BLACK);
						g.setFont(new Font("arial", 0, boxHeight));
						g.drawString(game.getScore() + "", x+(i*boxWidth), boxHeight);
					}
				}
				
			}
        };
        jf.add(panel);
        jf.validate();
	}
}
