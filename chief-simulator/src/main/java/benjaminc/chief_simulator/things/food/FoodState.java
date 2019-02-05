package benjaminc.chief_simulator.things.food;

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
}
