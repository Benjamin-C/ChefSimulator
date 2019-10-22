package benjaminc.chef_textures.graphicdesigner;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import benjaminc.chef_textures.shapelist.ShapeList;

public class GraphicsPanel extends JPanel {

	/** Keep eclipse happy */
	private static final long serialVersionUID = 1893595827834590138L;
	
	private int width = 512;
	private int height = 512;
	
	//private List<Shape> shapes;
	private ShapeList shapes;
	
	public GraphicsPanel(ShapeList shapes) {
		super();
		this.shapes = shapes;
		this.shapes.setOnUpdate(new Runnable() {
			@Override public void run() { repaint(); }
		});
	}
	
	@Override
	public void paint(Graphics gr) {
		int sqsz = 16;
		boolean color = true;
		for(int i = 0; i < width; i = i + sqsz) {
			for(int j = 0; j < height; j = j + sqsz) {
				if(color) {
					gr.setColor(new Color(200, 200, 200));
				} else {
					gr.setColor(new Color(255, 255, 255));
				}
				color = !color;
				gr.fillRect(i, j, sqsz, sqsz);
			}
			color = !color;
		}
		for(int i = shapes.shapeCount()-1; i >= 0; i--) {
			shapes.getShapeListElements().get(i).getShape().draw(width, height, gr);
		}
	}
	
//	public void createEditDialog() {
//		sh.createEditDialog(new Runnable() { @Override public void run() { repaint(); } });
//	}
}
