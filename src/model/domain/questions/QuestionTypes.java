package model.domain.questions;

public enum QuestionTypes {
	MultipleChoice("model.domain.questions.MultipleChoiceQuestion"),
	TrueOrFalse("model.domain.questions.TrueOrFalseQuestion");
	//add more question types here;
	
	private final String className;
	
	QuestionTypes(String name) {
		className = name;
	}
	
	public String getClassName() {
		return className;
	}
}
