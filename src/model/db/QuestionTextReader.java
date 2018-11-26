package model.db;

import java.io.File;
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
		/*questions = new ArrayList();
		List<String[]> rawList = scanner.getText("Categories.txt");
		for (String[] str : rawList) {
			Categorie cat = null;
			if (str.length == 2) {
				cat = new Question(str[0], str[1]);
			} else if( str.length == 3) {
				cat = new Categorie(str[0], str[1], findCategorie(str[2]));
			}
			if (cat != null) questions.add(cat);
		}
		return questions;*/
		return questions;
	}
	
	public List<Question> getQuestions() {
		//TODO -> implement file reader
		readQuestions();
		
		return questions;
	}
	
	public void addQuestion(Question question) {
		//TODO -> convert question to text and write to file
	}
	
	public void addQuestions(List<Question> questions) {
		//Same as addQuestion but for multiple at once
	}
}
