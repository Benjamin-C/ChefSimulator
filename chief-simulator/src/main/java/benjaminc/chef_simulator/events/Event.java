package benjaminc.chef_simulator.events;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import benjaminc.chef_simulator.data.Savable;
import benjaminc.util.JSONTools;

/**
 * Basic event
 * @author Benjamin-C
 *
 */
public abstract class Event implements Savable {
	
	private boolean canceled;
	
	/**
	 * Loads an {@link Event} from JSON. Returns null if the event could not be parsed.
	 * @param json 	the {@link String} JSON
	 * @return		the {@link Event}
	 */
	public static Event loadEventFromJSON(String json) {
		json = JSONTools.unformatJSON(json);
		if(json.charAt(0) == '{' && json.charAt(json.length()-1) == '}') {
			EventTypes type = null;
			String data = "";
			json = json.substring(1, json.length() - 1);
			Map<String, String> js = JSONTools.splitJSON(json);
			for(String s : js.keySet()) {
				switch(s) {
				case "EVENT_TYPE": type = EventTypes.valueOf(js.get(s)); break;
				case "DATA": data = js.get(s); break;
				}
			}
			if(type != null && data != "") {
				Object newobj = null;

				try {
					Class<?> clazz = type.getMyClass();
					Constructor<?> ctor = clazz.getConstructor(String.class);
					newobj = ctor.newInstance(data);
				} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException err) {
					err.printStackTrace();
				}
				
				if(newobj != null && newobj instanceof Event) {
					return (Event) newobj;
				} else {
					System.out.println("Something was null after reflection");
				}
			} else {
				System.out.println("Something was null so could not reflect");
			}
		}
		System.out.println("Malformed JSON");
		return null;
	}
	
	/**
	 * Sets weather or not the event should be canceled
	 * 
	 * @param cancel the boolean new cancel state
	 */
	public void setCanceled(boolean cancel) {
		canceled = cancel;
	}
	/**
	 * Gets weather or not the event was canceled
	 * 
	 * @return the boolean if canceled
	 */
	public boolean getCanceled() {
		return canceled;
	}
	
	protected String asJSON(EventTypes type, String eventJSON) {
		return "{\"EVENT_TYPE\":\"" + type + "\",\"DATA\":" + eventJSON + "}";
	}
	
	/**
	 * Runs the event
	 */
	public abstract void run();

}
