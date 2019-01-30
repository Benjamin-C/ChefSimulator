package benjaminc.chief_simulator.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import benjaminc.chief_simulator.Game;
import benjaminc.chief_simulator.control.KeyListen;
import benjaminc.chief_simulator.control.KeyListenAction;

public class GamePanel extends JPanel {
	
	// IDK why this is needed, but Eclipse complained without it
	private static final long serialVersionUID = -2595814863243266971L;
	
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
	
	private boolean lagometer;
	private double maxFPS;
	private List<Double> lagometerdata;
	
	public GamePanel(Game g, Room lvl) {
		this(g, lvl, 40, false, 0);
	}
	public GamePanel(Game g, Room lvl, int scale, boolean lagometer, double maxFPS) {
		game = g;
		level = lvl;
		boxWidth = scale;
		boxHeight = boxWidth;
		xloc = 0;
		yloc = boxHeight;
		width = (int) level.getSize().getWidth();
		height = (int) level.getSize().getHeight();
		jf = new JFrame("I am bob");
		jf.setResizable(false);
		jf.setSize((width * boxWidth) + xloc + 17, (height * boxHeight) + yloc + 40);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("I make picture now");
		drawRoom(xloc, yloc, 0);
        keyListen = new KeyListen();
        jf.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {keyListen.keyTyped(e);}
			public void keyReleased(KeyEvent e) {keyListen.keyReleased(e);}
			public void keyPressed(KeyEvent e) {keyListen.keyPressed(e);}
		});
        jf.validate();
        jf.setVisible(true);
        this.lagometer = lagometer;
        this.maxFPS = maxFPS;
	}
	
	public void setLevel(Room lvl) {
		level = lvl;
		width = (int) level.getSize().getWidth();
		height = (int) level.getSize().getHeight();
		jf.setSize((width * boxWidth) + xloc + 17, (height * boxHeight) + yloc + 40);
		System.out.println("I make picture now");
		drawRoom(xloc, yloc, 0);
	}
	public void update(double fps) {
		drawRoom(xloc, yloc, fps);
	}
	
	public void addKeyListener(KeyListenAction a) {
		keyListen.addKeyListen(a);
	}
	
	private void drawRoom(int x, int y, double fps) {
		panel = new JPanel() {

			// IDK why Eclipse needs this, but it complains when I don't give it
			private static final long serialVersionUID = -6353780231591627020L;

			@Override
            public void paintComponent(Graphics g) {
				level.draw(g, x, y, boxWidth, boxHeight);
				for(int i = 0; i < level.getWidth(); i++) {
					g.setColor(new Color(16, 64, 16));
					g.fillRect(x+(i*boxWidth), 0, boxWidth, boxHeight);
					if(game.getObjectives().size() > i) {
						game.getObjectives().get(i).draw(g, (i * boxWidth) + x, 0, boxWidth, boxHeight);
					}
					if(i == width - 1) {
						g.setColor(Color.WHITE);
						g.setFont(new Font("arial", 0, boxHeight));
						String score = game.getScore().getScore() + "";
						int textWidth = g.getFontMetrics().stringWidth(score+ "");
						g.drawString(score + "", x+((i+1)*boxWidth)-textWidth-(boxWidth/8), boxHeight - (boxHeight / 8));
					}
				}
				g.setFont(new Font(g.getFont().getName(), 0, (int) ((boxWidth * 0.5) * Toolkit.getDefaultToolkit().getScreenResolution() / 72.0)));
				g.setColor(Color.RED);
				int h = g.getFontMetrics().getHeight();
				String fpss = Double.toString(fps);
				g.drawString("fps:" + fpss.substring(0, Math.min(fpss.length(), 4)), level.getWidth() * boxWidth / 2, h);
				if(lagometer) {
					if(lagometerdata == null) {
						lagometerdata = new ArrayList<Double>();
					}
					lagometerdata.add(fps);
					while(lagometerdata.size() > (width * boxWidth)) {
						lagometerdata.remove(0);
					}
					int bottom = height * boxHeight;
					for(int i = 0; i < lagometerdata.size(); i++) {
						int rv = 128;
						int gv = 128;
						int bv = 128;
						int av = 256-64;
						
						double value = lagometerdata.get(i).doubleValue() / maxFPS;
						rv = rv + (int) (rv * (1 - value));
						gv = gv + (int) (gv * value);
						
						g.setColor(new Color(Math.min(rv, 255), Math.min(gv, 255), Math.min(bv, 255), Math.min(av, 255)));
						
						g.drawLine(i, bottom, i, bottom - (int) (value * 100));
					}
				}
			}
        };
        jf.add(panel);
        jf.validate();
	}
}
