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
	
	//TODO: add more methods, needed for story 1-4
}
