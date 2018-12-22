package controller;

import java.util.List;
import java.util.Observable;

import model.domain.Categorie;
import model.domain.Quiz;
import model.domain.QuizFacade;
import model.domain.feedbackStrategys.FeedbackStrategy;
import model.domain.questions.Question;
import model.domain.questions.QuestionFactory;
import model.domain.questions.QuestionTypes;

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
	
	public void updateQuestion(String className, String question, List<String> statements, String category, String feedback, Question previous) {
		facade.updateQuestion(className, question, statements, category, feedback, previous);
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
	
	private Categorie findCategorieByString(String name) {
		return facade.findCategorieByString(name);
	}
	
	public void save() {
		facade.save();
	}
	
	public List<Question> startQuiz() {
		return facade.startQuiz();
	}
	
	public void setFeedbackStrategy(FeedbackStrategy strategy) {
		facade.setFeedbackStrategy(strategy);
		notifyDisplays();
	}
	
	public FeedbackStrategy getFeedbackStrategy() {
		return facade.getFeedbackStrategy();
	}
	
	public String getFeedback() {
		return facade.getFeedback();
	}
	
	public void setFeedback(List<String> s) {
		facade.setFeedback(s);
		//System.out.println(quiz.getFeedbackStrategy().toString());
		notifyDisplays();
	}
	
	public boolean isFlawless() {
		return facade.isFlawless();
	}
	
	public void notifyDisplays() {
		setChanged();
		notifyObservers();
	}

	
}
