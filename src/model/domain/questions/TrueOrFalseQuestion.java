package model.domain.questions;

import java.util.Arrays;
import java.util.List;

import model.domain.Categorie;

public class TrueOrFalseQuestion extends Question {
	private List<String> statements;
	
	public TrueOrFalseQuestion(String question, Categorie cat, String feedback) {
		super(question, cat, feedback);
		this.setStatements(Arrays.asList("true", "false"));
	}
	
	public TrueOrFalseQuestion(String question, Categorie cat) {
		this(question, cat, null);
	}
	
	public List<String> getStatements() {
		return statements;
	}
	
	private void setStatements(List<String> statements) {
		this.statements = statements;
	}
	
	@Override
	public String toString() {
		return "TrueOrFalse-"+getQuestion()+"-[TRUE/FALSE]-"+ getCategoryObject().getName() +"-"+getFeedback();
	}
}
