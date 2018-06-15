package benjaminc.chief_simulator;

import benjaminc.util.Logger;

public class App {
	
	static Logger logger;
	
    @SuppressWarnings("unused")
	public static void main( String[] args ) {
    	System.out.println( "Hello World!" );
    	logger = new Logger();
 		logger.start();
 		System.out.println("I am logging now");
 		Game g = new Game();
    }
}
