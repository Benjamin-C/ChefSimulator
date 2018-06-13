package benjaminc.chief_simulator;

import benjaminc.util.Logger;

/**
 * Hello world!
 *
 */
public class App {
	
	Logger logger;
    public static void main( String[] args ) {
    	System.out.println( "Hello World!" );
    	App a = new App();
	}
	
	public App() {
        // Setup logger
 		logger = new Logger();
 		logger.start();
    }
}
