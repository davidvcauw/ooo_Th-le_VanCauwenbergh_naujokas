package model.domain.feedbackStrategys.scoreCalculations;

public class MinusWhenEmptyCalculationStrategy extends ScoreCalculationTemplate implements ScoreCalculationStrategy {
	@Override
	public int calculateScore(int asked, int correct, int answered) {
		correct -= (answered-correct);//remove points for wrong questions
		correct -= (asked-answered);//remove points for empty questions
		return correct;
	}

	@Override
	public String getDescription() {
		return "- Correct answers give 1 point\n- Wrong/Empty answers give -1 point";
	}
	
	@Override
	public String toString() {
		return "minusWhenEmpty";
	}
}
