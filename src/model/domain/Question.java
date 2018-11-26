package model.domain;

import java.util.List;

public class Question {
	private String question;
	private List<String> statements;
	private Categorie category;
	private String feedback;
	
	public Question(String question, List<String> statements, Categorie cat, String feedback) {
		setQuestion(question);
		setStatements(statements);
		setCategory(cat);
		setFeedback(feedback);
	}

	public String getQuestion() {
		return question;
	}

	public List<String> getStatements() {
		return statements;
	}

	public Categorie getCategory() {
		return category;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setQuestion(String question) {
		if (question == null || question.trim().isEmpty()) throw new IllegalArgumentException("Question can't be empty!");
		if (question.contains("-")) throw new IllegalArgumentException("Question can't include '-'!");
		this.question = question;
	}

	public void setStatements(List<String> statements) {
		if (statements == null || statements.size() < 2) throw new IllegalArgumentException("You need atleast 2 statements!");
		for (String s : statements) {
			if (s.contains("-")) throw new IllegalArgumentException("Statements can't include '-'!");
		}
		this.statements = statements;
	}

	public void setCategory(Categorie category) {
		if (category == null) throw new IllegalArgumentException("You need to provide a category!");
		this.category = category;
	}

	public void setFeedback(String feedback) {
		if (feedback.contains("-")) throw new IllegalArgumentException("Question description can't include '-'!");
		
		//not all questions need feed back
		this.feedback = feedback;
	}
}
