package model.domain.feedbackStrategys;

import java.util.ArrayList;
import java.util.List;

public class TextStrategy implements FeedbackStrategy {

	private List<String> feedback = new ArrayList<String>();
	private boolean hasBeenDone = false;
	
	@Override
	public void setFeedback(List<String> f) {
		this.feedback = f;
		hasBeenDone = true;
	}

	public void setHasBeenDone(boolean b) {
		hasBeenDone = b;
	}
	
	@Override
	public boolean isFlawless() {
		if (feedback.size() > 0 && feedback.get(0).equals("empty")) return true;
		else return false; 
	}
	
	public List<String> getFeedbackList() {
		return feedback;
	}
	
	public boolean hasBeenDone() {
		return hasBeenDone;
	}
	
	public void reset() {
		this.feedback = new ArrayList<String>();
		setHasBeenDone(false);
	}
	
	@Override
	public String getFeedback() {
		String feedback = "";
		for(String s : this.feedback) {
			feedback+=s+"\n";
		}
		return feedback;
	}
	
	
	public String getType() {
		return "feedback";
	}
	
	
	@Override
	public String toString() {
		return feedback.isEmpty()?"":"feedback--normal--"+feedback.toString();
	}

}
