package benjaminc.chief_simulator;

import benjaminc.chief_simulator.things.Thing;

public class Objective {

	protected Thing target;
	protected int points;
	
	public Objective(Thing tgt, int pts) {
		target = tgt;
		points = pts;
	}
	
	public boolean isMet(Thing t) {
		if(target.sameAs(t)) {
			return true;
		} else {
			return false;
		}
	}
}
