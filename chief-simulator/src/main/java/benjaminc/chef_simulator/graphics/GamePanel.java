package benjaminc.chef_simulator.graphics;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
	
	/** The {@link DataOMeter} lagometer */
	private DataOMeter lagometer;
	private DataOMeter heapometer;
	private DataOMeter fpsometer;
	
	private int meterheight = 100;

	/**
	 * 
	 * @param g the {@link Game} object this uses
	 * @param lvl the {@link Drawable} to draw
	 * @param scale the int scale for each box
	 * @param panelWidth the width of the panel in px
	 * @param panelHeight the height of the panel in px
	 * @param lago a boolean to show the lagometer
	 * @param maxFPS the {@link Double} max fps for the lagometer
	 * @param exitOnClose the boolean to exit the program when the window closes
	 */
	public GamePanel(Game g, Drawable lvl, int scale, int panelWidth, int panelHeight, boolean lago, double maxFPS, boolean exitOnClose) {
		game = g;
		toDraw = lvl;
		boxWidth = scale;
		boxHeight = boxWidth;
		xloc = 0;
		yloc = boxHeight;
		width = (int) toDraw.getWidth();
		height = (int) toDraw.getHeight();
		
		lagometer = new DataOMeter(lago, 1000, width * boxWidth, meterheight, "tps");
		fpsometer = new DataOMeter(lago, 1000, width * boxWidth, meterheight, "fps");
		heapometer = new DataOMeter(lago, 1000, width * boxWidth, meterheight, (int) (Runtime.getRuntime().maxMemory() / Math.pow(2,  20)), "MiB heap");
		
		jf = new JFrame("I am bob");
		jf.setResizable(false);
		//jf.setSize((width * boxWidth) + xloc + 17, (height * boxHeight) + yloc + 40);
		jf.setSize(panelWidth, panelHeight);
		if(exitOnClose) {
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else {
			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
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
        
        jf.validate();
        jf.setVisible(true); 
	}
	
	/**
	 * Sets what the GamePanel is drawing
	 * @param lvl the Drawable to show
	 */
	public void setLevel(Drawable lvl) {
		System.out.println("Set level");
		toDraw = lvl;
		width = (int) toDraw.getWidth();
		height = (int) toDraw.getHeight();
		int xos = jf.getInsets().left + jf.getInsets().right + 1;
		int yos = jf.getInsets().top + jf.getInsets().bottom + 1;
		jf.setSize((width * boxWidth) + xloc + xos, (height * boxHeight) + yloc + yos);
		lagometer.setWidth(jf.getWidth()/2); 
		fpsometer.setWidth(jf.getWidth()/2);
		heapometer.setWidth(jf.getWidth());
		System.out.println("I make picture now");
		update(0);
	}
	Double mtps;
	/**
	 * Draw the game panel to the JFamen
	 * @param fps how many FPS to show
	 */
	public void update(double tps) {
		mtps = tps;
		if(panel != null) {
			panel.repaint();
		} else {
			panel = new JPanel() {
	
				// IDK why Eclipse needs this, but it complains when I don't give it
				private static final long serialVersionUID = -6353780231591627020L;
	
				private long start = 0;
				
				@Override
	            public void paintComponent(Graphics g) {
					
					long laststart = start;
					start = System.nanoTime();
					long ttime = start - laststart;
					
					toDraw.draw(g, xloc, yloc, boxWidth, boxHeight, mtps);
					
					double mfps = 1/((double) ttime/1000000000);
					
					lagometer.draw(g, 0, ((height + 1) * boxHeight), mtps);
					fpsometer.draw(g, (int) ((double) lagometer.getWidth()), ((height + 1) * boxHeight), mfps);
					heapometer.draw(g, 0, ((height + 1) * boxHeight)-(1*meterheight), (double) (Runtime.getRuntime().totalMemory() / Math.pow(2,  20)));
				}
			};
			jf.add(panel);
			jf.revalidate();
		}
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
		lagometer.setEnabled(enabled);
		fpsometer.setEnabled(enabled);
		heapometer.setEnabled(enabled);
	}
	
	/**
	 * See if the lagometer is enabled
	 * @return a boolean of the lagometer state
	 */
	public boolean getLagometerEnabled() {
		return lagometer.isEnabled();
	}

}
