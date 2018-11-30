package model.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import model.domain.Categorie;
import model.domain.MultipleChoiceQuestion;
import model.domain.Question;

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
		String questionS = str[0];
		String feedback = str.length>3?str[3]:"";
		String categoryName = str[2];
		TextDbCategorieReader CR = TextDbCategorieReader.getInstance("Categories.txt");
		Categorie category = CR.findCategorie(categoryName);
		List<String> answers = new ArrayList<>(Arrays.asList(str[1].substring(1, str[1].length()-1).split(", ")));
		Question question = new MultipleChoiceQuestion(questionS, answers, category, feedback);
		
		return question;
	}

}
