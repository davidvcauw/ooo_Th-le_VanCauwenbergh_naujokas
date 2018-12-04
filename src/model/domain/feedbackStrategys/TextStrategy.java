package model.domain.feedbackStrategys;

import java.util.ArrayList;
import java.util.List;

public class TextStrategy implements FeedbackStrategy {

	private List<String> feedback = new ArrayList<String>();
	
	@Override
	public void setFeedback(List<String> f) {
		this.feedback = f;
	}

	@Override
	public boolean isFlawless() {
		if (feedback.size() == 0) return true;
		else return false;
	}
	
	@Override
	public String getFeedback() {
		String feedback = "";
		for(String s : this.feedback) {
			feedback+=s+"\n";
		}
		return feedback;
	}
	
	@Override
	public String toString() {
		return feedback.isEmpty()?"feedback-empty":"feedback-"+feedback.toString();
	}

}
