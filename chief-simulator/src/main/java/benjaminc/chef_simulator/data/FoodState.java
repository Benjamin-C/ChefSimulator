package benjaminc.chef_simulator.data;

public enum FoodState {
	NULL(0),
	RAW(1),
	COOKED(2),
	CHOPPED(3),
	CHOPPED_COOKED(4);
	
	protected int val;
	
	FoodState(int val) {
		this.val = val;
	}
	public int getVal() {
		return val;
	}
	public FoodState clone(FoodState toClone) {
		switch(toClone) {
		case CHOPPED: return FoodState.CHOPPED;
		case CHOPPED_COOKED: return FoodState.CHOPPED_COOKED;
		case COOKED: return FoodState.COOKED;
		case RAW: return FoodState.RAW;
		case NULL: return FoodState.NULL;
		default: return null;
		}
	}
}
