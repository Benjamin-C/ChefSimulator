package dev.benjaminc.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
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
		// Disable system prints during load
		PrintStream out = System.out;
		System.setOut(new PrintStream(new OutputStream() { @Override public void write(int b) throws IOException { } }));
		GraphicalLoader.loadCache("assets/chefgraphics/textures");
		// Re-enable system prints
		System.setOut(out);
		
		Map<String, Texture> c = GraphicalLoader.getCache();
		
		String obj = loadFile("models/cube.obj");
		String mtl = loadFile("models/cube.mtl");
		
		System.out.println("Generating textures ... ");
		for(String key : c.keySet()) {
			long start = System.currentTimeMillis();
			Texture t = c.get(key);
			
			String cePath = t.getFilename();
			
			String folder = cePath.substring(0, cePath.indexOf('/'));
			String name = cePath.substring(cePath.indexOf('/')+1, cePath.lastIndexOf('.'));
			
			System.out.print(cePath);
			
			for(FoodState fs : FoodState.values()) {
				String state = fs.toString().toLowerCase();
				
				if(t.get(fs) != null) {
					try {
				      int width = 64, height = 64;
	
				      // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
				      // into integer pixels
				      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	
				      Graphics2D g = bi.createGraphics();
				      g.setColor(Color.BLACK);
				      g.fillRect(0, 0, width, height);
				      
				      GraphicalDrawer gd = new GraphicalDrawer(g);
				      
				      gd.drawTexture(t.get(fs), 0, 0, width, height, cePath);
				      
				      
				      String fn =  name + "-" + state;
				      String fp = "assets/carrotgraphics/textures/" + folder + "/" + name + "/" + state;
				      String cfn = fp + "/" + fn;
				      
				      File dir = new File(fp);
				      dir.mkdirs();
				      
				      ImageIO.write(bi, "PNG", new File(cfn + ".png"));
				      
				      PrintWriter pwobj = new PrintWriter(new File(cfn + ".obj"));
				      pwobj.write(obj.replace("cube", fn));
				      pwobj.flush();
				      pwobj.close();
				      
				      PrintWriter pwmtl = new PrintWriter(new File(cfn + ".mtl"));
				      pwmtl.write(mtl.replace("cube", fn));
				      pwmtl.flush();
				      pwmtl.close();
				      
				    } catch (IOException ie) {
				      ie.printStackTrace();
				    }
				}
			}
			long end = System.currentTimeMillis();
			long dur = end-start;
			System.out.println(" [" + dur + " ms]");
		}
		
		System.out.println("Done generating textures");
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
