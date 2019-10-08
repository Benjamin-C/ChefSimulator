package benjaminc.chef_simulator.graphics;

import java.awt.Color;
import java.awt.Graphics;

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
	
	/** the {@link JFrame} to draw to */
	private JFrame jf;
	/** the {@link JPanel} to draw to */
	private JPanel panel;
	
	/** the int number of cells wide to draw */
	private int width;
	/** the int number of cells tall to draw */
	private int height;
	/** the int width of each cell in px */
	private int boxWidth;
	/** the int height of each cell in px */
	private int boxHeight;
	/** the int X location to draw the Drawable at */
	private int xloc;
	/** the int y location to draw the Drawable at */
	private int yloc;
	
	/** the {@link Drawable} to draw */
	private Drawable toDraw;
	
	/** the {@link Game} this is working for  */
	private Game game;
	
	/** the {@link KeyListen} to send keystorkes to */
	private KeyListen keyListen;
	
	/** the boolean to show lagometer */
	private boolean lagometer;
	/** the {@link Double} max fps to display */
	private double maxFPS;
	/** the {@link List} of {@link LagOBar} to store the lagometer data in */
	private List<LagOBar> lagometerdata;
	/** the long ms for the next line to be drawn at */
	private long nextlinems;
	/** The delay between lines */
	private int linedel = 1000;
	/** the height of the lagometer */
	private int lagoheight = 100;
	
	/**
	 * 
	 * @param g the {@link Game} object this uses
	 * @param lvl the {@link Drawable} to draw
	 * @param scale the int scale for each box
	 * @param panelWidth the width of the panel in px
	 * @param panelHeight the height of the panel in px
	 * @param lago a boolean to show the lagometer
	 * @param maxFPS the {@link Double} max fps for the lagometer
	 */
	public GamePanel(Game g, Drawable lvl, int scale, int panelWidth, int panelHeight, boolean lago, double maxFPS) {
		game = g;
		toDraw = lvl;
		boxWidth = scale;
		boxHeight = boxWidth;
		xloc = 0;
		yloc = boxHeight;
		width = (int) toDraw.getWidth();
		height = (int) toDraw.getHeight();
		jf = new JFrame("I am bob");
		jf.setResizable(false);
		//jf.setSize((width * boxWidth) + xloc + 17, (height * boxHeight) + yloc + 40);
		jf.setSize(panelWidth, panelHeight);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setIconImage(new ImageIcon("assets/icon/icon.png").getImage());
		
		System.out.println("I make picture now");
		
		update(0);
		//drawRoom(xloc, yloc, 0);
        keyListen = new KeyListen(game, this);
        jf.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {keyListen.keyTyped(e);}
			public void keyReleased(KeyEvent e) {keyListen.keyReleased(e);}
			public void keyPressed(KeyEvent e) {keyListen.keyPressed(e);}
		});
        lagometer = lago;
        this.maxFPS = maxFPS;
        
        jf.validate();
        jf.setVisible(true); 
	}
	
	/**
	 * Sets what the GamePanel is drawing
	 * @param lvl the Drawable to show
	 */
	public void setLevel(Drawable lvl) {
		toDraw = lvl;
		width = (int) toDraw.getWidth();
		height = (int) toDraw.getHeight();
		jf.setSize((width * boxWidth) + xloc + 17, (height * boxHeight) + yloc + 40);
		System.out.println("I make picture now");
		update(0);
	}
	/**
	 * Draw the game panel to the JFamen
	 * @param fps how many FPS to show
	 */
	public void update(double fps) {
		panel = new JPanel() {

			// IDK why Eclipse needs this, but it complains when I don't give it
			private static final long serialVersionUID = -6353780231591627020L;

			@Override
            public void paintComponent(Graphics g) {
				toDraw.draw(g, xloc, yloc, boxWidth, boxHeight, fps);
				drawLago(g, fps);
			}
		};
		jf.add(panel);
		jf.validate();
	}
	
	/**
	 * Add a task when a key is pressed
	 * @param a the {@link KeyListenAction} to add
	 */
	public void addKeyListener(KeyListenAction a) {
		keyListen.addKeyListen(a);
	}
	
	/**
	 * Enables the lagometer
	 * @param enabled the Boolean to enable
	 */
	public void enableLagometer(boolean enabled) {
		lagometer = enabled;
	}
	
	/**
	 * See if the lagometer is enabled
	 * @return a boolean of the lagometer state
	 */
	public boolean getLagometerEnabled() {
		return lagometer;
	}
	
	/**
	 * Draws the lagometer
	 * @param g the Graphics object to draw in
	 * @param fps how many fps to add to the list
	 */
	private void drawLago(Graphics g, double fps) {
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
			g.setFont(g.getFont().deriveFont(16f));
			String fpss = Double.toString(fps);
			g.drawString(fpss.substring(0, Math.min(fpss.length(), 4)), 0, bottom);
		}
	}
}
