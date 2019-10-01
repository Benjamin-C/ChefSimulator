package benjaminc.chef_simulator.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.control.KeyListen;
import benjaminc.chef_simulator.control.KeyListenAction;

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
	private List<LagOBar> lagometerdata;
	private long nextlinems;
	private int linedel = 1000;
	private int lagoheight = 100;
	
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
		jf.setIconImage(new ImageIcon("assets/textures/icon/icon.png").getImage());
		
		System.out.println("I make picture now");
		drawRoom(xloc, yloc, 0);
        keyListen = new KeyListen(game, this);
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
	
	public void enableLagometer(boolean enabled) {
		lagometer = enabled;
	}
	
	public boolean getLagometerEnabled() {
		return lagometer;
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
				if(lagometerdata == null) {
					lagometerdata = new ArrayList<LagOBar>();
				}
				int rv = 128;
				int gv = 128;
				int bv = 128;
				int av = 256-64;
				
				double value = fps / maxFPS;
				rv = rv + (int) (rv * (1 - value));
				gv = gv + (int) (gv * value);
				
				Color c = Color.BLACK;
				if(nextlinems < System.currentTimeMillis() && value > .9d) {
					c = new Color(128, 128, 128, av);
					nextlinems = System.currentTimeMillis() + linedel;
				} else {
					c = new Color(Math.min(rv, 255), Math.min(gv, 255), Math.min(bv, 255), Math.min(av, 255));
				}
				lagometerdata.add(new LagOBar(value, (int) (value * lagoheight), c));
				while(lagometerdata.size() > (width * boxWidth)) {
					lagometerdata.remove(0);
				}
				int bottom = (height + 1) * boxHeight;
				int side = width * boxWidth;
				if(lagometer) {
					for(int i = 0; i < lagometerdata.size(); i++) {
						g.setColor(lagometerdata.get(i).getColor());
						g.drawLine(i, bottom, i, bottom - lagometerdata.get(i).getHeight());
					}
					g.setColor(new Color(255, 0, 0, 255));
					double locs[] = {.5d, .75d, 1d};
					g.setColor(new Color(255, 0, 0, 255));
					g.setFont(g.getFont().deriveFont(12f));
					for(int i = 0; i < locs.length; i++) {
						int l = bottom - (int) (locs[i] * lagoheight);
						g.drawLine(0, l, side, l);
						String txt = Integer.toString((int) (maxFPS * locs[i]));
						g.drawString(txt, 0, l + 2);
					}
				}
			}
        };
        jf.add(panel);
        jf.validate();
	}
}
