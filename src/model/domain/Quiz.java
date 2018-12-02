package model.domain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.db.DbStrategy;
import model.db.TextDbCategorieReader;
import model.db.TextDbQuestionReader;

import model.domain.Question;
public class Quiz {
	private DbStrategy categorieReader;
	private DbStrategy questionReader;
	private List<String> results;
	
	
	
	public Quiz() {
		categorieReader = TextDbCategorieReader.getInstance("Categories.txt");
		questionReader = TextDbQuestionReader.getInstance("Questions.txt");
		
		this.results=new ArrayList<String>();
		
		//CR=  new CategorieTextReader();
		//QR = new QuestionTextReader();
	}
	
	public List<Question> getQuestions() {
		return this.questionReader.getItems();
		//return this.QR.getQuestions();
	}
	
	public List<Categorie> getCategories() {
		return this.categorieReader.getItems();
	}
	
	public void addQuestion(Question q) {
		if (q == null) throw new IllegalArgumentException("Can't add null to questions!");
		questionReader.addItem(q);
		
		//new QuestionTextReader().addQuestion(q);
	}
	
	public void addCategorie(Categorie c) {
		if (c == null) throw new IllegalArgumentException("Can't add null to categories!");
		categorieReader.addItem(c);
		//new CategorieTextReader().addCategorie(c);
	}
	
	public void addResults(List<String> results) {
		if (results == null || results.isEmpty()) throw new IllegalArgumentException("Not a valid list of results!");
		this.results = (results);
	}
	
	public String getResults() {
		if (this.results.isEmpty()) return "";
		else {
			String result = "";
			int totalAsked = 0;
			int totalCorrect = 0;
			List<String> categoryScores = new ArrayList<>();
			
			for (String r : results) {
				String[] rString = r.split("-");
				//rString[0] = category
				//rString[1] = # asked in this category
				//rString[2] = correct in this category
				totalAsked+=Integer.parseInt(rString[1]);
				totalCorrect+=Integer.parseInt(rString[2]);
				
				categoryScores.add("Category " + rString[0] + ": " + rString[2]+"/"+rString[1]);
			}
			
			result+="Your score: " + totalCorrect+"/"+totalAsked;
			
			for (String s : categoryScores) {
				result+="\n"+s;
			}
			
			return result;
		}
	}
	
	public String getFeedback() {
		//TODO (story 7)
		
		return "TODO";
	}
	
	public void save() {
		this.categorieReader.save();
		this.questionReader.save();
	}
	
	public List<Question> startQuiz() {
		List<Question> questions = this.getQuestions();
		Collections.shuffle(questions);
		
		return questions;
	}
}
