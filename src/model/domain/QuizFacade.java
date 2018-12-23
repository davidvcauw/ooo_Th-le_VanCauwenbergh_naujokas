package model.domain;

import java.util.ArrayList;
import java.util.List;

import model.domain.feedbackStrategys.FeedbackStrategy;
import model.domain.feedbackStrategys.FeedbackStrategyFactory;
import model.domain.feedbackStrategys.FeedbackTypes;
import model.domain.feedbackStrategys.TextStrategy;
import model.domain.questions.Question;
import model.domain.questions.QuestionFactory;
import model.domain.questions.QuestionTypes;

public class QuizFacade {
	private Quiz quiz;
	
	public QuizFacade() {
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
	}
	
	public void setTestmode(String file) {
		quiz.setTestmode(file);
	}
	
	public boolean hasBeenDone() {
		return quiz.hasBeenDone();
	}
	
	public void addQuestion(Question q) {
		quiz.addQuestion(q);
	}
	
	public void removeQuestion(Question q) {
		quiz.removeQuestion(q);
	}
	
	public void removeCategorie(Categorie c) {
		quiz.removeCategorie(c);
	}
	
	public boolean userCanEdit() {
		return quiz.userCanEdit();
	}
	
	public void resetResult() {
		quiz.resetResult();
	}
	
	public Categorie findCategorieByString(String name) {
		return quiz.findCategorieByString(name);
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
	}
	
	public boolean isFlawless() {
		return quiz.isFlawless();
	}
	
	public void updateCategorie(Categorie previous, String name, String description, String parentname) {
		if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Enter a name!");
		if (description == null || description.trim().isEmpty()) throw new IllegalArgumentException("Enter a description!");
		if (previous == null) {
			addCategorie(name, description, parentname);
		} else {
			quiz.removeCategorie(previous);
			addCategorie(name, description, parentname);
		}
	}
	
	public void addCategorie(String name, String description, String parentname) {
		if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Enter a name!");
		if (description == null || description.trim().isEmpty()) throw new IllegalArgumentException("Enter a description!");
		Categorie c = null;
		
		//check if a parent categorie was provided/chosen
		if (parentname == null || parentname.trim().isEmpty()) {
			c = new Categorie(name, description);
		} else {
			Categorie parent = findCategorieByString(parentname);
			c = new Categorie(name, description, parent);
		}
		addCategorie(c);
	}
	
	public void updateQuestion(String questionType, String question, List<String> statements, String category, String feedback, Question previous)  {	
		if (question == null || question.trim().isEmpty()) throw new IllegalArgumentException("Enter a question!");
		if (statements.size() < 2) throw new IllegalArgumentException("Atleast 2 statements are required!");
		if (category == null || category.trim().isEmpty()) throw new IllegalArgumentException("Choose a category!");
		Categorie categ = null;
		for (Categorie cat : quiz.getCategories()) {
			if (cat.getName().equals(category)) categ = cat;
		}
		try {
			QuestionTypes type = QuestionTypes.valueOf(questionType);
			
			Question q = null;
			
			if (feedback == null || feedback.trim().isEmpty()) {
				q = QuestionFactory.createQuestion(type.getClassName(), question, statements, categ);
			} else {
				q = QuestionFactory.createQuestion(type.getClassName(), question, statements, categ, feedback);
			}
			if (previous != null) {
				this.removeQuestion(previous);
			} 
			this.addQuestion(q);
		} catch(Exception e) {
			throw new IllegalArgumentException("Question type '" + questionType + "' does not excist!");
		}
	}
	
	public List<String> getCategorieNames() {
		List<String> names = new ArrayList<>();
		for (Categorie c : this.getCategories()) {
			names.add(c.getName());
		}
		return names;
	}
	
	public List<String> getFeedbackTypes() {
		List<String> types = new ArrayList<>();
		for (FeedbackTypes type : FeedbackTypes.values()) {
			types.add(type.name());
		}
		return types;
	}
	
	public void passFeedback(List<String> results, List<String> feedback) {
		if (getFeedbackStrategy() instanceof TextStrategy) quiz.setFeedback(feedback);
		else quiz.setFeedback(results);
	}
	
	public void setFeedbackStrategy(String name) {
		try {
			FeedbackTypes type = FeedbackTypes.valueOf(name);
			quiz.setFeedbackStrategy(FeedbackStrategyFactory.createStrategy(type.getClassName()));
		} catch (Exception e) {
			throw new IllegalArgumentException("Feedback type '" + name + "' does not excist!");
		}
	}
}
