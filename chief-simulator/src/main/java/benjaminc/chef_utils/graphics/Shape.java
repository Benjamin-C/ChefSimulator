package benjaminc.chef_utils.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.UUID;

public class Shape {

	// Instance Methods
	private float x = 5;
	private float y = 5;
	private float w = 10;
	private float h = 10;
	private int r = 255;
	private int g = 0;
	private int b = 0;
	
	private ShapeType shp;
	
	private UUID id;
	
	public Shape(ShapeType shp, float x, float y, float w, float h, int r, int g, int b) {
		super();
		this.shp = shp;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.r = r;
		this.g = g;
		this.b = b;
		id = UUID.randomUUID();
	}

	public void draw(int wi, int he, Graphics gr) {
		draw(wi, he, 0, 0, gr);
	}
	public void draw(int wi, int he, int ox, int oy, Graphics gr) {
		gr.setColor(new Color(r, g, b));
		int xs = (int) (x*wi) + ox;
		int ys = (int) (y*he) + oy;
		int ws = (int) (w*wi);
		int hs = (int) (h*he);
		switch(shp) {
		case EMPTY_ECLIPSE: gr.drawOval(xs, ys, ws, hs); break;
		case EMPTY_RECTANGLE:gr.drawRect(xs, ys, ws, hs);  break;
		case LINE: gr.drawLine(xs, ys, ws, hs); break;
		case SOLID_ELIPSE: gr.fillOval(xs, ys, ws, hs);  break;
		case SOLID_RECTANTLE: gr.fillRect(xs, ys, ws, hs); break;
		}
	}
	
	public void setSize(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	public void setColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
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
