package dev.benjaminc.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import dev.benjaminc.chef_simulator.chef_graphics.GraphicalDrawer;
import dev.benjaminc.chef_simulator.chef_graphics.GraphicalLoader;
import dev.benjaminc.chef_simulator.data.FoodState;
import dev.benjaminc.chef_utils.graphics.Texture;

public class ImageSaver {
	
	public static void main(String[] args) {
		GraphicalLoader.loadCache("assets/chefgraphics/textures");
		
		Map<String, Texture> c = GraphicalLoader.getCache();
		
		String obj = loadFile("models/cube.obj");
		String mtl = loadFile("models/cube.mtl");
		
		for(String s : c.keySet()) {
			String name = c.get(s).getFilename();
			
			System.out.println(name);
			
			try {
			      int width = 64, height = 64;

			      // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
			      // into integer pixels
			      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

			      Graphics2D g = bi.createGraphics();
			      g.setColor(Color.BLACK);
			      g.fillRect(0, 0, width, height);
			      
			      GraphicalDrawer gd = new GraphicalDrawer(g);
			      
			      gd.drawTexture(((Texture) c.get(s)).getList().get(FoodState.RAW), 0, 0, width, height, name);
			      
			      String fn = "assets/carrotgraphics/textures/" + name.substring(0, name.indexOf('.'));
			      ImageIO.write(bi, "PNG", new File(fn + ".png"));
			      
			      PrintWriter pwobj = new PrintWriter(new File(fn + ".obj"));
			      pwobj.write(obj.replace("cube", name.substring(name.indexOf("/")+1, name.indexOf("."))));
			      pwobj.flush();
			      pwobj.close();
			      
			      PrintWriter pwmtl = new PrintWriter(new File(fn + ".mtl"));
			      pwmtl.write(mtl.replace("cube", name.substring(name.indexOf("/")+1, name.indexOf("."))));
			      pwmtl.flush();
			      pwmtl.close();
			      
			    } catch (IOException ie) {
			      ie.printStackTrace();
			    }
		}
	}
	
	public static String loadFile(String name) {
		String s = "";
		Scanner f;
		try {
			f = new Scanner(new File(name));
			while(f.hasNextLine()) {
				s += f.nextLine() + "\n";
			}
			f.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

}
