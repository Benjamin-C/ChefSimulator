package benjaminc.chef_simulator.events;

public enum EventTypes {
	THING_ADD_EVENT(ThingAddEvent.class),
	THING_CHANGE_EVENT(ThingChangeEvent.class),
	THING_REMOVE_EVENT(ThingMoveEvent.class),
	THING_MOVE_EVENT(ThingRemoveEvent.class),
	CHEF_MOVE_EVENT(ChefMoveEvent.class),
	CHAT_EVENT(ChatEvent.class),
	SPECIAL_ACTION(SpecialActionEvent.class);
	
	Class<?> mclass;
	
	EventTypes(Class<?> mclass) {
		this.mclass = mclass;
	}
	
	public Class<?> getMyClass() {
		return mclass;
	}
}
