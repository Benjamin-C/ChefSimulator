package benjaminc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Benjamin-C
 *
 */
public class JSONTools {
	
	public enum JSONType {
		ARRAY,
		OBJECT,
		ELEMENT,
		NONE;
	}
	
	/**
	 * Guesses the {@link JSONType} of a JSON {@link String}
	 * @param json the JSON {@link String} to guess
	 * @return the guessed {@link JSONType}
	 */
	public static JSONType guessType(String json) {
		switch(json.charAt(0)) {
		case '\"': return JSONType.ELEMENT;
		case '{': return JSONType.OBJECT;
		case '[': return JSONType.ARRAY;
		default: return JSONType.ELEMENT;
		}
	}
	
	/**
	 * Removes formatting from json {@link String}
	 * @param formattedJSON the formatted JSON {@link String}
	 * @return the {@link String} of unformatted JSON
	 */
	public static String unformatJSON(String formattedJSON) {
		return formattedJSON.replace(" ","").replace("\t","").replace("\n","");
	}
	
	/**
	 * Splits a JSON array into its parts
	 * @param json the JSON array {@link String}
	 * @return the {@link List} of {@link String} of parts
	 */
	public static List<String> splitJSONArray(String json) {
		if(json != null && json.length() > 0) {
			List<String> s = new ArrayList<String>();
			
			json = peelChar(unformatJSON(json), "[");
			
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
						s.add(json.substring(startdata, i));
						startdata = i + 1;
					}
				}
				}
			}
			
			s.add(json.substring(startdata, json.length()));
			
			return s;
		}
		return null;
	}
	/**
	 * Splits JSON into its root key:value pairs
	 * @param json the JSON {@link String} to split
	 * @return the {@link Map} of {@link String} to {@link String} representation
	 */
	public static Map<String, String> splitJSON(String json) {
		if(json != null && json.length() > 0) {
			Map<String, String> s = new HashMap<String, String>();
			
			json = unformatJSON(json);
			json = peelChar(json, '{');
			
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
		return null;
	}
	
	/**
	 * Removes the char from the beginning and end of the {@link String}
	 * Does nothing if the char is not present at both the beginning and end of the {@link String}, and returns the {@link String}
	 * @param s the {@link String} to peel from
	 * @param c the {@link String} to peel. Will only peel 1st char
	 * @return the peeled {@link String}
	 */
	public static String peelChar(String s, String c) {
		if(c != null && c.length() > 0) {
			return peelChar(s, c.charAt(0));
		}
		return s;
	}
	
	/**
	 * Removes the char from the beginning and end of the {@link String}
	 * Does nothing if the char is not present at both the beginning and end of the {@link String}, and returns the {@link String}
	 * @param s the {@link String} to peel from
	 * @param c the {@link Character} to peel
	 * @return the peeled {@link String}
	 */
	public static String peelChar(String s, char c) {
		if(s != null && s.length() > 0) {
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
		return "";
	}
	
	private static void addKVPair(Map<String, String> map, String pair) {
		String k = peelChar(pair.substring(0, pair.indexOf(":")), '"');
		String v = peelChar(pair.substring(pair.indexOf(":")+1, pair.length()), '"');
		map.put(k, v);
	}
}
