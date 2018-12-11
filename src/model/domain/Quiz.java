package model.domain;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import model.db.DbStrategy;
import model.db.TextDbCategorieReader;
import model.db.TextDbQuestionReader;
import model.db.TextDbResultReader;
import model.domain.feedbackStrategys.FeedbackStrategy;
import model.domain.feedbackStrategys.FeedbackStrategyFactory;
import model.domain.feedbackStrategys.FeedbackTypes;
import model.domain.questions.Question;

public class Quiz {
	private DbStrategy categorieReader;
	private DbStrategy questionReader;
	private TextDbResultReader resultReader;
	
	public Quiz() {
		categorieReader = TextDbCategorieReader.getInstance("Categories.txt");
		questionReader = TextDbQuestionReader.getInstance("Questions.txt");
		
		
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("evaluation.properties"));
		} catch (IOException e) {
			System.out.println("Could not load properties file...");
		}
		
		this.setFeedbackStrategy(FeedbackStrategyFactory.createStrategy(FeedbackTypes.valueOf(properties.getProperty("evaluation.mode")).getClassName()));
	
		resultReader = new TextDbResultReader("Result.txt");
		
		getFeedbackStrategy().setFeedback(resultReader.getFeedback());
	}
	
	public void setFeedbackStrategy(FeedbackStrategy strategy) {
		if (resultReader != null) resultReader.setFeedback(strategy);
	}
	
	public FeedbackStrategy getFeedbackStrategy() {
		return this.resultReader.getFeedbackStrategy();
	}

	public List<Question> getQuestions() {
		return this.questionReader.getItems();
		//return this.QR.getQuestions();
	}
	
	public List<Categorie> getCategories() {
		return this.categorieReader.getItems();
	}
	
	public boolean hasBeenDone() {
		return getFeedbackStrategy().hasBeenDone();
	}
	
	public void addQuestion(Question q) {
		if (q == null) throw new IllegalArgumentException("Can't add null to questions!");
		questionReader.addItem(q);
		
		//new QuestionTextReader().addQuestion(q);
	}
	
	public void removeQuestion(Question q) {
		questionReader.removeItem(q);
	}
	
	public void addCategorie(Categorie c) {
		if (c == null) throw new IllegalArgumentException("Can't add null to categories!");
		categorieReader.addItem(c);
		//new CategorieTextReader().addCategorie(c);
	}
	
	public void removeCategorie(Categorie cat) {
		categorieReader.removeItem(cat);
	}
	
	public boolean isFlawless() {
		return getFeedbackStrategy().isFlawless();
		
	}
	
	public String getFeedback() {
		return getFeedbackStrategy().getFeedback();
	}
	
	public void setFeedback(List<String> feedbackS) {
		getFeedbackStrategy().setFeedback(feedbackS);
		getFeedbackStrategy().setHasBeenDone(true);
	}
	
	public void save() {
		this.categorieReader.save();
		this.questionReader.save();
		this.resultReader.save();
	}
	
	public List<Question> startQuiz() {
		List<Question> questions = this.getQuestions();
		Collections.shuffle(questions);
		
		return questions;
	}

	
}
