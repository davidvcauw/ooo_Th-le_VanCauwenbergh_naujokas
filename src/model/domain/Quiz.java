package model.domain;

import java.util.List;

import model.db.CategorieTextReader;
import model.db.QuestionTextReader;

public class Quiz {
	private CategorieTextReader CR;
	private QuestionTextReader QR;
	
	public Quiz() {
		
		
		CR=  new CategorieTextReader();
		QR = new QuestionTextReader();
	}
	
	public List<Question> getQuestions() {
		return this.QR.getQuestions();
	}
	
	public List<Categorie> getCategories() {
		return this.CR.getCategories();
	}
	
	public void addQuestion(Question q) {
		if (q == null) throw new IllegalArgumentException("Can't add null to questions!");
		new QuestionTextReader().addQuestion(q);
	}
	
	public void addCategorie(Categorie c) {
		if (c == null) throw new IllegalArgumentException("Can't add null to categories!");
		new CategorieTextReader().addCategorie(c);
	}
	
	//TODO: add more methods, needed for story 1-4
}
