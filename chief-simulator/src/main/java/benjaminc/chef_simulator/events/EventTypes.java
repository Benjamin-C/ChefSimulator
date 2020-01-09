package benjaminc.chef_simulator.events;

public enum EventTypes {
	GS_CHANGE_EVENT(GSChangeEvent.class),
	CHEF_MOVE_EVENT(ChefMoveEvent.class),
	CHAT_EVENT();
	
	Class<?> mclass;
	
	EventTypes(Class<?> mclass) {
		this.mclass = mclass;
	}
	
	public Class<?> getMyClass() {
		return mclass;
	}
}
