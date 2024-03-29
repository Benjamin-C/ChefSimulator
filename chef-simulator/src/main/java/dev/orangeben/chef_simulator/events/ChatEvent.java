package dev.orangeben.chef_simulator.events;

import java.util.Map;

import dev.orangeben.chef_simulator.Game;
import dev.orangeben.util.JSONTools;

public class ChatEvent extends Event {

	private enum ChatEventJsonKeys {
		MESSAGE, TIME;
	}

	private String message = "";
	private double time = -1;
	
	public ChatEvent(String message, double frame) {
		this.message = replaceToStorage(message);
		this.time = frame;
	}
	
	public ChatEvent(String json) {
		System.out.println("ChatJSON: " + json);
		if(json.charAt(0) == '{' && json.charAt(json.length()-1) == '}') {

			json = json.substring(1, json.length() - 1);
			Map<String, String> js = JSONTools.splitJSON(json);
			for(String s : js.keySet()) {
				ChatEventJsonKeys tdk = ChatEventJsonKeys.valueOf(s);
				switch(tdk) {
				case TIME: try { time = Double.parseDouble(js.get(s)); } catch(NumberFormatException e) {} break;
				case MESSAGE: message = js.get(s); break;
				default: break;
				}
			}
			if(time != -1 && !message.equals("")) {
				return;
			}
		}
		throw new IllegalArgumentException("The ChatEvent could not be created from the provided JSON");
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) { 
		this.message = message;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double frame) {
		this.time = frame;
	}
	public void run() {
		Game.consoleInput(replaceToDisplay(message));
	}
	
	private String replaceToStorage(String msg) {
		return msg.replace("\n", "\\n").replace(" ", "\\s");
	}
	private String replaceToDisplay(String msg) {
		return msg.replace("\\n", "\n").replace("\\s", " ");
	}
	
	@Override
	public String asJSON() {
		String out = "{";
		out += "\"" + ChatEventJsonKeys.MESSAGE + "\":\"" + message + "\",";
		out += "\"" + ChatEventJsonKeys.TIME + "\":\"" + time + "\"";
		return super.asJSON(EventTypes.CHAT_EVENT, out + "}");
	}
	
	@Override
	public String toString() {
		return "ChatEvent[" + message + " sent at at " + time + "]";
	}
}
