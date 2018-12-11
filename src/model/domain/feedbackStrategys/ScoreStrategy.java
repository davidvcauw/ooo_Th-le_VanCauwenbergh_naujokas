package model.domain.feedbackStrategys;

import java.util.ArrayList;
import java.util.List;

public class ScoreStrategy implements FeedbackStrategy {
	
	private List<String> results = new ArrayList<String>();
	private boolean hasBeenDone = false;

	@Override
	public void setFeedback(List<String> f) {
		this.results = f;		
	}
	
	public void setHasBeenDone(boolean b) {
		hasBeenDone = b;
	}
	
	public List<String> getFeedbackList() {
		return results;
	}
	
	public boolean hasBeenDone() {
		return hasBeenDone;
	}
	
	@Override
	public boolean isFlawless() {
		if (this.results.isEmpty()) return false;
		else {
			int totalAsked = 0;
			int totalCorrect = 0;
			for (String r : results) {
				String[] rString = r.split("-");
				if (rString[0].equals("empty")) {
					return false;
				} else {
					totalAsked+=Integer.parseInt(rString[1]);
					totalCorrect+=Integer.parseInt(rString[2]);
				}
			}
			return totalAsked == totalCorrect;
		}
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
	public String toString() {
		return results.isEmpty()?"score--empty":"score--"+results.toString();
	}
	
}
