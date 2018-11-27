package model.db;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.domain.Categorie;
import model.domain.Question;

@Deprecated
public class QuestionTextReader {
	private TextScanner scanner;
	private List<Question> questions;
	
	
	public QuestionTextReader() {
		scanner = TextScanner.getInstance();
		questions = new ArrayList<Question>();
		
	}
	
	private List<Question> readQuestions() {
		List<Question >questions = new ArrayList<Question>();
		List<String[]> rawList = TextScanner.getInstance().getText("Questions.txt");
		for (String[] str : rawList) {
			String questionS = str[0];
			String feedback = str.length>3?str[3]:"";
			String categoryName = str[2];
			CategorieTextReader CR = new CategorieTextReader();
			Categorie category = CR.findCategorie(categoryName);
			List<String> answers = new ArrayList<>(Arrays.asList(str[1].substring(1, str[1].length()-1).split(", ")));
			Question question = new Question(questionS, answers, category, feedback);
			
			questions.add(question);
		}
		return questions;
		
	}
	
	public List<Question> getQuestions() {
		this.questions = readQuestions();
		
		return questions;
	}
	
	public void addQuestion(Question question) {
		readQuestions();
		this.questions.add(question);
		writeQuestion();
	}
	
	public void addQuestions(List<Question> questions) {
		//Same as addQuestion but for multiple at once
		this.questions.addAll(questions);
		writeQuestion();
	}
	public void writeQuestion() {
		this.writeQuestion(questions);
	}
	public void writeQuestion(List<Question> question) {
		String towrite = "";
		for (Question q: question) {
			towrite +=q.getQuestion() +"-"+q.getStatements() +"-"+ q.getCategoryObject().getName() +"-"+q.getFeedback();
			towrite+="\n";
		}
		System.out.println("writing: " + towrite);
		try {
			FileWriter fileWriter = new FileWriter("Questions.txt");
		    fileWriter.write(towrite);
		    fileWriter.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		 
		
	}
}
