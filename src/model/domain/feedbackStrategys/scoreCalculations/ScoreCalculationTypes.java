package model.domain.feedbackStrategys.scoreCalculations;

public enum ScoreCalculationTypes {
	normal("model.domain.feedbackStrategys.scoreCalculations.NormalCalculationStrategy"),
	minusWhenWrong("model.domain.feedbackStrategys.scoreCalculations.MinusWhenWrongCalculationStrategy"), 
	minusWhenEmpty("model.domain.feedbackStrategys.scoreCalculations.MinusWhenEmptyCalculationStrategy"); 
	//add more question types here;
	
	private final String className;
	
	ScoreCalculationTypes(String name) {
		className = name;
	}
	
	public String getClassName() {
		return className;
	}
}
