package controller;

import java.util.List;
import java.util.Observable;

import model.domain.Categorie;
import model.domain.Question;
import model.domain.Quiz;

public class QuizController extends Observable {
	private Quiz quiz;
	
	public QuizController() {
		this.quiz = new Quiz();
	}
	
	public List<Categorie> getCategories() {
		return quiz.getCategories();
	}
	
	public List<Question> getQuestions() {
		return quiz.getQuestions();
	}
	
	public void addCategorie(Categorie c) {
		quiz.addCategorie(c);
		notifyDisplays();
	}
	
	public void addQuestion(Question q) {
		quiz.addQuestion(q);
		notifyDisplays();
	}
	
	public void save() {
		quiz.save();
	}
	
	public List<Question> startQuiz() {
		return quiz.startQuiz();
	}
	
	public String getFeedback() {
		//TODO (story 7)
		return quiz.getFeedback();
	}
	
	public void addResults(List<String> results) {
		quiz.addResults(results);
		notifyDisplays();
	}
	
	public String getResults() {
		return quiz.getResults();
	}
	
	public boolean isFlawless() {
		return quiz.isFlawless();
	}
	
	public void notifyDisplays() {
		setChanged();
		notifyObservers();
	}
}
