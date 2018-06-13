package benjaminc.chief_simulator.graphics;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private static JFrame jf;
	private static JPanel panel;
	
	private static int width;
	private static int height;
	
	public static void setGamePanel(JFrame j) {
		jf = j;
	}
	
	public static boolean drawMainPanel() {
		panel = new JPanel() {
			@Override
            public void paintComponent(Graphics g) {
				// Do painty stuff here
			}
        };
        jf.add(panel);
        jf.validate();
        return true;
	}
}
