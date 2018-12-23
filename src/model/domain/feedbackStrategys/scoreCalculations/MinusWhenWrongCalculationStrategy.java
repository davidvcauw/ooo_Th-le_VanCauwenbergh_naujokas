package model.domain.feedbackStrategys.scoreCalculations;

import java.util.ArrayList;
import java.util.List;

public class MinusWhenWrongCalculationStrategy implements ScoreCalculationStrategy {

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
					int asked = Integer.parseInt(rString[1]);
					totalAsked+=asked;
					int correct = Integer.parseInt(rString[2]);
					int answered = Integer.parseInt(rString[3]);
					
					correct -= (answered-correct);
					totalCorrect+=correct;
					
					categoryScores.add("Category " + rString[0] + ": " + correct+"/"+asked);
				}
			}
			
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

	@Override
	public String getDescription() {
		return "- Correct answers give 1 point\n- Wrong answers give -1 point\n- Empty answers give 0 points";
	}
	
	@Override
	public String toString() {
		return "minusWhenWrong";
	}

}
