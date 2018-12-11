package model.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import model.domain.Categorie;
import model.domain.questions.Question;
import model.domain.questions.QuestionFactory;
import model.domain.questions.QuestionTypes;

public class TextDbQuestionReader extends TextDb<Question>{
	private static HashMap<String, TextDbQuestionReader> instances = new HashMap<>();
	private TextDbQuestionReader(String bn) {
		super(bn);
		// TODO Auto-generated constructor stub
	}
	
	public static TextDbQuestionReader getInstance(String bn) {
		if (!instances.containsKey(bn)) {
			instances.put(bn, new TextDbQuestionReader(bn));
		}
		return instances.get(bn);
	}

	@Override
	protected Question parseInput(String[] str) {
		// TODO Auto-generated method stub
		String typeQuestion = str[0];
		String questionS = str[1];
		String feedback = str.length>4?str[4]:"";
		String categoryName = str[3];
		TextDbCategorieReader CR = TextDbCategorieReader.getInstance("Categories.txt");
		Categorie category = CR.findCategorie(categoryName);
		List<String> answers = new ArrayList<String>(Arrays.asList(str[2].substring(1, str[2].length()-1).split(", ")));
		
		Question question = null;
		
		//if (typeQuestion.equals("MC")) question = new MultipleChoiceQuestion(questionS, answers, category, feedback);
		//without factory
		
		question = QuestionFactory.createQuestion(QuestionTypes.valueOf(typeQuestion).getClassName(), questionS, answers, category, feedback);
		//with factory
		//later when more type of questions get added lines added here, maybe factory?
		
		return question;
	}

}
