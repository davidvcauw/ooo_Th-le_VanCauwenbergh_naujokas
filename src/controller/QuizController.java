package controller;

import java.util.List;
import java.util.Observable;

import model.domain.QuizFacade;

import model.domain.Categorie;
import model.domain.questions.Question;

public class QuizController extends Observable {
	private QuizFacade facade;
	
	public QuizController() {
		this.facade = new QuizFacade();
	}
	
	public List<Categorie> getCategories() {
		return facade.getCategories();
	}
	
	public List<Question> getQuestions() {
		return facade.getQuestions();
	}
	
	public void addCategorie(Categorie c) {
		facade.addCategorie(c);
		notifyDisplays();
	}
	
	public void setTestmode(String file) {
		facade.setTestmode(file);
		notifyDisplays();
	}
	
	public boolean hasBeenDone() {
		return facade.hasBeenDone();
	}
	
	public void addQuestion(Question q) {
		facade.addQuestion(q);
		notifyDisplays();
	}
	
	public void removeQuestion(Question q) {
		facade.removeQuestion(q);
		notifyDisplays();
	}
	
	public void removeCategorie(Categorie c) {
		facade.removeCategorie(c);
		notifyDisplays();
	}
	
	public boolean userCanEdit() {
		return facade.userCanEdit();
	}
	
	public void resetResult() {
		facade.resetResult();
		notifyDisplays();
	}
	
	public void updateQuestion(String questionType, String question, List<String> statements, String category, String feedback, Question previous) {
		facade.updateQuestion(questionType, question, statements, category, feedback, previous);
		notifyDisplays();
	}
	
	public void addCategorie(String title, String description, String parentname) {
		facade.addCategorie(title, description, parentname);
		notifyDisplays();
	}
	
	//if previous is null, add the categorie. Otherwise, change the values of previous
	public void updateCategorie(Categorie previous, String name, String description, String parentname) {
		facade.updateCategorie(previous, name, description, parentname);
		notifyDisplays();
	}
	
	public void save() {
		facade.save();
	}
	
	public List<String> getScoreCalcTypes() {
		return facade.getScoreCalcTypes();
	}
	
	public List<Question> startQuiz() {
		return facade.startQuiz();
	}
	
	public String getFeedback() {
		return facade.getFeedback();
	}
	
	public boolean isFlawless() {
		return facade.isFlawless();
	}

	public void setFeedbackStrategy(String strategyName) {
		facade.setFeedbackStrategy(strategyName);
	}
	
	public List<String> getFeedbackTypes() {
		return facade.getFeedbackTypes();
	}
	
	public void passFeedback(List<String> results, List<String> feedback) {
		facade.passFeedback(results, feedback);
		notifyDisplays();
	}
	
	public List<String> getCategorieNames() {
		return facade.getCategorieNames();
	}
	
///////////////////////////////////////////////////////////////////////////////////////	
	
	private void notifyDisplays() {
		setChanged();
		notifyObservers();
	}

	public String setScoreCalcStrategy(String strategy) {
		String result = facade.setScoreCalcStrategy(strategy);
		notifyDisplays();
		return result;
	}

	public String getCalcStrategy() {
		return facade.getCalcStrategy();
	}
}
