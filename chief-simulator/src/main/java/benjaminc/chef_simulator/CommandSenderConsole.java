package benjaminc.chef_simulator;

import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CommandSenderConsole {
	
	private JDialog jdl;
	private JPanel jp;
	private JTextField jt;
	private JButton jb;
	
	private List<String> txts;
	private int pos;
	
	public CommandSenderConsole() {
		jdl = new JDialog();
		
		jp = new JPanel();
		jt = new JTextField(32);
		jb = new JButton("Send");
		
		txts = new ArrayList<String>();
		
		jt.addKeyListener(new KeyListener() {
			@Override public void keyTyped(KeyEvent e) { }
			@Override public void keyReleased(KeyEvent e) { }
			@Override public void keyPressed(KeyEvent e) { 
				if(e.getKeyCode() == KeyEvent.VK_UP) {
					pos = Math.min(++pos, txts.size()-1);
					jt.setText(txts.get(pos));
					if(pos >= 0 && txts.size() > 0) {
						jt.setText(txts.get(pos));
					} else {
						jt.setText("");
					}
				} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					pos = Math.max(--pos, -1);
					
					if(pos >= 0 && txts.size() > 0) {
						jt.setText(txts.get(pos));
					} else {
						jt.setText("");
					}
				} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					jb.doClick();
				}
//				Game.chat(Integer.toString(pos));
			}
		});
		jb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				Game.consoleInput(jt.getText());
				txts.add(0, jt.getText());
				jt.setText("");
			}
		});
		
		jp.add(jt);
		jp.add(jb);
		
		jdl.add(jp);
		jdl.pack();
		jdl.validate();
		jdl.setVisible(true);
	}

}
