package model.db;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.domain.Categorie;
import model.domain.Question;

public class QuestionTextReader {
	private TextScanner scanner;
	private List<Question> questions;
	
	
	public QuestionTextReader() {
		scanner = TextScanner.getInstance();
		questions = new ArrayList<Question>();
		
	}
	
	private List<Question> readQuestions() {
		questions = new ArrayList();
		/*List<String[]> rawList = scanner.getText("Question.txt");
		for (String[] str : rawList) {
			Question question = null;
			if (str.length == 3) {
				question = new Question(str[0], str[1],str[2]);
			} else if( str.length == 4) {
				question = new Question(str[0], str[1], str[2], str[3]);// !!!!!dont know how to deal with List
			}
			if (question != null) questions.add(question);
		}*/
		return questions;
		
	}
	
	public List<Question> getQuestions() {
		readQuestions();
		
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
			towrite +=q.getQuestion() +"-"+q.getStatements() +"-"+ q.getCategory() +"-"+q.getFeedback();
			towrite+="\n";
		}
		System.out.println(towrite);
		try {
			FileWriter fileWriter = new FileWriter("Questions.txt");
		    fileWriter.write(towrite);
		    fileWriter.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		 
		
	}
}
