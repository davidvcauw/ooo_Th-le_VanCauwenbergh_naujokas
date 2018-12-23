package model.domain.feedbackStrategys.scoreCalculations;

import java.util.ArrayList;
import java.util.List;

public abstract class ScoreCalculationTemplate {
	private String result;
	private int totalAsked;
	private int totalCorrect;
	private List<String> results;
	private List<String> categoryScores;
	
	public String parseFeedback(List<String> results) {
		if (results.isEmpty()) return "";
		else {
			this.results = results;
			iterateResults();
			return displayResults();
		}
	}
	
	public void iterateResults() {
		result = "";
		totalAsked = 0;
		totalCorrect = 0;
		categoryScores = new ArrayList<>();
		
		for (String r : results) {
			String[] rString = r.split("-");
			if (!rString[0].equals("empty")) {
				int asked = Integer.parseInt(rString[1]);
				int correct = Integer.parseInt(rString[2]);
				int answered = Integer.parseInt(rString[3]);
				
				correct = calculateScore(asked, correct, answered);
				
				totalCorrect+=correct;
				totalAsked+=asked;
				
				categoryScores.add("Category " + rString[0] + ": " + correct+"/"+asked);
			}
		}
	}
	
	public abstract int calculateScore(int asked, int correct, int answered);
	
	public String displayResults() {
		boolean negative = false;
		while (totalCorrect < 0) {
			negative = true;
			totalCorrect++;
		}
		
		result+="Your score: " + totalCorrect+"/"+totalAsked;
		
		for (String s : categoryScores) {
			result+="\n"+s;
		}
		
		if (negative) result+="\n \n(Minimum total score = 0)";
		
		return result;
	}
}
