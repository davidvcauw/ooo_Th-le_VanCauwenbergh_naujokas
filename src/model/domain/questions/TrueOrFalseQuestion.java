package model.domain.questions;

import java.util.Arrays;
import java.util.List;

import model.domain.Categorie;

public class TrueOrFalseQuestion extends Question {
	private List<String> statements;
	
	public TrueOrFalseQuestion(String question, Categorie cat, String feedback, Boolean answer) {
		super(question, cat, feedback);
		if (answer) this.setStatements(Arrays.asList("true", "false"));
		else this.setStatements(Arrays.asList("false", "true"));
	}
	
	public TrueOrFalseQuestion(String question, Categorie cat, Boolean answer) {
		this(question, cat, null, answer);
	}
	
	public List<String> getStatements() {
		return statements;
	}
	
	private void setStatements(List<String> statements) {
		this.statements = statements;
	}
	
	@Override
	public String toString() {
		return "TrueOrFalse-"+getQuestion()+"-["+statements.get(0)+"]-"+ getCategoryObject().getName() +"-"+getFeedback();
	}
}
