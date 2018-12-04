package model.domain.feedbackStrategys;

import java.util.ArrayList;
import java.util.List;

public class ScoreStrategy implements FeedbackStrategy {
	
	private List<String> results;

	@Override
	public void setFeedback(List<String> f) {
		this.results = f;
		
	}

	@Override
	public String getFeedback() {
		if (this.results.isEmpty()) return "";
		else {
			String result = "";
			int totalAsked = 0;
			int totalCorrect = 0;
			List<String> categoryScores = new ArrayList<>();
			
			for (String r : results) {
				String[] rString = r.split("-");
				//rString[0] = category
				//rString[1] = # asked in this category
				//rString[2] = correct in this category
				totalAsked+=Integer.parseInt(rString[1]);
				totalCorrect+=Integer.parseInt(rString[2]);
				
				categoryScores.add("Category " + rString[0] + ": " + rString[2]+"/"+rString[1]);
			}
			
			result+="Your score: " + totalCorrect+"/"+totalAsked;
			
			for (String s : categoryScores) {
				result+="\n"+s;
			}
			
			return result;
		}
	}
	
}
