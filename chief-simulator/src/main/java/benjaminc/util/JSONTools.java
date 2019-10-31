package benjaminc.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Benjamin-C
 *
 */
public class JSONTools {
	
	/**
	 * Splits JSON into its root key:value pairs
	 * @param json
	 * @return
	 */
	public static Map<String, String> splitJSON(String json) {
		Map<String, String> s = new HashMap<String, String>();
		
		json = json.replace(" ", "");
		
		int curl = 0;
		int squ = 0;
		int startdata = 0;
		for(int i = 0; i < json.length(); i++) {
			switch(json.charAt(i)) {
			case '{': { curl++; } break;
			case '}': { curl--; } break;
			case '[': { squ++; } break;
			case ']': { squ--; } break;
			case ',': { 
				if(curl==0 && squ==0) {
					String p = json.substring(startdata, i);
					addKVPair(s, p);
					startdata = i + 1;
				}
			}
			}
		}
		
		addKVPair(s, json.substring(startdata, json.length()));
		
		return s;
	}
	
	/**
	 * Removes the char from the beginning and end of the {@link String}
	 * Does nothing if the char is not present at both the beginning and end of the {@link String}, and returns the {@link String}
	 * @param s the {@link String} to peel from
	 * @param c the {@link String} to peel. Will only peel 1st char
	 * @return the peeled {@link String}
	 */
	public static String peelChar(String s, String c) {
		return peelChar(s, c.charAt(0));
	}
	
	/**
	 * Removes the char from the beginning and end of the {@link String}
	 * Does nothing if the char is not present at both the beginning and end of the {@link String}, and returns the {@link String}
	 * @param s the {@link String} to peel from
	 * @param c the {@link Character} to peel
	 * @return the peeled {@link String}
	 */
	public static String peelChar(String s, char c) {
		char ec = c;
		
		switch(c) {
		case '{': ec = '}'; break;
		case '[': ec = ']'; break;
		case '(': ec = ')'; break;
		case '<': ec = '>'; break;
		}
		if(s.charAt(0) == c && s.charAt(s.length()-1) == ec) {
			return s.substring(1,s.length()-1);
		} else {
			return s;
		}
	}
	
	private static void addKVPair(Map<String, String> map, String pair) {
		String k = peelChar(pair.substring(0, pair.indexOf(":")), '"');
		String v = peelChar(pair.substring(pair.indexOf(":")+1, pair.length()), '"');
		map.put(k, v);
	}
}
