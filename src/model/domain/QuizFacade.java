package model.domain;

import java.util.ArrayList;
import java.util.List;

import model.domain.feedbackStrategys.FeedbackStrategy;
import model.domain.feedbackStrategys.FeedbackStrategyFactory;
import model.domain.feedbackStrategys.FeedbackTypes;
import model.domain.feedbackStrategys.ScoreStrategy;
import model.domain.feedbackStrategys.scoreCalculations.ScoreCalculationStrategy;
import model.domain.feedbackStrategys.scoreCalculations.ScoreCalculationTypes;
import model.domain.questions.MultipleChoiceQuestion;
import model.domain.questions.Question;
import model.domain.questions.QuestionFactory;
import model.domain.questions.QuestionTypes;
import model.domain.questions.TrueOrFalseQuestion;

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
		for (Question q : quiz.getQuestions()) {
			if (q.getCategoryObject().equals(c)) quiz.removeQuestion(q);
		}
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
			Categorie c = addCategorie(name, description, parentname);
			for (Question q : this.getQuestions()) {
				if (q.getCategoryObject().equals(previous)) q.setCategory(c);
			}
		}
	}
	
	public Categorie addCategorie(String name, String description, String parentname) {
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
		return c;
	}
	
	public void updateQuestion(String questionType, String question, String category, String feedback, Question previous, Object...args)  {	
		if (question == null || question.trim().isEmpty()) throw new IllegalArgumentException("Enter a question!");
		if (category == null || category.trim().isEmpty()) throw new IllegalArgumentException("Choose a category!");
		Categorie categ = null;
		for (Categorie cat : quiz.getCategories()) {
			if (cat.getName().equals(category)) categ = cat;
		}
		try {
			QuestionTypes type = QuestionTypes.valueOf(questionType);
			
			Question q = null;
			if (args!=null && args.length > 0) {
				if (feedback == null || feedback.trim().isEmpty()) q = QuestionFactory.createQuestion(type.getClassName(), question, categ, args);
				else q = QuestionFactory.createQuestion(type.getClassName(), question, categ, feedback, args);
			} else {
				if (feedback == null || feedback.trim().isEmpty()) q = QuestionFactory.createQuestion(type.getClassName(), question, categ);
				else q = QuestionFactory.createQuestion(type.getClassName(), question, categ, feedback);
			}
			if (previous != null) {
				this.removeQuestion(previous);
			} 
			this.addQuestion(q);
		} catch(Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Question type '" + questionType + "' does not exist!");
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
		quiz.passFeedback(results, feedback);
	}
	
	public void setFeedbackStrategy(String name) {
		try {
			FeedbackTypes type = FeedbackTypes.valueOf(name);
			quiz.setFeedbackStrategy(FeedbackStrategyFactory.createStrategy(type.getClassName()));
		} catch (Exception e) {
			throw new IllegalArgumentException("Feedback type '" + name + "' does not excist!");
		}
	}
	
	public List<String> getScoreCalcTypes() {
		List<String> types = new ArrayList<>();
		for (ScoreCalculationTypes type : ScoreCalculationTypes.values()) {
			types.add(type.name());
		}
		return types;
	}

	public String setScoreCalcStrategy(String strategy) {
		FeedbackStrategy strat = quiz.getFeedbackStrategy();
		if (strat instanceof ScoreStrategy) {
			ScoreStrategy scoreStrat = (ScoreStrategy)strat;
			ScoreCalculationStrategy calcStrat = scoreStrat.setScoreCalculationStrategy(strategy);
			return calcStrat.getDescription();
		}
		return null;
	}

	public String getCalcStrategy() {
		if (this.getFeedbackStrategy() instanceof ScoreStrategy) {
			return ((ScoreStrategy)getFeedbackStrategy()).getCalcStrategy();
		}
		return null;
	}

	public List<String> getQuestionTypes() {
		List<String> types = new ArrayList<>();
		
		for (QuestionTypes type : QuestionTypes.values()) {
			types.add(type.name());
		}
		
		return types;
	}

	public String getQuestionType(Question question) {
		return question.getClass().getSimpleName().replaceAll("Question", "");
	}

	public List<String> getAnswers(Question question) {
		List<String> answers = new ArrayList<>();
		
		if (question instanceof MultipleChoiceQuestion) answers.addAll(((MultipleChoiceQuestion)question).getStatements());
		if (question instanceof TrueOrFalseQuestion) answers.addAll(((TrueOrFalseQuestion)question).getStatements());
		
		return answers;
	}
}
