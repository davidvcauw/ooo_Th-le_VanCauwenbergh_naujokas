package model.domain;

import java.util.List;

import model.db.CategorieTextReader;
import model.db.QuestionTextReader;

public class Quiz {
	private List<Categorie> categories;
	private List<Question> questions;
	
	public Quiz() {
		this.categories = new CategorieTextReader().getCategories();
		this.questions = new QuestionTextReader().getQuestions();
	}
	
	public List<Question> getQuestions() {
		return this.questions;
	}
	
	public List<Categorie> getCategories() {
		return this.categories;
	}
	
	public void addQuestion(Question q) {
		if (q == null) throw new IllegalArgumentException("Can't add null to questions!");
		this.questions.add(q);
	}
	
	public void addCategorie(Categorie c) {
		if (c == null) throw new IllegalArgumentException("Can't add null to categories!");
		this.categories.add(c);
	}
	
	//TODO: add more methods, needed for story 1-4
}
