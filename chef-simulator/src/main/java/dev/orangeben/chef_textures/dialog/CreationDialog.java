package dev.orangeben.chef_textures.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dev.orangeben.chef_utils.graphics.ShapeType;


public class CreationDialog {
	
	private JDialog jdl;
	private CreationDialogDoneRunnable onDoneObj;
	
//	private JTextArea width;
//	private JTextArea height;
//	private JTextArea xstart;
//	private JTextArea ystart;
	
	private JComboBox<ShapeType> shapebox;
	
	private JTextArea inputs[];
	
	private JButton applybutton;
	private JButton donebutton;
	
	private final int optCnt;
	
	public CreationDialog(String title, CreationDialogDoneRunnable onDone, ShapeType shp, String labels[], String values[]) {
		this(title, onDone, shp, labels, values, false);
	}
	public CreationDialog(String title, CreationDialogDoneRunnable onDone, ShapeType shp, String labels[], String values[], boolean isNew) {
		onDoneObj = onDone;
		
		jdl = new JDialog();
		jdl.setTitle(title);
		JPanel jp = new JPanel();
		
		shapebox = new JComboBox<ShapeType>(ShapeType.values());
		shapebox.setSelectedItem(shp);
		jp.add(shapebox);
		
		optCnt = Math.min(labels.length, values.length);
		
		inputs = new JTextArea[optCnt];
		
		for(int i = 0; i < optCnt; i++) {
			jp.add(new JLabel(labels[i]));
			inputs[i] = new JTextArea(1, 3);
			inputs[i].setText(values[i]);
			jp.add(inputs[i]);
		}
		applybutton = new JButton("Apply");
		applybutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					float outvals[] = new float[optCnt];
					for(int i = 0; i < optCnt; i++) {
						outvals[i] = Float.parseFloat(inputs[i].getText());
					}
					onDoneObj.done((ShapeType) shapebox.getSelectedItem(), outvals);
					
				} catch(NumberFormatException e) {
					new MessageDialog("NumberFormatException", "Something that should be a number is not");
				}
			}
			
		});
		applybutton.setEnabled(!isNew);
		
		donebutton = new JButton("Done");
		donebutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				applybutton.getActionListeners()[0].actionPerformed(arg0);
				jdl.dispose();
		}});
		
		jp.add(applybutton);
		jp.add(donebutton);
		jdl.add(jp);

		jdl.validate();
		jdl.pack();
		jdl.setVisible(true);
	}
}
