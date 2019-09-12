package benjaminc.chief_simulator.data;

public enum FoodState {
	RAW(0),
	COOKED(1),
	CHOPPED(2),
	CHOPPED_COOKED(3);
	
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
		default: return null;
		}
	}
}
