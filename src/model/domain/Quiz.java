package model.domain;
import model.db.QuestionTextReader;
import java.util.List;

import model.db.CategorieTextReader;
import model.db.QuestionTextReader;
import model.domain.Question;
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
	
	public void runQuiz() {
		QuestionTextReader qr = new QuestionTextReader();
		/*Question q;
		qr.getQuestions();*/
		////for(String [] question : q.getStatements())
		for (Question q : qr.getQuestions()) {
			for (String s : q.getStatements()) {
				System.out.println(q.getQuestion() + ' ' + s);
			}
		}
		System.out.println();
		
	}
}
