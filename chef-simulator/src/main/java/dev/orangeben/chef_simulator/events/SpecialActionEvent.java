package dev.orangeben.chef_simulator.events;

import java.util.Map;

import dev.orangeben.chef_simulator.Game;
import dev.orangeben.chef_simulator.control.EventHandler;
import dev.orangeben.util.JSONTools;

public class SpecialActionEvent extends Event {

	private enum SpecialActionEventJsonKeys {
		ACTION_CODE, PAYLOAD;
	}
	public enum SpecialActionEventTypes {
		GET_MAP, SEND_DATA;
	}

	private SpecialActionEventTypes actioncode = null;
	private String payload = "";
	
	public SpecialActionEvent(SpecialActionEventTypes actioncode, String payload) {
		this.actioncode = actioncode;
		this.payload = payload;
	}
	
	public SpecialActionEvent(String json) {
		System.out.println(json);
		if(json.charAt(0) == '{' && json.charAt(json.length()-1) == '}') {

			json = json.substring(1, json.length() - 1);
			Map<String, String> js = JSONTools.splitJSON(json);
			for(String s : js.keySet()) {
				SpecialActionEventJsonKeys tdk = SpecialActionEventJsonKeys.valueOf(s);
				switch(tdk) {
				case ACTION_CODE: actioncode = SpecialActionEventTypes.valueOf(js.get(s)); break;
				case PAYLOAD: payload = js.get(s); break;
				default: break;
				}
			}
			if(actioncode != null) {
				return;
			}
		}
		throw new IllegalArgumentException("The ChatEvent could not be created from the provided JSON");
	}
	
	public SpecialActionEventTypes getActionCode() {
		return actioncode;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public void run() {
		switch(actioncode) {
		case GET_MAP: EventHandler.fireEvent(
				new SpecialActionEvent(SpecialActionEventTypes.SEND_DATA, JSONTools.unformatJSON(Game.getRoom().asJSON())));break;
		case SEND_DATA: if(Game.getDataLoader() != null) { Game.getDataLoader().processData(payload); } break;
		default: break;
		}
	}
	
	@Override
	public String asJSON() {
		String out = "{";
		out += "\"" + SpecialActionEventJsonKeys.ACTION_CODE + "\":\"" + actioncode + "\",";
		out += "\"" + SpecialActionEventJsonKeys.PAYLOAD + "\":\"" + payload + "\"";
		return super.asJSON(EventTypes.SPECIAL_ACTION, out + "}");
	}
	
	@Override
	public String toString() {
		return "SpecialActionEvent[" + actioncode + " sent at at " + payload + "]";
	}
}
