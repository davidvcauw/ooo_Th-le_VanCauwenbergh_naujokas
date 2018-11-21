package model.db;

import java.io.File;
import java.util.List;

import model.domain.Question;

public class QuestionTextReader {
	private File file;
	
	public QuestionTextReader() {
		this.file=new File("Questions");
	}
	
	public List<Question> getQuestions() {
		//TODO -> implement file reader
		
		return null;
	}
	
	public void addQuestion(Question question) {
		//TODO -> convert question to text and write to file
	}
	
	public void addQuestions(List<Question> questions) {
		//Same as addQuestion but for multiple at once
	}
}
