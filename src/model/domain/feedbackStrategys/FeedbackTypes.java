package model.domain.feedbackStrategys;

public enum FeedbackTypes {
	score("model.domain.feedbackStrategys.ScoreStrategy"),
	feedback("model.domain.feedbackStrategys.TextStrategy");
	//add more question types here;
	
	private final String className;
	
	FeedbackTypes(String name) {
		className = name;
	}
	
	public String getClassName() {
		return className;
	}
}
