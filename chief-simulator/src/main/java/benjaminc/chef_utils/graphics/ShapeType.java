package benjaminc.chef_utils.graphics;

public enum ShapeType {
SOLID_RECTANTLE,
EMPTY_RECTANGLE,
SOLID_ELIPSE,
EMPTY_ECLIPSE,
LINE;
	
	public static ShapeType getShapeTypeFromString(String s) {
		switch(s.toUpperCase()) {
		case "SOLID_RECTANTLE": return SOLID_RECTANTLE;
		case "EMPTY_RECTANGLE": return EMPTY_RECTANGLE;
		case "SOLID_ELIPSE": return SOLID_ELIPSE;
		case "EMPTY_ECLIPSE": return EMPTY_ECLIPSE;
		case "LINE": return LINE;
		}
		return null;
	}
}
