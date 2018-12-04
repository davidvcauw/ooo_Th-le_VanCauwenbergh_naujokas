package model.domain.questions;

import java.util.ArrayList;
import java.util.List;

import model.domain.Categorie;

public class MultipleChoiceQuestion extends Question {
	private List<String> statements;
	
	public MultipleChoiceQuestion(String question, ArrayList<String> statements, Categorie cat, String feedback) {
		super(question, cat, feedback);
		this.setStatements(statements);
	}
	
	public MultipleChoiceQuestion(String question, ArrayList<String> statements, Categorie cat) {
		this(question, statements, cat, null);
	}
	
	public List<String> getStatements() {
		return statements;
	}
	
	public void setStatements(List<String> statements) {
		if (statements == null || statements.size() < 2) throw new IllegalArgumentException("You need atleast 2 statements!");
		for (String s : statements) {
			if (s.contains("-")) throw new IllegalArgumentException("Statements can't include '-'!");
		}
		this.statements = statements;
	}
	
	@Override
	public String toString() {
		return "MC-"+getQuestion()+"-"+getStatements() +"-"+ getCategoryObject().getName() +"-"+getFeedback();
	}
}
