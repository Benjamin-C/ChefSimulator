package benjaminc.chief_simulator;

public class Score {
	
	protected int score;
	
	public Score() {
		this(0);
	}
	public Score(int score) {
		this.score = score;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void addScore(int num) {
		score = score + num;
	}
	public void removeScore(int num) {
		score = score - num;
	}
	
}
