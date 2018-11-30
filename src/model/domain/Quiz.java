package model.domain;
import java.util.Collections;
import java.util.List;

import model.db.DbStrategy;
import model.db.TextDbCategorieReader;
import model.db.TextDbQuestionReader;

import model.domain.Question;
public class Quiz {
	private DbStrategy categorieReader;
	private DbStrategy questionReader;
	
	
	
	public Quiz() {
		categorieReader = TextDbCategorieReader.getInstance("Categories.txt");
		questionReader = TextDbQuestionReader.getInstance("Questions.txt");
		
		//CR=  new CategorieTextReader();
		//QR = new QuestionTextReader();
	}
	
	public List<Question> getQuestions() {
		return this.questionReader.getItems();
		//return this.QR.getQuestions();
	}
	
	public List<Categorie> getCategories() {
		return this.categorieReader.getItems();
	}
	
	public void addQuestion(Question q) {
		if (q == null) throw new IllegalArgumentException("Can't add null to questions!");
		questionReader.addItem(q);
		
		//new QuestionTextReader().addQuestion(q);
	}
	
	public void addCategorie(Categorie c) {
		if (c == null) throw new IllegalArgumentException("Can't add null to categories!");
		categorieReader.addItem(c);
		//new CategorieTextReader().addCategorie(c);
	}
	
	public void save() {
		this.categorieReader.save();
		this.questionReader.save();
	}
	
	public List<Question> startQuiz() {
		List<Question> questions = this.getQuestions();
		Collections.shuffle(questions);
		
		return questions;
	}
}
