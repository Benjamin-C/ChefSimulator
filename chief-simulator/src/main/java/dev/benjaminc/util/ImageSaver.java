package dev.benjaminc.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import dev.benjaminc.chef_simulator.chef_graphics.GraphicalDrawer;
import dev.benjaminc.chef_simulator.chef_graphics.GraphicalLoader;
import dev.benjaminc.chef_simulator.data.FoodState;
import dev.benjaminc.chef_utils.graphics.Texture;

public class ImageSaver {
	
	public static void main(String[] args) {
		GraphicalLoader.loadCache("assets/chefgraphics/textures");
		
		Map<String, Texture> c = GraphicalLoader.getCache();
		
		for(String s : c.keySet()) {
			String name = c.get(s).getFilename();
			System.out.println(name);
			
			try {
			      int width = 64, height = 64;

			      // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
			      // into integer pixels
			      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

			      Graphics2D g = bi.createGraphics();
			      
			      GraphicalDrawer gd = new GraphicalDrawer(g);
			      
			      gd.drawTexture(((Texture) c.get(s)).getList().get(FoodState.RAW), 0, 0, width, height, name);
			      
			      ImageIO.write(bi, "PNG", new File("assets/carrotgraphics/textures/" + name.substring(name.indexOf('.')) + ".png"));
			      
			    } catch (IOException ie) {
			      ie.printStackTrace();
			    }
		}
	}

}
