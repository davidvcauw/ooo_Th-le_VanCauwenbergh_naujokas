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
		bn = "src/testdatabase/" + bn;
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
		Object[] params = new Object[4];
		
		params[0] = questionS;
		params[2] = feedback;
		params[1] = category;
		if (answers.get(0).equals("true") || answers.get(0).equals("false")) params[3] = Boolean.valueOf(answers.get(0));
		else params[3] = answers;
		
		question = QuestionFactory.createQuestion(QuestionTypes.valueOf(typeQuestion).getClassName(), params);
		
		return question;
	}

}
