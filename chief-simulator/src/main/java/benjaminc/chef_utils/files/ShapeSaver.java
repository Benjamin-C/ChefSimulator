package benjaminc.chef_utils.files;

import benjaminc.chef_utils.data.FoodState;
import benjaminc.chef_utils.graphics.Shape;
import benjaminc.chef_utils.graphics.Texture;

public class ShapeSaver {
	
	public ShapeSaver() {
		
	}

	public String save(Texture t) {
		String save = "";
		for(FoodState s : FoodState.values()) {
			save = save + "\n" + s + ":\n";
			if(t.get(s) != null) {
				for(Shape sh : t.get(s)) {
					save = save + getJsonFromShape(sh) + "\n";
				}
			}
		}
		return save;
	}
	public String getJsonFromShape(Shape s) {
		return "{\"" + ShapeDataKey.SHPAETYPE_KEY + "\":\"" + s.getShapeType() + "\","
				 + "\"" + ShapeDataKey.X_KEY + "\":\"" + s.getX() + "\","
				 + "\"" + ShapeDataKey.Y_KEY + "\":\"" + s.getY() + "\","
				 + "\"" + ShapeDataKey.W_KEY + "\":\"" + s.getW() + "\","
				 + "\"" + ShapeDataKey.H_KEY + "\":\"" + s.getH() + "\","
				 + "\"" + ShapeDataKey.C_KEY + "\":\"" + s.getC() + "\","
				 + "\"" + ShapeDataKey.R_KEY + "\":\"" + s.getR() + "\","
				 + "\"" + ShapeDataKey.G_KEY + "\":\"" + s.getG() + "\","
				 + "\"" + ShapeDataKey.B_KEY + "\":\"" + s.getB() + "\"}";
	}
}
