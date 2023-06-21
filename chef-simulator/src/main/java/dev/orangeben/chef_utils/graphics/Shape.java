package dev.orangeben.chef_utils.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.util.UUID;

public class Shape {

	// Instance Methods
	protected float x = 5;
	protected float y = 5;
	protected float w = 10;
	protected float h = 10;
	protected float c = 0;
	protected int r = 255;
	protected int g = 0;
	protected int b = 0;
	
	/** X loc */
	protected int xs = 0;
	/** Y loc */
	protected int ys = 0;
	/** X pos */
	protected int ws = 0;
	/** Y pos */
	protected int hs = 0;
	/** shape size */
	protected int ss = 1;
	/** X loc */
	protected Polygon triangle;
	
	protected int owi = 0;
	protected int ohe = 0;
	protected int oox = 0;
	protected int ooy = 0;
	protected Graphics ogr = null;
	
	protected ShapeType shp;
	
	protected boolean recalc = false;
	
	protected UUID id;
	
	public Shape(ShapeType shp, float x, float y, float w, float h, float c, int r, int g, int b) {
		super();
		this.shp = shp;
		triangle = new Polygon();
		setSize(x, y, w, h, c);
		setColor(r, g, b);
		id = UUID.randomUUID();
		
	}

	public void draw(int wi, int he, Graphics gr) {
		draw(wi, he, 0, 0, gr);
	}
	public void draw(int wi, int he, int ox, int oy, Graphics gr) {
		draw(wi, he, ox, oy, gr, 0);
	}
	public void draw(int wi, int he, int ox, int oy, Graphics gr, int dir) {
		gr.setColor(new Color(r, g, b));
		
		if(!(wi == owi && he == ohe && ox == oox && oy == ooy && gr == ogr) || recalc) {
			switch(dir) { // 0=u, 1=r, 2=d, 3=l
			case 0: { xs = (int) Math.round(x*wi) + ox; ys = (int) Math.round(y*he) + oy; ws = (int) Math.round(w*wi); hs = (int) Math.round(h*he); } break;
			case 1: { xs = (int) Math.round((1-y-h)*wi) + ox; ys = (int) Math.round((1-x-w)*he) + oy; ws = (int) Math.round(h*wi); hs = (int) Math.round(w*he); } break;
			case 2: { xs = (int) Math.round((1-x-w)*wi) + ox; ys = (int) Math.round((1-y-h)*he) + oy; ws = (int) Math.round(w*wi); hs = (int) Math.round(h*he); } break;
			case 3: { xs = (int) Math.round(y*wi) + ox; ys = (int) Math.round(x*he) + oy; ws = (int) Math.round(h*wi); hs = (int) Math.round(w*he); } break;
			}
			
			ss = (int) (wi*c);
			if(ss < 1) {
				ss = 1;
			}
			
			switch(shp) {
			case SOLID_RECTANTLE: {
				ss = (int) (c * Math.min(ws, hs));
			} break;
			case EMPTY_TRIANGLE_H: // or next case
			case SOLID_TRIANGLE_H: {
				triangle.reset();
				triangle.addPoint(xs, ys);
				triangle.addPoint(xs, ys + hs);
				triangle.addPoint(xs + ws, ys + (int) (hs*c));
			} break;

			case EMPTY_TRIANGLE_V: // or next case
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
		case EMPTY_ECLIPSE: prepSize(gr, ss); gr.drawOval(xs, ys, ws, hs); resetSize(gr); break;
		case SOLID_ELIPSE: gr.fillOval(xs, ys, ws, hs); break;
		
		case EMPTY_RECTANGLE: prepSize(gr, ss); gr.drawRect(xs, ys, ws, hs); resetSize(gr);  break;
		case SOLID_RECTANTLE: gr.fillRoundRect(xs, ys, ws, hs, ss, ss); break;
		
		case EMPTY_TRIANGLE_V: prepSize(gr, ss); gr.drawPolygon(triangle); resetSize(gr); break;
		case SOLID_TRIANGLE_V: gr.fillPolygon(triangle); break;
		
		case EMPTY_TRIANGLE_H: prepSize(gr, ss); gr.drawPolygon(triangle); resetSize(gr); break;
		case SOLID_TRIANGLE_H: gr.fillPolygon(triangle); break;
		
		case LINE: prepSize(gr, ss); gr.drawLine(xs, ys, xs+ws, ys+hs); resetSize(gr); break;
		}
	}
	
	private Graphics2D g2;
	private Stroke sd;
	private void prepSize(Graphics gr, float ss) {
		g2 = (Graphics2D) gr;
		sd = g2.getStroke();
		g2.setStroke(new BasicStroke(ss));
	}
	private void resetSize(Graphics gr) {
		if(g2 != null) {
			g2.setStroke(sd);
		}
	}
	
	
	public void setSize(float x, float y, float w, float h, float c) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.c = c;
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
	public float getC() {
		return c;
	}
	public void setC(float c) {
		this.c = c;
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
	
	@Override
	public Shape clone() {
		return new Shape(shp, x, y, w, h, c, r, g, b);
	}
	
	public String toString() {
		return "Shape[shp=" + shp + ",x=" + x + ",y=" + y + ",w=" + w + ",h=" + h + ",c=" + c + ",r=" + r + ",g=" + g + ",b=" + b + "]";
	}
}
