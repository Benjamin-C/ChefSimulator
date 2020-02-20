package dev.benjaminc.chef_simulator.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.UUID;

import dev.benjaminc.chef_simulator.CommandSenderConsole;
import dev.benjaminc.chef_simulator.Game;
import dev.benjaminc.chef_simulator.control.KeyListenAction;

/**
 * @author 705822
 *
 */
public class PauseScreen {
	
	private Color backgroundColor;
	private Color textColor;
	private String label = "ChefSimulator";
	private String options = "[esc] Resume game\n[F2] Exit\n[F3] Debug\n[F5] Start multiplayer\n[F7] Open console input";
	private UUID kla;
	
	public PauseScreen(Color background, Color text) {
		this.backgroundColor = background;
		this.textColor = text;
	}
	
	public void enableKeys() {
		kla = Game.registerKeylistener(new KeyListenAction() {
			
			@Override
			public void keyReleaseEvent(int key) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressEvent(int key) {
				switch(key) {
				case KeyEvent.VK_F2: { Game.end(); } break;
				case KeyEvent.VK_F3: { Game.getGamePanel().toggleLagometer(); Game.redrawFrame(); } break;
				case KeyEvent.VK_F5: { Game.openMultiplayer(); } break;
				case KeyEvent.VK_F7: { new CommandSenderConsole(); } break;
				}
			}
		});
	}
	
	public void disableKeys() {
		Game.removeKeyListener(kla);
		kla = null;
	}
	
	/**
	 * Draws the {@link PauseScreen}. Scales to fit the allowed area.
	 * @param g the {@link Graphics} to draw on
	 * @param x the int x location
	 * @param y the int y location
	 * @param w the int width of the allowed area
	 * @param h the int height of the allowed area
	 */
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(backgroundColor);
		g.fillRect(x, y, w, h);
		
		g.setFont(g.getFont().deriveFont((float) h/8));
		g.setColor(textColor);
		
		Rectangle2D txt = g.getFontMetrics().getStringBounds(label, g);
		int xloc = (int) (w-txt.getWidth())/2;
		int yloc = (int) (h+txt.getHeight())/4;
		g.drawString(label, xloc , yloc);
		
		g.setFont(g.getFont().deriveFont(16f));
		yloc += g.getFontMetrics().getHeight();
		String[] sarr = options.split("\n");
		for(String s : sarr) {
			yloc += g.getFontMetrics().getHeight();
			g.drawString(s, xloc, yloc);
		}
	}

}
