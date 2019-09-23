package benjaminc.chef_utils.graphics;

public enum ShapeType {
SOLID_RECTANTLE,
EMPTY_RECTANGLE,
SOLID_ELIPSE,
EMPTY_ECLIPSE,
SOLID_TRIANGLE_H,
EMPTY_TRIANGLE_H,
SOLID_TRIANGLE_V,
EMPTY_TRIANGLE_V,
LINE;
	
	public static ShapeType getShapeTypeFromString(String s) {
		switch(s.toUpperCase()) {
		case "SOLID_RECTANTLE": return SOLID_RECTANTLE;
		case "EMPTY_RECTANGLE": return EMPTY_RECTANGLE;
		case "SOLID_ELIPSE": return SOLID_ELIPSE;
		case "EMPTY_ECLIPSE": return EMPTY_ECLIPSE;
		case "SOLID_TRIANGLE": return SOLID_TRIANGLE_H;
		case "EMPTY_TRIANGLE": return EMPTY_TRIANGLE_H;
		case "SOLID_TRIANGLE_V": return SOLID_TRIANGLE_V;
		case "EMPTY_TRIANGLE_V": return EMPTY_TRIANGLE_V;
		case "LINE": return LINE;
		}
		return null;
	}
}
