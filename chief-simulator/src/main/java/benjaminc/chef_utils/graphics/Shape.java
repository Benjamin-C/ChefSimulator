package benjaminc.chef_utils.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.UUID;

public class Shape {

	// Instance Methods
	protected float x = 5;
	protected float y = 5;
	protected float w = 10;
	protected float h = 10;
	protected int r = 255;
	protected int g = 0;
	protected int b = 0;
	
	protected int xs = 0;
	protected int ys = 0;
	protected int ws = 0;
	protected int hs = 0;
	protected Polygon triangle;
	
	protected int owi = 0;
	protected int ohe = 0;
	protected int oox = 0;
	protected int ooy = 0;
	protected Graphics ogr = null;
	
	protected ShapeType shp;
	
	protected boolean recalc = false;
	
	protected UUID id;
	
	public Shape(ShapeType shp, float x, float y, float w, float h, int r, int g, int b) {
		super();
		this.shp = shp;
		triangle = new Polygon();
		setSize(x, y, w, h);
		setColor(r, g, b);
		id = UUID.randomUUID();
		
	}

	public void draw(int wi, int he, Graphics gr) {
		draw(wi, he, 0, 0, gr);
	}
	public void draw(int wi, int he, int ox, int oy, Graphics gr) {
		gr.setColor(new Color(r, g, b));
		
		if(!(wi == owi && he == ohe && ox == oox && oy == ooy && gr == ogr) || recalc) {
			xs = (int) (x*wi) + ox;
			ys = (int) (y*he) + oy;
			ws = (int) (w*wi);
			hs = (int) (h*he);
			
			switch(shp) {
			case EMPTY_TRIANGLE_H: // or next line
			case SOLID_TRIANGLE_H: {
				triangle.reset();
				triangle.addPoint(xs, ys);
				triangle.addPoint(xs, ys + hs);
				triangle.addPoint(xs + ws, ys + (hs/2));
			} break;

			case EMPTY_TRIANGLE_V: // or next line
			case SOLID_TRIANGLE_V: {
				triangle.reset();
				triangle.addPoint(xs, ys);
				triangle.addPoint(xs + ws, ys);
				triangle.addPoint(xs + (ws/2), ys + hs);
			} break;
			default: break;
			}
			if(shp == ShapeType.EMPTY_TRIANGLE_V || shp == ShapeType.SOLID_TRIANGLE_V) {
				
			}
		}
		
		switch(shp) {
		case EMPTY_ECLIPSE: gr.drawOval(xs, ys, ws, hs); break;
		case EMPTY_RECTANGLE:gr.drawRect(xs, ys, ws, hs);  break;
		case LINE: gr.drawLine(xs, ys, xs+ws, ys+hs); break;
		case SOLID_ELIPSE: gr.fillOval(xs, ys, ws, hs);  break;
		case SOLID_RECTANTLE: gr.fillRect(xs, ys, ws, hs); break;
		case SOLID_TRIANGLE_V: gr.fillPolygon(triangle); break;
		case EMPTY_TRIANGLE_V: gr.drawPolygon(triangle); break;
		case SOLID_TRIANGLE_H: gr.fillPolygon(triangle); break;
		case EMPTY_TRIANGLE_H: gr.drawPolygon(triangle); break;
		}
	}
	
	public void setSize(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		recalc = true;
	}
	public void setColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
		recalc = true;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getW() {
		return w;
	}
	public void setW(float w) {
		this.w = w;
	}
	public float getH() {
		return h;
	}
	public void setH(float h) {
		this.h = h;
	}
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
	}
	public int getG() {
		return g;
	}
	public void setG(int g) {
		this.g = g;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
	public UUID getID() {
		return id;
	}
	public ShapeType getShapeType() {
		return shp;
	}
	public boolean equals(Shape test) {
		return test.getID() == id;
	}
	
	public Shape clone() {
		return new Shape(shp, x, y, w, h, r, g, b);
	}
	
	public String toString() {
		return "Shape[shp=" + shp + ",x=" + x + ",y=" + y + ",w=" + w + ",h=" + h + ",r=" + r + ",g=" + g + ",b=" + b + "]";
	}
	
}
