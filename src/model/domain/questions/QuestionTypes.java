package model.domain.questions;

public enum QuestionTypes {
	MC("model.domain.questions.MultipleChoiceQuestion");
	//add more question types here;
	
	private final String className;
	
	QuestionTypes(String name) {
		className = name;
	}
	
	public String getClassName() {
		return className;
	}
}
