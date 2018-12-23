package model.domain.feedbackStrategys.scoreCalculations;

import java.util.ArrayList;
import java.util.List;

public class NormalCalculationStrategy implements ScoreCalculationStrategy {

	@Override
	public String parseFeedback(List<String> results) {
		if (results.isEmpty()) return "";
		else {
			String result = "";
			int totalAsked = 0;
			int totalCorrect = 0;
			List<String> categoryScores = new ArrayList<>();
			
			for (String r : results) {
				String[] rString = r.split("-");
				if (!rString[0].equals("empty")) {
					totalAsked+=Integer.parseInt(rString[1]);
					totalCorrect+=Integer.parseInt(rString[2]);
					
					categoryScores.add("Category " + rString[0] + ": " + rString[2]+"/"+rString[1]);
				}
			}
			
			result+="Your score: " + totalCorrect+"/"+totalAsked;
			
			for (String s : categoryScores) {
				result+="\n"+s;
			}
			
			return result;
		}
	}

	@Override
	public String getDescription() {
		return "- Correct answers give 1 point\n- Wrong/Empty answers give 0 points";
	}
	
	@Override
	public String toString() {
		return "normal";
	}

}
