package model.domain.feedbackStrategys.scoreCalculations;

import java.util.List;

public interface ScoreCalculationStrategy {
	public String parseFeedback(List<String> results);
	public String getDescription();
}
