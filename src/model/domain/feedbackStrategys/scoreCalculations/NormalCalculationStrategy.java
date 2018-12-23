package model.domain.feedbackStrategys.scoreCalculations;

public class NormalCalculationStrategy extends ScoreCalculationTemplate implements ScoreCalculationStrategy {
	@Override
	public int calculateScore(int asked, int correct, int answered) {
		return correct;
	}

	@Override
	public String getDescription() {
		return "- Correct answers give 1 point\n- Wrong/Empty answers give 0 points";
	}
	
	@Override
	public String toString() {
		return "normal";
	}
}
