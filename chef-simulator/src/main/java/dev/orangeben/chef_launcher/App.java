package dev.orangeben.chef_launcher;

import dev.orangeben.BenTCP.TCPTesterMain;
import dev.orangeben.chef_simulator.ChefSimulatorControl;
import dev.orangeben.chef_simulator.Game;
import dev.orangeben.chef_simulator.Objective;
import dev.orangeben.chef_simulator.data.location.Location2d;
import dev.orangeben.chef_simulator.rooms.Room;
import dev.orangeben.chef_simulator.things.food.Potato;
import dev.orangeben.chef_simulator.things.food.Tomato;
import dev.orangeben.chef_simulator.things.tools.Dishbin;
import dev.orangeben.util.Logger;

@SuppressWarnings("unused")
public class App {
	
	static Logger logger;
	
	/**
	 * The main method for the program
	 * @param args the array of {@link String} so that Java can find the main method
	 */
	public static void main( String[] args ) {
		System.out.println( "Hello World!" );
    	logger = new Logger();
 		logger.start();
 		System.out.println("I am logging now");
 		
 		boolean runGame = true;
 		boolean useStarter = true;
 		
 		if(runGame) {
 			doit(useStarter);
 		} else {
 			doThat();
 		}
	}
	
	public static void doit(boolean useStarter) {
		if(!useStarter) {
			Room r = new Room(16, 16, null);
			r.addThing(new Potato(), new Location2d(5, 5));
			Game.setupGame(40, 30, false, true);
			Game.playJSONMap(r.asJSON());
		} else {
 			new Starter();
		}
    }
	
	public static void doThat() {
		
		Dishbin p = new Dishbin(new Potato());
		p.addItem(new Tomato());
//		String dta = p.asJSON();
//		Thing q = BasicThing.makeThingFromJSON(dta);
		Objective o = new Objective(p, 5);
		String odata = o.asJSON();
		Objective oo = new Objective(odata);
		
		System.out.println("\n\n\n\n\n\n\n\nSTART");
		System.out.println(odata);
		System.out.println(oo.asJSON());
    }
}
