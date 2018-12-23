package model.domain.feedbackStrategys.scoreCalculations;

public class MinusWhenWrongCalculationStrategy extends ScoreCalculationTemplate implements ScoreCalculationStrategy {
	@Override
	public int calculateScore(int asked, int correct, int answered) {
		correct -= (answered-correct);
		return correct;
	}
	
	@Override
	public String getDescription() {
		return "- Correct answers give 1 point\n- Wrong answers give -1 point\n- Empty answers give 0 points";
	}
	
	@Override
	public String toString() {
		return "minusWhenWrong";
	}
}
