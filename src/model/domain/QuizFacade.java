package model.domain;

import java.util.List;

import model.domain.feedbackStrategys.FeedbackStrategy;
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
	
	public void updateQuestion(String className, String question, List<String> statements, String category, String feedback, Question previous)  {
		if (question == null || question.trim().isEmpty()) throw new IllegalArgumentException("Enter a question!");
		if (statements.size() < 2) throw new IllegalArgumentException("Atleast 2 statements are required!");
		if (category == null || category.trim().isEmpty()) throw new IllegalArgumentException("Choose a category!");
		Categorie categ = null;
		for (Categorie cat : quiz.getCategories()) {
			if (cat.getName().equals(category)) categ = cat;
		}
		Question q = null;
		
		if (feedback == null || feedback.trim().isEmpty()) {
			q = QuestionFactory.createQuestion(QuestionTypes.MC.getClassName(), question, statements, categ);
		} else {
			q = QuestionFactory.createQuestion(QuestionTypes.MC.getClassName(), question, statements, categ, feedback);
		}
		if (previous != null) {
			this.removeQuestion(previous);
		} 
		this.addQuestion(q);
	}
}
