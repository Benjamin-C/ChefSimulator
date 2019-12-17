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
import java.util.UUID;

import benjaminc.chef_simulator.Game;
import benjaminc.chef_simulator.control.KeyListen;
import benjaminc.chef_simulator.control.KeyListenAction;
import benjaminc.chef_simulator.graphics.debug.DataOMeter;
import benjaminc.chef_simulator.graphics.debug.DebugDataZone;
import benjaminc.chef_simulator.graphics.debug.DebugDataZone.DebugDataZoneDataGetter;

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
	
	/** the {@link KeyListen} to send keystorkes to */
	private KeyListen keyListen;
	
	/** The {@link DataOMeter} lagometer */
	private DataOMeter lagometer;
	/** The {@link DataOMeter} heapometer */
	private DataOMeter heapometer;
	/** The {@link DataOMeter} fpsometer */
	private DataOMeter fpsometer;
	
	/** The {@link DebugDataZone} of type long of dropped frames */
	private DebugDataZone dropzone;
	/** The {@link DebugDataZone} of type long of current frame */
	private DebugDataZone framezone;
	
	/** The {@link List} of {@link DebugDataZone} of type ? to show */
	private List<DebugDataZone> ddzs;
	
	/** The height of the {@link DataOMeter} */
	private int meterheight = 100;

	/** The number of dropped frames */
	private Long drops;
	/** The long current frame */
	private Long mframe;
	
	/** the boolean to enable the Lag-O-Meter */
	private boolean lagoEnables;
	
	/** the {@link ChatBox} to show */
	private ChatBox chatBox;
	
	/** the {@link PauseScreen} to show when paused */
	private PauseScreen pauseScreen;
	
	/** the default {@link Color} for text backgounds */
	private Color defaultTextBackgroundColor;
	
	/** the default {@link Color} for text */
	private Color defaultTextForgroundColor;
	/**
	 * @param lvl the {@link Drawable} to draw
	 * @param scale the int scale for each box
	 * @param panelWidth the width of the panel in px
	 * @param panelHeight the height of the panel in px
	 * @param lago a boolean to show the lagometer
	 * @param maxFPS the {@link Double} max fps for the lagometer
	 * @param exitOnClose the boolean to exit the program when the window closes
	 */
	public GamePanel(Drawable lvl, int scale, int panelWidth, int panelHeight, boolean lago, double maxFPS, boolean exitOnClose) {
		toDraw = lvl;
		boxWidth = scale;
		boxHeight = boxWidth;
		xloc = 0;
		yloc = boxHeight;
		width = (int) toDraw.getWidth();
		height = (int) toDraw.getHeight();
		
		lagoEnables = lago;
		
		defaultTextBackgroundColor = new Color(64, 64, 64, 192);
		defaultTextForgroundColor = new Color(255, 255, 255, 192);
		lagometer = new DataOMeter(1000, width * boxWidth, meterheight, "tps");
		fpsometer = new DataOMeter(1000, width * boxWidth, meterheight, "fps");
		heapometer = new DataOMeter(1000, width * boxWidth, meterheight, (int) (Runtime.getRuntime().maxMemory() / Math.pow(2,  20)), "MiB heap");
		
		ddzs = new ArrayList<DebugDataZone>();
		
		drops = (long) 0;
		dropzone = new DebugDataZone("Dropped ticks", new DebugDataZoneDataGetter() { @Override public String getData() { return Long.toString(drops); } }, "", true);
		framezone = new DebugDataZone("Current tick", new DebugDataZoneDataGetter() { @Override public String getData() { return Long.toString(mframe); } }, "", true);
		
		ddzs.add(dropzone);
		ddzs.add(framezone);
		
		chatBox = new ChatBox(12, (int) (width*4), 14, defaultTextBackgroundColor, defaultTextForgroundColor, 60*4);
		
		pauseScreen = new PauseScreen(defaultTextBackgroundColor, defaultTextForgroundColor);
		
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
		
		update(0, 0, 0);
		//drawRoom(xloc, yloc, 0);
        keyListen = new KeyListen();
        
        keyListen.addKeyListen(new KeyListenAction() {
			
			@Override
			public void keyReleaseEvent(int key) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressEvent(int key) {
				if(key == KeyEvent.VK_F3 && !Game.getPaused()) {
					toggleLagometer();
				}
			}
		});
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
		chatBox.setWidth((int) (width*boxWidth*.75));
		System.out.println("I make picture now");
		update(0, 0, 0);
	}
	
	private Double mtps;
	
	/**
	 * Draw the game panel to the JFamen
	 * @param droppedFrameCount 
	 * @param fps how many FPS to show
	 */
	public void update(double tps, int droppedFrameCount, long frame) {
		mtps = tps;
		drops = (long) droppedFrameCount;
		mframe = frame;
		if(panel != null) {
			panel.repaint();
		} else {
			panel = new JPanel() {
	
				// IDK why Eclipse needs this, but it complains when I don't give it
				private static final long serialVersionUID = -6353780231591627020L;
	
				private long start = 0;
				
				@Override
	            public void paintComponent(Graphics g) {
//					double heap = Runtime.getRuntime().totalMemory();
//					double heapchange = Runtime.getRuntime().totalMemory()-heap;
//					if(Math.abs(heapchange) > 1) {
//						System.out.println("Heap changed bigly during TickTimer loop! " + heapchange);
//					}
					
					long laststart = start;
					start = System.nanoTime();
					long ttime = start - laststart;

					toDraw.draw(g, xloc, yloc, boxWidth, boxHeight, mtps);
					
					double mfps = 1/((double) ttime/1000000000);
					
					if(lagoEnables) {
						lagometer.draw(g, 0, ((height + 1) * boxHeight), mtps);
						fpsometer.draw(g, (int) ((double) lagometer.getWidth()), ((height + 1) * boxHeight), mfps);
						heapometer.draw(g, 0, ((height + 1) * boxHeight)-(1*meterheight), (double) (Runtime.getRuntime().totalMemory() / Math.pow(2,  20)));
						
						chatBox.draw(g, 0, ((height + 1) * boxHeight)-(2*meterheight), mframe);
						
						int pos = 0;
						for(int i = 0; i < ddzs.size(); i++) {
							int c = ddzs.get(i).draw(g, 0, pos);
							if(c >= 0) {
								pos = pos + c;
							} else {
								
							}
						}
					} else {
						lagometer.addData(mtps);
						fpsometer.addData(mfps);
						heapometer.addData((double) (Runtime.getRuntime().totalMemory() / Math.pow(2,  20)));
						
						chatBox.draw(g, 0, ((height + 1) * boxHeight), mframe);
					}
					
					if(Game.getPaused()) {
						pauseScreen.draw(g, 0, 0, width*boxWidth, (height+1)*boxHeight);
					}
				}
			};
			jf.add(panel);
			jf.revalidate();
		}
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	/**
	 * Gets the {@link PauseScreen} for the game.
	 * @return the {@link PauseScreen}
	 */
	public PauseScreen getPauseScreen() {
		return pauseScreen;
	}
	
	/**
	 * Add a task when a key is pressed
	 * @param a the {@link KeyListenAction} to add
	 * @return the {@link UUID} of the {@link KeyListenAction}
	 */
	public UUID addKeyListener(KeyListenAction a) {
		return keyListen.addKeyListen(a);
	}
	
	/**
	 * Removes a task when a key is pressed
	 * @param a the {@link UUID} of the KeyListenAction to remove
	 */
	public void removeKeyListener(UUID a) {
		keyListen.removeKeyListen(a);
	}
	
	/**
	 * Enables the lagometer
	 * @param enabled the Boolean to enable
	 */
	public void enableLagometer(boolean enabled) {
		lagoEnables = enabled;
	}
	
	public void toggleLagometer() {
		lagoEnables = !lagoEnables;
	}
	/**
	 * See if the lagometer is enabled
	 * @return a boolean of the lagometer state
	 */
	public boolean getLagometerEnabled() {
		return lagoEnables;
	}

	public ChatBox getChatBox() {
		return chatBox;
	}
}
