package benjaminc.chief_simulator;

import benjaminc.util.Logger;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class App {
	
	static Logger logger;
	
    @SuppressWarnings("unused")
	public static void main( String[] args ) {
    	doit();
    }
    public void controllerTest(String agrs[]) {
    	while (true) {
			/* Get the available controllers */
			Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
			if (controllers.length == 0) {
				System.out.println("Found no controllers.");
				System.exit(0);
			}

			for (int i = 0; i < controllers.length; i++) {
				/* Remember to poll each one */
				controllers[i].poll();

				/* Get the controllers event queue */
				EventQueue queue = controllers[i].getEventQueue();

				/* Create an event object for the underlying plugin to populate */
				Event event = new Event();

				/* For each object in the queue */
				while (queue.getNextEvent(event)) {

					/*
					 * Create a string buffer and put in it, the controller name,
					 * the time stamp of the event, the name of the component
					 * that changed and the new value.
					 * 
					 * Note that the timestamp is a relative thing, not
					 * absolute, we can tell what order events happened in
					 * across controllers this way. We can not use it to tell
					 * exactly *when* an event happened just the order.
					 */
					StringBuffer buffer = new StringBuffer(controllers[i]
							.getName());
					buffer.append(" at ");
					buffer.append(event.getNanos()).append(", ");
					Component comp = event.getComponent();
					buffer.append(comp.getName()).append(" changed to ");
					float value = event.getValue();

					/*
					 * Check the type of the component and display an
					 * appropriate value
					 */
					if (comp.isAnalog()) {
						buffer.append(value);
					} else {
						if (value == 1.0f) {
							buffer.append("On");
						} else {
							buffer.append("Off");
						}
					}
					System.out.println(buffer.toString());
				}
			}

			/*
			 * Sleep for 20 milliseconds, in here only so the example doesn't
			 * thrash the system.
			 */
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    }
    public static void doit() {
    	System.out.println( "Hello World!" );
    	logger = new Logger();
 		logger.start();
 		System.out.println("I am logging now");
 		new Starter();
    }
}
