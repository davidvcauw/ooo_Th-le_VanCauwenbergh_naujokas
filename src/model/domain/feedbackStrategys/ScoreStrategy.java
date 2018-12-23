package model.domain.feedbackStrategys;

import java.util.ArrayList;
import java.util.List;

import model.domain.feedbackStrategys.scoreCalculations.MinusWhenWrongCalculationStrategy;
import model.domain.feedbackStrategys.scoreCalculations.NormalCalculationStrategy;
import model.domain.feedbackStrategys.scoreCalculations.ScoreCalculationStrategy;
import model.domain.feedbackStrategys.scoreCalculations.ScoreCalculationStrategyFactory;
import model.domain.feedbackStrategys.scoreCalculations.ScoreCalculationTypes;

public class ScoreStrategy implements FeedbackStrategy {
	
	private List<String> results = new ArrayList<String>();
	private boolean hasBeenDone = false;
	private ScoreCalculationStrategy calculation = new NormalCalculationStrategy();

	@Override
	public void setFeedback(List<String> f) {
		this.results = f;		
	}
	
	public void setHasBeenDone(boolean b) {
		hasBeenDone = b;
	}
	
	private void setScoreCalculationStrategy(ScoreCalculationStrategy strategy) {
		if (strategy == null) throw new IllegalArgumentException("calculation strategy can't be null!");
		this.calculation = strategy;
	}
	
	public ScoreCalculationStrategy setScoreCalculationStrategy(String strategy) {
		if (strategy == null || strategy.trim().isEmpty()) throw new IllegalArgumentException("Strategy can't be null");
		try {
			ScoreCalculationTypes calcType = ScoreCalculationTypes.valueOf(strategy);
			ScoreCalculationStrategy calc = ScoreCalculationStrategyFactory.createStrategy(calcType.getClassName());
			this.setScoreCalculationStrategy(calc);
			return calc;
		} catch (Exception e) {
			throw new IllegalArgumentException("Calculation strategy '" + strategy + "' not found");
		}
	}
	
	public List<String> getFeedbackList() {
		return results;
	}
	
	public boolean hasBeenDone() {
		return hasBeenDone;
	}
	
	public void reset() {
		this.results = new ArrayList<String>();
		setHasBeenDone(false);
	}
	
	@Override
	public boolean isFlawless() {
		if (this.results.isEmpty()) return false;
		else {
			try {
				int totalAsked = 0;
				int totalCorrect = 0;
				for (String r : results) {
					String[] rString = r.split("-");
					if (rString[0].equals("empty")) {
						return false;
					} else {
						totalAsked+=Integer.parseInt(rString[1]);
						totalCorrect+=Integer.parseInt(rString[2]);
					}
				}
				return totalAsked == totalCorrect;
			} catch (Exception ex) {
				return false;
			}
			
		}
	}

	@Override
	public String getFeedback() {
		return calculation.parseFeedback(this.results);
	}
	
	@Override
	public String toString() {
		return results.isEmpty()?"":"score--"+this.calculation+"--"+results.toString();
	}

	public String getCalcStrategy() {
		return this.calculation.toString();
	}
	
	public ScoreCalculationStrategy getCalcStrategyObj() {
		return this.calculation;
	}
	
}
