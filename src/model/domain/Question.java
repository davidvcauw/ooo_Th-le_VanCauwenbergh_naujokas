package model.domain;

public abstract class Question {
	private String question;
	private Categorie category;
	private String feedback;
	
	protected Question(String question, Categorie cat, String feedback) {
		setQuestion(question);
		setCategory(cat);
		setFeedback(feedback);
	}
	public Question(String question, Categorie cat) {
		this(question, cat, null);
	}

	public String getQuestion() {
		return question;
	}


	public Categorie getCategoryObject() {
		return category;
	}
	
	public String getCategory() {
		return category.getName();
	}

	public String getFeedback() {
		return feedback;
	}

	public void setQuestion(String question) {
		if (question == null || question.trim().isEmpty()) throw new IllegalArgumentException("Question can't be empty!");
		if (question.contains("-")) throw new IllegalArgumentException("Question can't include '-'!");
		this.question = question;
	}

	public void setCategory(Categorie category) {
		if (category == null) throw new IllegalArgumentException("You need to provide a category!");
		this.category = category;
	}

	public void setFeedback(String feedback) {
		if (feedback == null) feedback = "";
		if (feedback.contains("-")) throw new IllegalArgumentException("Question description can't include '-'!");
		
		//not all questions need feed back
		this.feedback = feedback;
	}
	
	@Override
	public String toString() {
		return "Q-"+ getQuestion()+"-"+"-"+ getCategoryObject().getName() +"-"+getFeedback();
	}
}
