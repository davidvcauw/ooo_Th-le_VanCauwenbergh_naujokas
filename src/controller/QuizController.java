package controller;

import java.util.List;
import java.util.Observable;

import model.domain.Categorie;
import model.domain.Quiz;
import model.domain.feedbackStrategys.FeedbackStrategy;
import model.domain.questions.Question;

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
	
	public boolean hasBeenDone() {
		return quiz.hasBeenDone();
	}
	
	public void addQuestion(Question q) {
		
		quiz.addQuestion(q);
		
		notifyDisplays();
	}
	
	public void addCategorie(String title, String description, String parentname) {
		Categorie c = null;
		
		//check if a parent categorie was provided/chosen
		if (parentname == null || parentname.trim().isEmpty()) {
			c = new Categorie(title, description);
		} else {
			Categorie parent = findCategorieByString(parentname);
			c = new Categorie(title, description, parent);
		}
		addCategorie(c);
	}
	
	//if previous is null, add the categorie. Otherwise, change the values of previous
	public void updateCategorie(Categorie previous, String name, String description, String parentname) {
		if (previous == null) {
			addCategorie(name, description, parentname);
		} else {
			/*previous.setDescription(description);
			previous.setName(name);
			
			if (parentname != null && !parentname.trim().isEmpty()) {
				previous.setParent(findCategorieByString(parentname));
			} */
			
			quiz.removeCategorie(previous);
			
			this.addCategorie(name, description, parentname);
			notifyDisplays();
		}
	}
	
	private Categorie findCategorieByString(String name) {
		for (Categorie cat : quiz.getCategories()) {
			if (cat.getName().equals(name)) return cat;
		}
		throw new IllegalArgumentException("categorie kon niet gevonden worden");
	}
	
	public void save() {
		quiz.save();
	}
	
	public List<Question> startQuiz() {
		return quiz.startQuiz();
	}
	
	public void setFeedbackStrategy(FeedbackStrategy strategy) {
		quiz.setFeedbackStrategy(strategy);
	}
	
	public FeedbackStrategy getFeedbackStrategy() {
		return quiz.getFeedbackStrategy();
	}
	
	public String getFeedback() {
		return quiz.getFeedback();
	}
	
	public void setFeedback(List<String> s) {
		quiz.setFeedback(s);
		//System.out.println(quiz.getFeedbackStrategy().toString());
		notifyDisplays();
	}
	
	public boolean isFlawless() {
		return quiz.isFlawless();
	}
	
	public void notifyDisplays() {
		setChanged();
		notifyObservers();
	}
}
