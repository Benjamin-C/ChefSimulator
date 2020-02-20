package dev.benjaminc.chef_textures.shapes;

import dev.benjaminc.chef_textures.dialog.CreationDialog;
import dev.benjaminc.chef_textures.dialog.CreationDialogDoneRunnable;
import dev.benjaminc.chef_utils.graphics.Shape;
import dev.benjaminc.chef_utils.graphics.ShapeType;

public class GraphicalShape extends Shape {
	
	public GraphicalShape(Shape s) {
		super(s.getShapeType(), s.getX(), s.getY(), s.getW(), s.getH(), s.getC(), s.getR(), s.getG(), s.getB());
	}
	public GraphicalShape(ShapeType shp, float x, float y, float w, float h, float c, int r, int g, int b) {
		super(shp, x, y, w, h, c, r, g, b);
		// TODO Auto-generated constructor stub
	}
	public void createEditDialog(Runnable onDone) {
		createEditDialog(false, "Change shape", onDone);
	}
	public void createEditDialog(boolean isNew, Runnable onDone) {
		createEditDialog(isNew, "Change shape", onDone);
	}
	public void createEditDialog(boolean isNew, String title, Runnable onDone) {
		String[] labels = {"X", "Y", "W", "H", "C", "R", "G", "B"};
		String[] text = new String[8]; // Change this line if you add/subtract elements from the dialog
		text[0] = Float.toString(x);
		text[1] = Float.toString(y);
		text[2] = Float.toString(w);
		text[3] = Float.toString(h);
		text[4] = Float.toString(c);
		text[5] = Integer.toString(r);
		text[6] = Integer.toString(g);
		text[7] = Integer.toString(b);
		
		new CreationDialog(title, new CreationDialogDoneRunnable() {
			@Override public void done(ShapeType sh, float[] vals) {
				shp = sh;
				setSize(vals[0], vals[1], vals[2], vals[3], vals[4]);
				setColor((int) vals[5], (int) vals[6], (int) vals[7]);
				if(onDone != null) {
					onDone.run();
				}
			}
		}, shp, labels, text, isNew);
	}
}
