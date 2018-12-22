package model.domain;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import model.db.DbStrategy;
import model.db.ExcelDb;
import model.db.TextDbCategorieReader;
import model.db.TextDbQuestionReader;
import model.db.TextDbResultReader;
import model.domain.feedbackStrategys.FeedbackStrategy;
import model.domain.feedbackStrategys.FeedbackStrategyFactory;
import model.domain.feedbackStrategys.FeedbackTypes;
import model.domain.questions.Question;

@SuppressWarnings("rawtypes")
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
		
		resultReader = new TextDbResultReader("Result.txt");
		this.setFeedbackStrategy(resultReader.getFeedbackStrategy());
	}

	public void setTestmode(String file) {
		if (file.contains(".xls")) {
			categorieReader = ExcelDb.getInstance(file);
			questionReader = ExcelDb.getInstance(file);
		} else {
			categorieReader = TextDbCategorieReader.getInstance("Categories.txt");
			questionReader = TextDbQuestionReader.getInstance("Questions.txt");
		}
	}
	
	public boolean userCanEdit() {
		if (this.categorieReader instanceof ExcelDb) return false;
		return true;
	}
	
	public void setFeedbackStrategy(FeedbackStrategy strategy) {
		if (resultReader != null) resultReader.setFeedback(strategy);
	}
	
	public FeedbackStrategy getFeedbackStrategy() {
		return this.resultReader.getFeedbackStrategy();
	}

	public List<Question> getQuestions() {
		List<Question> questions = new ArrayList<>();
		
		for(Object e : questionReader.getItems()) {
			if (e instanceof Question) {
				questions.add((Question)e);
			}
		}
		
		return questions;
	}
	
	public List<Categorie> getCategories() {
		List<Categorie> categories = new ArrayList<>();
		
		for(Object e : categorieReader.getItems()) {
			if (e instanceof Categorie) {
				categories.add((Categorie)e);
			}
		}
		
		return categories;
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
	
	public void resetResult() {
		getFeedbackStrategy().reset();
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
